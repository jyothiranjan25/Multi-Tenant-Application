package com.example.jkpvt.Entities.UserManagement.Roles;

import com.example.jkpvt.Core.Json.JsonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RolesController {

    private final RolesService rolesService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<RolesDTO> Get(@RequestParam Map<String, String> queryParams) {
        RolesDTO rolesDTO = JsonMap.toDto(queryParams, RolesDTO.class);
        return rolesService.get(rolesDTO);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RolesDTO Create(@RequestBody RolesDTO rolesDTO) {
        return rolesService.create(rolesDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public RolesDTO Update(@RequestBody RolesDTO rolesDTO) {
        return rolesService.update(rolesDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String Delete(@RequestBody RolesDTO rolesDTO) {
        return rolesService.delete(rolesDTO);
    }

}
