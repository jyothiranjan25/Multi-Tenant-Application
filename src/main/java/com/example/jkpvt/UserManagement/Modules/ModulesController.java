package com.example.jkpvt.UserManagement.Modules;

import com.example.jkpvt.Core.JsonUtil.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/modules")
public class ModulesController {

    private final ModulesService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<ModulesDTO> get(@RequestParam Map<String, String> queryParams) {
        ModulesDTO modulesDTO = MapUtils.toDto(queryParams, ModulesDTO.class);
        return service.get(modulesDTO);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModulesDTO create(@RequestBody ModulesDTO modulesDTO) {
        return service.create(modulesDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ModulesDTO update(@RequestBody ModulesDTO modulesDTO) {
        return service.update(modulesDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestBody ModulesDTO modulesDTO) {
        return service.delete(modulesDTO);
    }
}
