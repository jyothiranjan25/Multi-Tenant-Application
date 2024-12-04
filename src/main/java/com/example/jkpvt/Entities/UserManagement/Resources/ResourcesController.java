package com.example.jkpvt.Entities.UserManagement.Resources;

import com.example.jkpvt.Core.Json.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resources")
public class ResourcesController {

    private final ResourcesService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private ResourcesDTO get(@QueryParams ResourcesDTO resourcesDTO) {
        List<ResourcesDTO> resources = service.get(resourcesDTO);
        resourcesDTO.setData(resources);
        return resourcesDTO;
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
