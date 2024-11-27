package com.example.jkpvt.Entities.UserManagement.AppUser;

import com.example.jkpvt.Core.Json.QueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appUser")
public class AppUserController {

    private final AppUserService appUserService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private AppUserDTO get(@QueryParams AppUserDTO appUserDTO) {
        List<AppUserDTO> appUsers = appUserService.get(appUserDTO);
        appUserDTO.setData(appUsers);
        return appUserDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private AppUserDTO create(@RequestBody AppUserDTO appUserDTO) {
        return appUserService.createAppUser(appUserDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    private AppUserDTO update(@RequestBody AppUserDTO appUserDTO) {
        return appUserService.updateAppUser(appUserDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    private String delete(@RequestBody AppUserDTO appUserDTO) {
        return appUserService.deleteAppUser(appUserDTO);
    }
}
