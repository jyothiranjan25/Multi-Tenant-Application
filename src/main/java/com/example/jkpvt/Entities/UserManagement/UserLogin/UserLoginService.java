package com.example.jkpvt.Entities.UserManagement.UserLogin;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.SessionStorageData.SessionStorageUtil;
import com.example.jkpvt.Entities.UserManagement.AppUser.AppUserDTO;
import com.example.jkpvt.Entities.UserManagement.AppUser.AppUserMessages;
import com.example.jkpvt.Entities.UserManagement.AppUser.AppUserService;
import com.example.jkpvt.Entities.UserManagement.Roles.RolesMessages;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginService implements UserDetailsService, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserLoginService.applicationContext = applicationContext;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setUserName(username);
        List<AppUserDTO> appUserDTOList = applicationContext.getBean(AppUserService.class).get(appUserDTO);
        if (appUserDTOList.isEmpty()) {
            throw new CommonException(AppUserMessages.USER_NOT_FOUND);
        }

        if (appUserDTOList.getFirst().getAppUserRoles().isEmpty()) {
            throw new CommonException(RolesMessages.ROLE_NOT_FOUND);
        }
        // Store the first AppUserDTO in the session
        SessionStorageUtil.setAppUser(appUserDTOList.getFirst());
        SessionStorageUtil.setUserEmail(appUserDTOList.getFirst().getEmail());
        SessionStorageUtil.setUserName(appUserDTOList.getFirst().getUserName());

        return User.withUsername(appUserDTOList.getFirst().getUserName())
                .username(appUserDTOList.getFirst().getUserName())
                .password(appUserDTOList.getFirst().getPassword())
                .disabled(!appUserDTOList.getFirst().getIsActive())
                .build();
    }
}
