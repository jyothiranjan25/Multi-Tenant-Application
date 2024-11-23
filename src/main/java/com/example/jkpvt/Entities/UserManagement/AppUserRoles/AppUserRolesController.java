package com.example.jkpvt.Entities.UserManagement.AppUserRoles;

import com.example.jkpvt.Core.Json.JsonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appUserRoles")
public class AppUserRolesController {

    private final AppUserRolesService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private List<AppUserRolesDTO> get(@RequestParam Map<String, String> queryParams) {
        AppUserRolesDTO appUserRolesDTO = JsonMap.toDto(queryParams, AppUserRolesDTO.class);
        return service.get(appUserRolesDTO);
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
