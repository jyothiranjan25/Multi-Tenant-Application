package com.example.jkpvt.UserManagement.RoleModule.RoleModuleResources;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roleModuleResources")
public class RoleModuleResourcesController {

    private final RoleModuleResourcesService roleModuleResourcesService;

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public RoleModuleResourcesDTO updateRoleModuleResources(@RequestBody RoleModuleResourcesDTO roleModuleResourcesDTO) {
        return roleModuleResourcesService.update(roleModuleResourcesDTO);
    }
}
