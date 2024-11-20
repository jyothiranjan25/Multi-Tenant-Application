package com.example.jkpvt.UserManagement.UserLogin;

import com.example.jkpvt.Core.Json.JsonMap;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userLoginDetails")
public class UserLoginDetailsController {

    private final UserLoginDetailsService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<UserLoginDetailsDTO> getAllUserLoginDetails(@RequestParam Map<String, String> queryParams) {
        UserLoginDetailsDTO userLoginDetailsDTO = JsonMap.toDto(queryParams, UserLoginDetailsDTO.class);
        return service.get(userLoginDetailsDTO);
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public void storeUserLoginDetails(@RequestBody AppUserRolesDTO appUserRolesDTO) {
        service.storeUserLoginDetails(appUserRolesDTO);
    }
}
