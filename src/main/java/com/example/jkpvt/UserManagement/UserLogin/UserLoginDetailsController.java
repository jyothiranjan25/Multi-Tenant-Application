package com.example.jkpvt.UserManagement.UserLogin;

import com.example.jkpvt.Core.JsonUtil.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userLoginDetails")
public class UserLoginDetailsController {

    private final UserLoginDetailsService service;

    @RequestMapping(value = "/get" , method = RequestMethod.GET)
    public List<UserLoginDetailsDTO> getAllUserLoginDetails(@RequestParam Map<String, String> queryParams){
        UserLoginDetailsDTO userLoginDetailsDTO = MapUtils.toDto(queryParams, UserLoginDetailsDTO.class);
        return service.get(userLoginDetailsDTO);
    }

    @RequestMapping(value = "/test" , method = RequestMethod.POST)
    public void test(){
        service.test();
    }
}