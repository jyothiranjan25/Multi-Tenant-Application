package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.Messages.CommonMessages;
import com.example.jkpvt.Core.Messages.Messages;
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
        AppUserRoles appUserRoles = mapper.map(appUserRolesDTO);
        appUserRoles.setRoles(rolesService.getById(appUserRolesDTO.getRolesId()));
        appUserRoles.setAppUser(appUserService.getById(appUserRolesDTO.getAppUserId()));
        appUserRoles = repository.save(appUserRoles);
        return mapper.map(appUserRoles);
    }

    @Transactional
    public String delete(AppUserRolesDTO appUserRolesDTO) {
        if (repository.existsById(appUserRolesDTO.getId())) {
            repository.deleteById(appUserRolesDTO.getId());
            return Messages.getMessage(CommonMessages.DATA_DELETE_SUCCESS).toString();
        } else {
            throw new CommonException(AppUserRolesMessages.APP_USER_ROLE_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public AppUserRoles getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommonException(AppUserRolesMessages.APP_USER_ROLE_NOT_FOUND));
    }
}
