package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources.RoleModuleResourcesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleModuleService {

    private final RoleModuleRepository repository;
    private final RoleModuleMapper mapper;
    private final RoleModuleResourcesService roleModuleResourcesService;
    @Transactional
    public RoleModuleDTO saveRoleModule(RoleModuleDTO roleModuleDTO) {
        try {
            RoleModule roleModule = mapper.map(roleModuleDTO);
            repository.save(roleModule);
            return roleModuleDTO;
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public List<RoleModuleDTO> saveAll(List<RoleModule> roleModule) {
        try {
            List<RoleModule> roleModuleList = repository.saveAll(roleModule);
            List<RoleModuleDTO> roleModuleDTOList = mapper.map(roleModuleList);
            roleModuleResourcesService.saveAll(roleModuleList);
            return roleModuleDTOList;
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }


    @Transactional
    public RoleModuleDTO updateRoleModule(RoleModuleDTO roleModuleDTO) {
        try {
            RoleModule roleModule = mapper.map(roleModuleDTO);
            repository.save(roleModule);
            return roleModuleDTO;
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public String deleteRoleModule(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return "Data deleted successfully";
            } else {
                throw new CommonException("Data not found");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

}
