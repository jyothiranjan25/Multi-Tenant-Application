package com.example.jkpvt.UserManagement.UserLogin;

import com.example.jkpvt.UserManagement.AppUser.AppUserDAO;
import com.example.jkpvt.UserManagement.AppUser.AppUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLoginService implements UserDetailsService {

    private final AppUserDAO appUserDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUserDTO appUserDTO = new AppUserDTO();
            appUserDTO.setUserName(username);
            List<AppUserDTO> appUserDTOList = appUserDAO.get(appUserDTO);
            if (appUserDTOList.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            return User.withUsername(appUserDTOList.getFirst().getUserName())
                    .password(appUserDTOList.getFirst().getPassword())
                    .roles(appUserDTOList.getFirst().getIsAdmin() ? "ADMIN" : "USER")
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
