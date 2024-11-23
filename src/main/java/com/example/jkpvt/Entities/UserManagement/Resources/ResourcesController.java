package com.example.jkpvt.Entities.UserManagement.Resources;

import com.example.jkpvt.Core.Json.JsonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resources")
public class ResourcesController {

    private final ResourcesService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private List<ResourcesDTO> get(@RequestParam Map<String, String> queryParams) {
        ResourcesDTO resourcesDTO = JsonMap.toDto(queryParams, ResourcesDTO.class);
        return service.get(resourcesDTO);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private ResourcesDTO create(@RequestBody ResourcesDTO resourcesDTO) {
        return service.create(resourcesDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    private ResourcesDTO update(@RequestBody ResourcesDTO resourcesDTO) {
        return service.update(resourcesDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    private String delete(@RequestBody ResourcesDTO resourcesDTO) {
        return service.delete(resourcesDTO);
    }

}
