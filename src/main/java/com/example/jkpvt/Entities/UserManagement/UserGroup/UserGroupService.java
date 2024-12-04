package com.example.jkpvt.Entities.UserManagement.UserGroup;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.Messages.CommonMessages;
import com.example.jkpvt.Core.Messages.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupDAO dao;
    private final UserGroupMapper mapper;
    private final UserGroupRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<UserGroupDTO> getAll(UserGroupDTO userGroupDTO) {
        List<UserGroup> userGroup = dao.get(userGroupDTO);
        return mapper.map(userGroup);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<UserGroupDTO> get(UserGroupDTO userGroupDTO) {
        List<UserGroup> userGroups = dao.get(userGroupDTO);
        List<UserGroupDTO> userGroupDTOList = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            if (userGroup.getParentGroup() != null) {
                continue;
            }
            UserGroupDTO newUserGroupDTO = mapToUserGroupDTO(userGroup);
            Set<UserGroupDTO> childGroups = getChildGroups(userGroup);
            if (!childGroups.isEmpty()) {
                newUserGroupDTO.setChildGroups(childGroups);
            }
            userGroupDTOList.add(newUserGroupDTO);
        }
        return userGroupDTOList;
    }

    @Transactional
    public UserGroupDTO create(UserGroupDTO userGroupDTO) {
        UserGroup userGroup = mapper.map(userGroupDTO);
        if (userGroupDTO.getParentId() != null) {
            UserGroup parentGroup = getById(userGroupDTO.getParentId());
            userGroup.setParentGroup(parentGroup);
            userGroup.setQualifiedName(parentGroup.getQualifiedName() + "." + userGroup.getGroupName().toUpperCase());
        } else {
            userGroup.setQualifiedName(userGroup.getGroupName().toUpperCase());
        }
        userGroup = repository.save(userGroup);
        return mapper.map(userGroup);
    }

    @Transactional
    public UserGroupDTO update(UserGroupDTO userGroupDTO) {
        UserGroup userGroup = getById(userGroupDTO.getId());

        if (userGroupDTO.getGroupDescription() != null) {
            userGroup.setGroupDescription(userGroupDTO.getGroupDescription());
        }

        userGroup = repository.save(userGroup);
        return mapper.map(userGroup);
    }

    @Transactional
    public String delete(UserGroupDTO userGroupDTO) {
        if (repository.existsById(userGroupDTO.getId())) {
            repository.deleteById(userGroupDTO.getId());
            return Messages.getMessage(CommonMessages.DATA_DELETE_SUCCESS).getMessage();
        } else {
            throw new CommonException(UserGroupMessages.USER_GROUP_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public UserGroup getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommonException(UserGroupMessages.USER_GROUP_NOT_FOUND));
    }

    private Set<UserGroupDTO> getChildGroups(UserGroup userGroup) {
        Set<UserGroupDTO> childGroups = new HashSet<>();
        if (userGroup.getChildGroups() != null) {
            for (UserGroup childGroup : userGroup.getChildGroups()) {
                UserGroupDTO newUserGroupDTO = mapToUserGroupDTO(childGroup);
                Set<UserGroupDTO> child = getChildGroups(childGroup);
                if (!child.isEmpty()) {
                    newUserGroupDTO.setChildGroups(getChildGroups(childGroup));
                }
                childGroups.add(newUserGroupDTO);
            }
        }
        return childGroups;
    }

    private UserGroupDTO mapToUserGroupDTO(UserGroup userGroup) {
        UserGroupDTO newUserGroupDTO = new UserGroupDTO();
        newUserGroupDTO.setId(userGroup.getId());
        newUserGroupDTO.setGroupName(userGroup.getGroupName());
        newUserGroupDTO.setGroupDescription(userGroup.getGroupDescription());
        newUserGroupDTO.setQualifiedName(userGroup.getQualifiedName());
        return newUserGroupDTO;
    }
}