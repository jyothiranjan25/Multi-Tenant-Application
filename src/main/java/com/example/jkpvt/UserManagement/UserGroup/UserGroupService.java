package com.example.jkpvt.UserManagement.UserGroup;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupDAO dao;
    private final UserGroupMapper mapper;
    private final UserGroupRepository repository;

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public List<UserGroupDTO> get(UserGroupDTO userGroupDTO) {
        return dao.get(userGroupDTO);
    }

    @Transactional
    public UserGroupDTO create(UserGroupDTO userGroupDTO) {
        try {
            UserGroup userGroup = mapper.map(userGroupDTO);
            if (userGroupDTO.getParentId() != null) {
                UserGroup parentGroup = repository.findById(userGroupDTO.getParentId())
                        .orElseThrow(() -> new CommonException("Parent not found"));
                userGroup.setParentGroup(parentGroup);
                userGroup.setQualifiedName(parentGroup.getQualifiedName() + "." + userGroup.getGroupName().toUpperCase());
            } else {
                userGroup.setQualifiedName(userGroup.getGroupName().toUpperCase());
            }
            userGroup = repository.save(userGroup);
            return mapper.map(userGroup);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public UserGroupDTO update(UserGroupDTO userGroupDTO) {
        try {
            UserGroup userGroup = getById(userGroupDTO.getId());

            if(userGroupDTO.getGroupDescription() != null) {
                userGroup.setGroupDescription(userGroupDTO.getGroupDescription());
            }

            userGroup = repository.save(userGroup);
            return mapper.map(userGroup);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public String delete(UserGroupDTO userGroupDTO) {
        try {
            if (repository.existsById(userGroupDTO.getId())) {
                repository.deleteById(userGroupDTO.getId());
                return "Data deleted successfully";
            } else {
                throw new CommonException("Data not found");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserGroup getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommonException("Group not found"));
    }
}