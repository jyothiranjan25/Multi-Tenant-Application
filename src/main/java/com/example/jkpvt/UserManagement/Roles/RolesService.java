package com.example.jkpvt.UserManagement.Roles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.RoleModule.RoleModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesDAO dao;
    private final RolesMapper mapper;
    private final RolesRepository repository;
    private final RoleModuleService roleModuleService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<RolesDTO> get(RolesDTO rolesDTO) {
        List<Roles> roles = dao.get(rolesDTO);
        return mapper.map(roles);
    }

    @Transactional
    public RolesDTO create(RolesDTO rolesDTO) {
        try {
            Roles roles = mapper.map(rolesDTO);
            roles = repository.save(roles);
            return mapper.map(roles);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }


    @Transactional
    public RolesDTO update(RolesDTO rolesDTO) {
        try {
            Roles roles = getById(rolesDTO.getId());

            // Update the Roles entity with the new values
            if (rolesDTO.getRoleName() != null) {
                roles.setRoleName(rolesDTO.getRoleName());
            }
            if (rolesDTO.getRoleDescription() != null) {
                roles.setRoleDescription(rolesDTO.getRoleDescription());
            }
            if (rolesDTO.getRoleIcon() != null) {
                roles.setRoleIcon(rolesDTO.getRoleIcon());
            }
            roles = repository.save(roles);
            return mapper.map(roles);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public String delete(RolesDTO rolesDTO) {
        try {
            if (repository.existsById(rolesDTO.getId())) {
                repository.deleteById(rolesDTO.getId());
                return "Data deleted successfully";
            } else {
                throw new CommonException("Data not found");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Roles getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommonException("Role with id: " + id + " not found"));
    }
}
