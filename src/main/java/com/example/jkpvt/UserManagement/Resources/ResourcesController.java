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

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<ResourcesDTO> get(@RequestParam Map<String, String> queryParams) {
        ResourcesDTO resourcesDTO = MapUtils.toDto(queryParams, ResourcesDTO.class);
        return service.get(resourcesDTO);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResourcesDTO create(@RequestBody ResourcesDTO resourcesDTO) {
        return service.create(resourcesDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ResourcesDTO update(@RequestBody ResourcesDTO resourcesDTO) {
        return service.update(resourcesDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestBody ResourcesDTO resourcesDTO) {
        return service.delete(resourcesDTO);
    }

}
