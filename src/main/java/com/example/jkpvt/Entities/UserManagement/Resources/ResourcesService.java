package com.example.jkpvt.Entities.UserManagement.Resources;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.Messages.CommonMessages;
import com.example.jkpvt.Core.Messages.Messages;
import com.example.jkpvt.Entities.UserManagement.Modules.Modules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResourcesService {

    private final ResourcesDAO dao;
    private final ResourcesMapper mapper;
    private final ResourcesRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<ResourcesDTO> get(ResourcesDTO resourcesDTO) {
        List<Resources> resources = dao.get(resourcesDTO);
        return getResourceDtoList(resources);
    }

    @Transactional
    public ResourcesDTO create(ResourcesDTO resourcesDTO) {
        Resources resources = mapper.map(resourcesDTO);

        if (resourcesDTO.getParentId() != null) {
            Resources parent = getById(resourcesDTO.getParentId());
            resources.setParentResource(parent);
        }

        rearrangeOrderCreate(resourcesDTO, resources);
        resources = repository.save(resources);
        return mapper.map(resources);
    }

    @Transactional
    public ResourcesDTO update(ResourcesDTO resourcesDTO) {
        Resources resources = getById(resourcesDTO.getId());
        updateResourcesData(resources, resourcesDTO);
        resources = repository.saveAndFlush(resources);
        return mapper.map(resources);
    }

    @Transactional
    public String delete(ResourcesDTO resourcesDTO) {
        Resources resource = getById(resourcesDTO.getId());

        // Clear relationships in the join table
        for (Modules module : new HashSet<>(resource.getModules())) {
            module.getResources().remove(resource);
        }

        resource.getModules().clear();
        repository.save(resource); // Save the resource to persist the relationship removal

        // Delete the resource itself
        repository.delete(resource);

        return Messages.getMessage(CommonMessages.DATA_DELETE_SUCCESS).getMessage();
    }

    @Transactional(readOnly = true)
    public Resources getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CommonException(ResourcesMessages.RESOURCE_NOT_FOUND));
    }

    @Transactional
    public List<Resources> getByIds(List<Long> ids) {
        return repository.findByIdIn(ids);
    }

    private void updateResourcesData(Resources resources, ResourcesDTO resourcesDTO) {
        if (resourcesDTO.getResourceName() != null) {
            resources.setResourceName(resourcesDTO.getResourceName());
        }

        if (resourcesDTO.getResourceFullName() != null) {
            resources.setResourceFullName(resourcesDTO.getResourceFullName());
        }

        if (resourcesDTO.getResourceDescription() != null) {
            resources.setResourceDescription(resourcesDTO.getResourceDescription());
        }

        if (resourcesDTO.getResourceUrl() != null) {
            resources.setResourceUrl(resourcesDTO.getResourceUrl());
        }

        if (resourcesDTO.getShowInMenu() != null) {
            resources.setShowInMenu(resourcesDTO.getShowInMenu());
        }

        if (resourcesDTO.getResourceOrder() != null) {
            rearrangeOrderUpdate(resourcesDTO, resources);
        }
    }

    private void updateAll(List<Resources> resources) {
        try {
            resources = repository.saveAll(resources);
            mapper.map(resources);
        } catch (Exception e) {
            throw new CommonException(CommonMessages.APPLICATION_ERROR);
        }
    }

    private void rearrangeOrderCreate(ResourcesDTO resourcesDTO, Resources resources) {
        List<Resources> resourcesList = repository.findAll();

        Map<Long, Resources> parentResources = new HashMap<>();
        Map<Long, List<Resources>> childResourcesForParent = new HashMap<>();
        for (Resources res : resourcesList) {
            if (res.getParentResource() == null) {
                parentResources.put(res.getId(), res);
            } else {
                childResourcesForParent.computeIfAbsent(res.getParentResource().getId(), k -> new ArrayList<>()).add(res);
            }
        }

        boolean orderMatchFound = parentResources.values().stream()
                .anyMatch(parent -> parent.getResourceOrder().equals(resourcesDTO.getResourceOrder()));

        long initialParentResourcesSize = parentResources != null ? parentResources.size() + 1 : +1;

        if (resourcesDTO.getParentId() == null) {
            if (resourcesDTO.getResourceOrder() == null || resourcesDTO.getResourceOrder() > initialParentResourcesSize) {
                resources.setResourceOrder(initialParentResourcesSize);
                resources.setResourceSubOrder(initialParentResourcesSize + "0");
            } else {
                resources.setResourceOrder(resourcesDTO.getResourceOrder());
                resources.setResourceSubOrder(resourcesDTO.getResourceOrder() + "0");
                if (orderMatchFound) {
                    changeOrderForParentAndChild(resources, parentResources, childResourcesForParent);
                }
            }
        } else {
            List<Resources> childResources = childResourcesForParent.get(resourcesDTO.getParentId());
            long initialChildResourceSize = childResources != null ? childResources.size() + 1 : +1;
            resources.setResourceOrder(null);
            if (resourcesDTO.getResourceOrder() == null || resourcesDTO.getResourceOrder() > initialChildResourceSize) {
                resources.setResourceSubOrder(resources.getParentResource().getResourceOrder() + Long.toString(initialChildResourceSize));
            } else {
                String subResOrder = resources.getParentResource().getResourceOrder() + resourcesDTO.getResourceOrder().toString();
                resources.setResourceSubOrder(subResOrder);
                boolean orderMatchFound1 = childResources != null && childResources.stream()
                        .anyMatch(child -> child.getResourceSubOrder().equals(subResOrder));
                if (orderMatchFound1) {
                    changeOrderForChild(resourcesDTO, childResources);
                }
            }
        }
    }

    private void changeOrderForParentAndChild(Resources resources, Map<Long, Resources> parentResources, Map<Long, List<Resources>> childResourcesForParent) {
        List<Resources> updatedResources = new ArrayList<>();
        for (Map.Entry<Long, Resources> entry : parentResources.entrySet()) {
            Resources parent = entry.getValue();
            if (parent.getResourceOrder() >= resources.getResourceOrder()) {
                parent.setResourceOrder(parent.getResourceOrder() + 1);
                parent.setResourceSubOrder(parent.getResourceOrder() + "0");
                updatedResources.add(parent);
                List<Resources> childResources = childResourcesForParent.get(parent.getId());
                if (childResources != null) {
                    for (Resources child : childResources) {
                        String parentOrderStr = String.valueOf(parent.getResourceOrder());
                        int length = parentOrderStr.length();
                        String actualChildOrder = child.getResourceSubOrder().substring(length);
                        child.setResourceSubOrder(child.getParentResource().getResourceOrder() + actualChildOrder);
                        updatedResources.add(child);
                    }
                }
            }
        }
        if (!updatedResources.isEmpty()) {
            updateAll(updatedResources);
        }
    }

    private void changeOrderForChild(ResourcesDTO resourcesDTO, List<Resources> childResources) {
        List<Resources> updatedResources = new ArrayList<>();
        for (Resources child : childResources) {
            String parentOrderStr = String.valueOf(child.getParentResource().getResourceOrder());
            int length = parentOrderStr.length();
            String actualChildOrder = child.getResourceSubOrder().substring(length);
            if (Long.parseLong(actualChildOrder) >= resourcesDTO.getResourceOrder()) {
                long newOrder = Long.parseLong(actualChildOrder) + 1;
                child.setResourceSubOrder(child.getParentResource().getResourceOrder() + Long.toString(newOrder));
                updatedResources.add(child);
            }
        }
        if (!updatedResources.isEmpty()) {
            updateAll(updatedResources);
        }
    }

    private void rearrangeOrderUpdate(ResourcesDTO resourcesDTO, Resources resources) {
        List<Resources> resourcesList = repository.findAll();

        Map<Long, Resources> parentResources = new HashMap<>();
        Map<Long, List<Resources>> childResourcesForParent = new HashMap<>();
        for (Resources res : resourcesList) {
            if (res.getParentResource() == null) {
                parentResources.put(res.getId(), res);
            } else {
                childResourcesForParent.computeIfAbsent(res.getParentResource().getId(), k -> new ArrayList<>()).add(res);
            }
        }

        boolean orderMatchFound = parentResources.values().stream()
                .anyMatch(parent -> parent.getResourceOrder().equals(resourcesDTO.getResourceOrder()));

        long initialParentResourcesSize = parentResources != null ? parentResources.size() + 1 : +1;

        // check if id matches with parent id or child id
        if (resources.getParentResource() == null) {
            if (!resources.getResourceOrder().equals(resourcesDTO.getResourceOrder())) {
                if (resourcesDTO.getResourceOrder() > initialParentResourcesSize) {
                    resources.setResourceOrder(initialParentResourcesSize);
                    resources.setResourceSubOrder(initialParentResourcesSize + "0");
                    updateChildOrder(childResourcesForParent.get(resources.getId()));
                } else {
                    Long oldOrder = resources.getResourceOrder();
                    Long newOrder = resourcesDTO.getResourceOrder();
                    resources.setResourceOrder(newOrder);
                    resources.setResourceSubOrder(resourcesDTO.getResourceOrder() + "0");
                    updateChildOrder(childResourcesForParent.get(resources.getId()));
                    if (orderMatchFound) {
                        changeOrderForParentAndChildUpdate(resources, parentResources, childResourcesForParent, oldOrder, newOrder);
                    }
                }
            }
        } else {
            List<Resources> childResources = childResourcesForParent.get(resources.getParentResource().getId());
            long initialChildResourceSize = childResources != null ? childResources.size() + 1 : +1;
            String parentOrderStr = String.valueOf(resources.getParentResource().getResourceOrder());
            int length = parentOrderStr.length();
            String actualChildOrder = resources.getResourceSubOrder().substring(length);
            String newChildOrder = resourcesDTO.getResourceOrder().toString();
            if (!actualChildOrder.equals(newChildOrder)) {
                if (resourcesDTO.getResourceOrder() > initialChildResourceSize) {
                    resources.setResourceSubOrder(resources.getParentResource().getResourceOrder() + Long.toString(initialChildResourceSize));
                } else {
                    resources.setResourceSubOrder(parentOrderStr + newChildOrder);
                    boolean orderMatchFound1 = childResources != null && childResources.stream()
                            .anyMatch(child -> child.getResourceSubOrder().equals(parentOrderStr + newChildOrder));
                    if (orderMatchFound1) {
                        changeOrderForChildUpdate(resources, childResources, actualChildOrder, newChildOrder);
                    }
                }
            }

        }
    }

    private void changeOrderForParentAndChildUpdate(Resources resources, Map<Long, Resources> parentResources, Map<Long, List<Resources>> childResourcesForParent, long oldOrder, long newOrder) {
        List<Resources> updatedResources = new ArrayList<>();
        for (Map.Entry<Long, Resources> entry : parentResources.entrySet()) {
            Resources parent = entry.getValue();
            if (resources.getId().equals(parent.getId())) {
                continue;
            }

            Long ParentCurrentOrder = parent.getResourceOrder();
            if (oldOrder < newOrder) {
                // Shifting up
                if (ParentCurrentOrder > oldOrder && ParentCurrentOrder <= newOrder) {
                    parent.setResourceOrder(ParentCurrentOrder - 1);
                }
            } else {
                // Shifting down
                if (ParentCurrentOrder < oldOrder && ParentCurrentOrder >= newOrder) {
                    parent.setResourceOrder(ParentCurrentOrder + 1);
                }
            }
            parent.setResourceSubOrder(parent.getResourceOrder() + "0");
            updatedResources.add(parent);
            updateChildOrder(childResourcesForParent.get(parent.getId()));
        }
        if (!updatedResources.isEmpty()) {
            updateAll(updatedResources);
        }
    }

    private void changeOrderForChildUpdate(Resources resources, List<Resources> childResources, String actualChildOrder, String newChildOrder) {
        List<Resources> updatedResources = new ArrayList<>();
        for (Resources child : childResources) {
            if (resources.getId().equals(child.getId())) {
                continue;
            }
            if (Long.parseLong(actualChildOrder) < Long.parseLong(newChildOrder)) {
                // Shifting up
                if (Long.parseLong(actualChildOrder) < Long.parseLong(child.getResourceSubOrder().substring(resources.getParentResource().getResourceOrder().toString().length())) &&
                        Long.parseLong(child.getResourceSubOrder().substring(resources.getParentResource().getResourceOrder().toString().length())) <= Long.parseLong(newChildOrder)) {
                    long newOrder = Long.parseLong(child.getResourceSubOrder().substring(resources.getParentResource().getResourceOrder().toString().length())) - 1;
                    child.setResourceSubOrder(resources.getParentResource().getResourceOrder() + Long.toString(newOrder));
                    updatedResources.add(child);
                }
            } else {
                // Shifting down
                if (Long.parseLong(actualChildOrder) > Long.parseLong(child.getResourceSubOrder().substring(resources.getParentResource().getResourceOrder().toString().length())) &&
                        Long.parseLong(child.getResourceSubOrder().substring(resources.getParentResource().getResourceOrder().toString().length())) >= Long.parseLong(newChildOrder)) {
                    long newOrder = Long.parseLong(child.getResourceSubOrder().substring(resources.getParentResource().getResourceOrder().toString().length())) + 1;
                    child.setResourceSubOrder(resources.getParentResource().getResourceOrder() + Long.toString(newOrder));
                    updatedResources.add(child);
                }
            }
        }
        if (!updatedResources.isEmpty()) {
            updateAll(updatedResources);
        }
    }

    private void updateChildOrder(List<Resources> childResources) {
        List<Resources> updatedResources = new ArrayList<>();
        if (childResources != null) {
            for (Resources child : childResources) {
                long parentResOrder = child.getParentResource().getResourceOrder();
                String parentOrderStr = String.valueOf(parentResOrder);
                int length = parentOrderStr.length();
                String actualChildOrder = child.getResourceSubOrder().substring(length);
                child.setResourceSubOrder(child.getParentResource().getResourceOrder() + actualChildOrder);
                updatedResources.add(child);
            }
        }
        if (!updatedResources.isEmpty()) {
            updateAll(updatedResources);
        }
    }

    public List<ResourcesDTO> getResourceDtoList(List<Resources> resources) {
        // Get all parent resources
        HashMap<Long, List<Resources>> parentChildResources = new HashMap<>();
        List<Resources> parentResources = new ArrayList<>();
        for (Resources resource : resources) {
            if (resource.getParentResource() == null) {
                parentResources.add(resource);
            } else {
                parentChildResources.computeIfAbsent(resource.getParentResource().getId(), k -> new ArrayList<>()).add(resource);
            }
        }

        List<ResourcesDTO> resourcesDTOList = new ArrayList<>();
        for (Resources parent : parentResources) {
            ResourcesDTO parentDTO = mapToResourcesDTO(parent);
            parentDTO.setChildResources(null);
            List<Resources> childResources = parentChildResources.get(parent.getId());
            if (childResources != null) {
                parentDTO.setChildResources(getChildResources(childResources));
            }
            resourcesDTOList.add(parentDTO);
        }
        return resourcesDTOList;
    }

    private ResourcesDTO mapToResourcesDTO(Resources resources) {
        ResourcesDTO resourcesDTO = mapper.map(resources);
        resourcesDTO.setParentResource(null);
        return resourcesDTO;
    }

    private Set<ResourcesDTO> getChildResources(List<Resources> resources) {
        Set<ResourcesDTO> childResources = new HashSet<>();
        for (Resources resource : resources) {
            ResourcesDTO newResourcesDTO = mapToResourcesDTO(resource);
            if (resource.getResourceSubOrder() != null) {
                long Order = Long.parseLong(resource.getResourceSubOrder().substring(resource.getParentResource().getResourceOrder().toString().length()));
                newResourcesDTO.setResourceOrder(Order);
            }
            childResources.add(newResourcesDTO);

        }
        return childResources;
    }
}
