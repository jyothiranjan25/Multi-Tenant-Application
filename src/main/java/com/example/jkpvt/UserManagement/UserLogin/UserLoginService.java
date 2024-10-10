package com.example.jkpvt.UserManagement.UserLogin;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.ExceptionHandling.RoleNotFoundExemption;
import com.example.jkpvt.UserManagement.AppUser.AppUserDAO;
import com.example.jkpvt.UserManagement.AppUser.AppUserDTO;
import com.example.jkpvt.UserManagement.AppUser.AppUserListener;
import com.example.jkpvt.UserManagement.AppUser.AppUserService;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRoles;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesDAO;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesDTO;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesService;
import com.example.jkpvt.UserManagement.Roles.RolesDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
public class UserLoginService implements UserDetailsService, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserLoginService.applicationContext = applicationContext;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, RoleNotFoundExemption {
        try {
            AppUserDTO appUserDTO = new AppUserDTO();
            appUserDTO.setUserName(username);
            List<AppUserDTO> appUserDTOList = applicationContext.getBean(AppUserService.class).get(appUserDTO);
            if (appUserDTOList.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }

            if (appUserDTOList.getFirst().getAppUserRoles().isEmpty()) {
                throw new RoleNotFoundExemption("Role not found");
            }
            // Store the first AppUserDTO in the session
            storeUserLoginDetails(appUserDTOList.getFirst());

            return User.withUsername(appUserDTOList.getFirst().getUserName())
                    .username(appUserDTOList.getFirst().getUserName())
                    .password(appUserDTOList.getFirst().getPassword())
                    .disabled(!appUserDTOList.getFirst().getIsActive())
                    .build();
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }catch (RoleNotFoundExemption e){
            throw new RoleNotFoundExemption(e.getMessage());
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    public void storeUserLoginDetails(AppUserDTO AppUserDTO) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        session.setAttribute("appUser", AppUserDTO);
    }
}
