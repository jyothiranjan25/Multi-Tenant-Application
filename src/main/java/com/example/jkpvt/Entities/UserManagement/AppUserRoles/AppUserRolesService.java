package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Entities.UserManagement.AppUser.AppUserService;
import com.example.jkpvt.Entities.UserManagement.Roles.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserRolesService {

    private final AppUserRolesRepository repository;
    private final AppUserRolesDAO dao;
    private final AppUserRolesMapper mapper;
    private final AppUserService appUserService;
    private final RolesService rolesService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<AppUserRolesDTO> get(AppUserRolesDTO appUserRolesDTO) {
        List<AppUserRoles> appUserRoles = dao.get(appUserRolesDTO);
        return mapper.map(appUserRoles);
    }

    @Transactional
    public AppUserRolesDTO create(AppUserRolesDTO appUserRolesDTO) {
        try {
            AppUserRoles appUserRoles = mapper.map(appUserRolesDTO);
            appUserRoles.setRoles(rolesService.getById(appUserRolesDTO.getRolesId()));
            appUserRoles.setAppUser(appUserService.getById(appUserRolesDTO.getAppUserId()));
            appUserRoles = repository.save(appUserRoles);
            return mapper.map(appUserRoles);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public String delete(AppUserRolesDTO appUserRolesDTO) {
        try {
            if (repository.existsById(appUserRolesDTO.getId())) {
                repository.deleteById(appUserRolesDTO.getId());
                return "Data deleted successfully";
            } else {
                throw new CommonException("Data not found");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public AppUserRoles getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommonException("Data not found"));
    }

}
