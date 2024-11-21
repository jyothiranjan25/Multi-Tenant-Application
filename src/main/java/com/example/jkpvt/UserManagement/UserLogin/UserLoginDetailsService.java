package com.example.jkpvt.UserManagement.UserLogin;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.SessionStorageData.SessionStorageUtil;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLoginDetailsService {

    private final UserLoginDetailsDAO userLoginDetailsDAO;
    private final UserLoginDetailsMapper mapper;
    private final UserLoginDetailsRepository repository;

    @Transactional(readOnly = true)
    public List<UserLoginDetailsDTO> get(UserLoginDetailsDTO userLoginDetailsDTO) {
        List<UserLoginDetails> userLoginDetails = userLoginDetailsDAO.get(userLoginDetailsDTO);
        return mapper.map(userLoginDetails);
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
        SessionStorageUtil.setUserGroup(appUserRolesDTO.getUserGroup());
        SessionStorageUtil.setAppUserRole(appUserRolesDTO);
    }
}
