package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Core.Json.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appUserRoles")
public class AppUserRolesController {

    private final AppUserRolesService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private AppUserRolesDTO get(@QueryParams AppUserRolesDTO appUserRolesDTO) {
        List<AppUserRolesDTO> appUserRoles = service.get(appUserRolesDTO);
        appUserRolesDTO.setData(appUserRoles);
        return appUserRolesDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private AppUserRolesDTO create(@RequestBody AppUserRolesDTO appUserRolesDTO) {
        return service.create(appUserRolesDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    private String delete(@RequestBody AppUserRolesDTO appUserRolesDTO) {
        return service.delete(appUserRolesDTO);
    }
}
