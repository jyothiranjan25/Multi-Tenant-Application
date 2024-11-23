package com.example.jkpvt.Entities.UserManagement.UserGroup;


import com.example.jkpvt.Core.Json.JsonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userGroup")
public class UserGroupController {

    private final UserGroupService service;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    private List<UserGroupDTO> getAll(@RequestParam Map<String, String> queryParams) {
        UserGroupDTO userGroupDTO = JsonMap.toDto(queryParams, UserGroupDTO.class);
        return service.getAll(userGroupDTO);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private List<UserGroupDTO> get(@RequestParam java.util.Map<String, String> queryParams) {
        UserGroupDTO userGroupDTO = JsonMap.toDto(queryParams, UserGroupDTO.class);
        return service.get(userGroupDTO);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private UserGroupDTO create(@RequestBody UserGroupDTO userGroupDTO) {
        return service.create(userGroupDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    private UserGroupDTO update(@RequestBody UserGroupDTO userGroupDTO) {
        return service.update(userGroupDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    private String delete(@RequestBody UserGroupDTO userGroupDTO) {
        return service.delete(userGroupDTO);
    }
}
