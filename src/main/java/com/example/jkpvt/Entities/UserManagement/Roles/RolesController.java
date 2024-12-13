package com.example.jkpvt.Entities.UserManagement.Roles;

import com.example.jkpvt.Core.Json.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RolesController {

    private final RolesService rolesService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private RolesDTO Get(@QueryParams RolesDTO rolesDTO) {
        List<RolesDTO> roles = rolesService.get(rolesDTO);
        rolesDTO.setData(roles);
        return rolesDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private RolesDTO create(@RequestBody RolesDTO rolesDTO) {
        return rolesService.create(rolesDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    private RolesDTO update(@RequestBody RolesDTO rolesDTO) {
        return rolesService.update(rolesDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    private String delete(@RequestBody RolesDTO rolesDTO) {
        return rolesService.delete(rolesDTO);
    }

}
