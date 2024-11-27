package com.example.jkpvt.Entities.UserManagement.RoleModule;

import com.example.jkpvt.Core.Json.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roleModule")
@RequiredArgsConstructor
public class RoleModuleController {
    private final RoleModuleService roleModuleService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private RoleModuleDTO get(@QueryParams RoleModuleDTO roleModuleDTO) {
        List<RoleModuleDTO> roleModules = roleModuleService.get(roleModuleDTO);
        roleModuleDTO.setData(roleModules);
        return roleModuleDTO;
    }

    @PostMapping
    private List<RoleModuleDTO> addOrRemove(@RequestBody RoleModuleDTO roleModuleDTO) {
        return roleModuleService.addOrRemove(roleModuleDTO);
    }
}
