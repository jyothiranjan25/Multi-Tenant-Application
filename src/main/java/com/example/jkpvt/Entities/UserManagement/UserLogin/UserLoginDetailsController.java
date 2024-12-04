package com.example.jkpvt.Entities.UserManagement.UserLogin;

import com.example.jkpvt.Core.Json.QueryParams;
import com.example.jkpvt.Entities.UserManagement.AppUserRoles.AppUserRolesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userLoginDetails")
public class UserLoginDetailsController {

    private final UserLoginDetailsService service;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private UserLoginDetailsDTO getAllUserLoginDetails(@QueryParams UserLoginDetailsDTO userLoginDetailsDTO) {
        List<UserLoginDetailsDTO> userLoginDetails = service.get(userLoginDetailsDTO);
        userLoginDetailsDTO.setData(userLoginDetails);
        return userLoginDetailsDTO;
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    private void storeUserLoginDetails(@RequestBody AppUserRolesDTO appUserRolesDTO) {
        service.storeUserLoginDetails(appUserRolesDTO);
    }
}
