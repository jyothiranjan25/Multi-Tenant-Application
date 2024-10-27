package com.example.jkpvt.UserManagement.Resources;

import com.example.jkpvt.Core.JsonUtil.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resources")
public class ResourcesController {

    private final ResourcesService service;

    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<ResourcesDTO> getResourcesAll(@RequestParam Map<String, String> queryParams) {
        ResourcesDTO resourcesDTO = MapUtils.toDto(queryParams, ResourcesDTO.class);
        return service.get(resourcesDTO);
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public List<ResourcesDTO> getResources(@RequestParam Map<String, String> queryParams) {
        ResourcesDTO resourcesDTO = MapUtils.toDto(queryParams, ResourcesDTO.class);
        return service.getResources(resourcesDTO);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResourcesDTO createResources(@RequestBody ResourcesDTO resourcesDTO) {
        return service.create(resourcesDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ResourcesDTO updateResources(@RequestBody ResourcesDTO resourcesDTO) {
        return service.update(resourcesDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteResources(@RequestBody ResourcesDTO resourcesDTO) {
        return service.delete(resourcesDTO);
    }

}
