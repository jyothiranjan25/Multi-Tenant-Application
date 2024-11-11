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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RoleModuleDTO create(@RequestBody RoleModuleDTO roleModuleDTO) {
        return roleModuleService.create(roleModuleDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public RoleModuleDTO update(@RequestBody RoleModuleDTO roleModuleDTO) {
        return roleModuleService.update(roleModuleDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestBody RoleModuleDTO roleModuleDTO) {
        return roleModuleService.delete(roleModuleDTO);
    }
}
