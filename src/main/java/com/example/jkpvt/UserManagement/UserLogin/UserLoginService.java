package com.example.jkpvt.UserManagement.UserLogin;

import com.example.jkpvt.UserManagement.AppUser.AppUserDAO;
import com.example.jkpvt.UserManagement.AppUser.AppUserDTO;
import com.example.jkpvt.UserManagement.AppUser.AppUserListener;
import com.example.jkpvt.UserManagement.AppUser.AppUserService;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRoles;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesDAO;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesDTO;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesService;
import com.example.jkpvt.UserManagement.Roles.RolesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUserDTO appUserDTO = new AppUserDTO();
            appUserDTO.setUserName(username);
            List<AppUserDTO> appUserDTOList = applicationContext.getBean(AppUserService.class).get(appUserDTO);
            if (appUserDTOList.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }

            return User.withUsername(appUserDTOList.getFirst().getUserName())
                    .username(appUserDTOList.getFirst().getUserName())
                    .password(appUserDTOList.getFirst().getPassword())
                    .disabled(!appUserDTOList.getFirst().getIsActive())
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
