package com.example.jkpvt.UserManagement.AppUser;

import com.example.jkpvt.Core.JsonUtil.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appUser")
public class AppUserController {

    private final AppUserService appUserService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<AppUserDTO> getAppUser(@RequestParam Map<String, String> queryParams) {
        AppUserDTO appUserDTO = MapUtils.toDto(queryParams, AppUserDTO.class);
        return appUserService.get(appUserDTO);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public AppUserDTO createAppUser(@RequestBody AppUserDTO appUserDTO) {
        return appUserService.createAppUser(appUserDTO);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public AppUserDTO updateAppUser(@RequestBody AppUserDTO appUserDTO) {
        return appUserService.updateAppUser(appUserDTO);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteAppUser(@RequestBody AppUserDTO appUserDTO) {
        return appUserService.deleteAppUser(appUserDTO);
    }
}
