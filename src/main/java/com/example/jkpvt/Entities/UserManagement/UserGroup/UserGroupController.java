package com.example.jkpvt.Entities.UserManagement.UserGroup;


import com.example.jkpvt.Core.Json.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userGroup")
public class UserGroupController {

    private final UserGroupService service;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    private UserGroupDTO getAll(@QueryParams UserGroupDTO userGroupDTO) {
        List<UserGroupDTO> userGroups = service.getAll(userGroupDTO);
        userGroupDTO.setData(userGroups);
        return userGroupDTO;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private UserGroupDTO get(@QueryParams UserGroupDTO userGroupDTO) {
        List<UserGroupDTO> userGroups = service.get(userGroupDTO);
        userGroupDTO.setData(userGroups);
        return userGroupDTO;
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
