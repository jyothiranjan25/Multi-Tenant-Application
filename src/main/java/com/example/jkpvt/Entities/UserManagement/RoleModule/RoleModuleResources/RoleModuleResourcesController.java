package com.example.jkpvt.Entities.UserManagement.RoleModule.RoleModuleResources;

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
    private RoleModuleResourcesDTO update(@RequestBody RoleModuleResourcesDTO roleModuleResourcesDTO) {
        return roleModuleResourcesService.update(roleModuleResourcesDTO);
    }
}
