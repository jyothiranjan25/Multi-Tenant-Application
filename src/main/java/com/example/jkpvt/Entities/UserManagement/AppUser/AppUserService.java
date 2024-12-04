package com.example.jkpvt.Entities.UserManagement.AppUser;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
import com.example.jkpvt.Core.Messages.CommonMessages;
import com.example.jkpvt.Core.Messages.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;
    private final AppUserMapper mapper;
    private final AppUserDAO appUserDAO;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<AppUserDTO> get(AppUserDTO appUserDTO) {
        List<AppUser> appUser = appUserDAO.get(appUserDTO);
        return mapper.map(appUser);
    }

    @Transactional
    public AppUserDTO createAppUser(AppUserDTO appUserDTO) {

        if (appUserDTO.getPassword() != null) {
            appUserDTO.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        } else {
            appUserDTO.setPassword(passwordEncoder.encode(appUserDTO.getUserName()));
        }
        AppUser appUser = mapper.map(appUserDTO);
        repository.save(appUser);
        return mapper.map(appUser);
    }

    @Transactional
    public AppUserDTO updateAppUser(AppUserDTO appUserDTO) {
        AppUser appUser = getById(appUserDTO.getId());
        updateAppUserData(appUserDTO, appUser);
        appUser = repository.save(appUser);
        return mapper.map(appUser);
    }

    @Transactional
    public String deleteAppUser(AppUserDTO appUserDTO) {
        if (repository.existsById(appUserDTO.getId())) {
            repository.deleteById(appUserDTO.getId());
            return Messages.getMessage(CommonMessages.DATA_DELETE_SUCCESS).getMessage();
        } else {
            throw new CommonException(AppUserMessages.USER_NAME_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public AppUser getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CommonException(AppUserMessages.USER_NAME_NOT_FOUND));
    }

    private void updateAppUserData(AppUserDTO appUserDTO, AppUser appUser) {
        if (appUserDTO.getUserName() != null) {
            appUser.setUserName(appUserDTO.getUserName());
        }
        if (appUserDTO.getEmail() != null) {
            appUser.setEmail(appUserDTO.getEmail());
        }
        if (appUserDTO.getPassword() != null && !appUserDTO.getPassword().isEmpty()) {
            appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        }
        if (appUserDTO.getIsAdmin() != null) {
            appUser.setIsAdmin(appUserDTO.getIsAdmin());
        }
        if (appUserDTO.getIsActive() != null) {
            appUser.setIsActive(appUserDTO.getIsActive());
        }
    }
}
