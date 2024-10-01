package com.example.jkpvt.UserManagement.AppUserRoles;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appUserRoles")
public class AppUserRolesController {

    private final AppUserRolesService service;

    @RequestMapping(value = "/create")
    public AppUserRolesDTO createAppUserRoles(@RequestBody AppUserRolesDTO appUserRolesDTO) {
        return service.create(appUserRolesDTO);
    }


    @RequestMapping(value = "/delete")
    public String deleteAppUserRoles(@RequestBody AppUserRolesDTO appUserRolesDTO) {
        return service.delete(appUserRolesDTO);
    }
}
