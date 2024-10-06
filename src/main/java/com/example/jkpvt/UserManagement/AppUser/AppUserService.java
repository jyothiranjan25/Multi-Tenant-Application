package com.example.jkpvt.UserManagement.AppUser;

import com.example.jkpvt.Core.ExceptionHandling.CommonException;
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

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public List<AppUserDTO> get(AppUserDTO appUserDTO) {
            return appUserDAO.get(appUserDTO);
    }

    @Transactional
    public AppUserDTO createAppUser(AppUserDTO appUserDTO) {
        try{
            if(appUserDTO.getPassword() != null){
                appUserDTO.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
            }else{
                appUserDTO.setPassword(passwordEncoder.encode(appUserDTO.getUserName()));
            }
            AppUser appUser = mapper.map(appUserDTO);
            repository.save(appUser);
            return mapper.map(appUser);
        }catch(Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public AppUserDTO updateAppUser(AppUserDTO appUserDTO) {
        try{
            AppUser appUser = getById(appUserDTO.getId());

            if(appUserDTO.getUserName() != null){
                appUser.setUserName(appUserDTO.getUserName());
            }
            if(appUserDTO.getEmail() != null){
                appUser.setEmail(appUserDTO.getEmail());
            }
            if(appUserDTO.getPassword() != null && !appUserDTO.getPassword().isEmpty()){
                appUserDTO.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
            }
            if(appUserDTO.getIsAdmin() != null){
                appUser.setIsAdmin(appUserDTO.getIsAdmin());
            }
            if(appUserDTO.getIsActive() != null){
                appUser.setIsActive(appUserDTO.getIsActive());
            }
            appUser = repository.save(appUser);
            return mapper.map(appUser);
        }catch(Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional
    public String deleteAppUser(AppUserDTO appUserDTO) {
        try{
            if(repository.existsById(appUserDTO.getId())){
                repository.deleteById(appUserDTO.getId());
                return "Data deleted successfully";
            }else{
                throw new CommonException("Data not found");
            }
        }catch(Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    public AppUser getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CommonException("User with id: " + id + " not found"));
    }
}
