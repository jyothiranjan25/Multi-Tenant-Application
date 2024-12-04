package com.example.jkpvt.Entities.UserManagement.Modules;

import com.example.jkpvt.Core.Json.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/modules")
public class ModulesController {

    private final ModulesService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private ModulesDTO get(@QueryParams ModulesDTO modulesDTO) {
        List<ModulesDTO> modules = service.get(modulesDTO);
        modulesDTO.setData(modules);
        return modulesDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private ModulesDTO create(@RequestBody ModulesDTO modulesDTO) {
        return service.create(modulesDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    private ModulesDTO update(@RequestBody ModulesDTO modulesDTO) {
        return service.update(modulesDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    private String delete(@RequestBody ModulesDTO modulesDTO) {
        return service.delete(modulesDTO);
    }
}
