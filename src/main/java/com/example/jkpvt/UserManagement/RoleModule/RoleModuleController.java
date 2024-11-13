package com.example.jkpvt.UserManagement.RoleModule;

import com.example.jkpvt.Core.JsonUtil.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roleModule")
@RequiredArgsConstructor
public class RoleModuleController {
    private final RoleModuleService roleModuleService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<RoleModuleDTO> get(@RequestParam Map<String, String> queryParams) {
        RoleModuleDTO roleModuleDTO = MapUtils.toDto(queryParams, RoleModuleDTO.class);
        return roleModuleService.get(roleModuleDTO);
    }

    @PostMapping
    public List<RoleModuleDTO> addOrRemove(@RequestBody RoleModuleDTO roleModuleDTO) {
        return roleModuleService.addOrRemove(roleModuleDTO);
    }
}
