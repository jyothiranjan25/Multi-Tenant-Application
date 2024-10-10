package com.example.jkpvt.UserManagement.UserLogin;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.UserManagement.AppUser.AppUserDTO;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesDTO;
import com.example.jkpvt.UserManagement.Roles.RolesDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLoginDetailsService {

    private final UserLoginDetailsDAO userLoginDetailsDAO;
    private final UserLoginDetailsMapper mapper;
    private final UserLoginDetailsRepository repository;

    @Transactional(readOnly = true)
    public List<UserLoginDetailsDTO> get(UserLoginDetailsDTO userLoginDetailsDTO) {
        return userLoginDetailsDAO.get(userLoginDetailsDTO);
    }

    @Transactional
    public UserLoginDetailsDTO create(UserLoginDetailsDTO userLoginDetailsDTO) {
        try {
            UserLoginDetails userLoginDetails = mapper.map(userLoginDetailsDTO);
            userLoginDetails = repository.save(userLoginDetails);
            return mapper.map(userLoginDetails);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    public void storeUserLoginDetails(AppUserRolesDTO appUserRolesDTO) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        session.setAttribute("userGroup", appUserRolesDTO.getUserGroup());
        session.setAttribute("roleId", appUserRolesDTO.getRoles().getId());
    }

    public void test() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        String userGroup = (String) session.getAttribute("userGroup");
        Long roleId = (Long) session.getAttribute("roleId");
        AppUserDTO appUserDTO = (AppUserDTO) session.getAttribute("appUser");

        System.out.println("userGroup: " + userGroup);
        System.out.println("roleId: " + roleId);
        System.out.println("appUserDTO: " + appUserDTO);
    }
}