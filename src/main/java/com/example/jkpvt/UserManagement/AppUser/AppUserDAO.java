package com.example.jkpvt.UserManagement.AppUser;

import java.util.List;

public interface AppUserDAO {
    List<AppUserDTO> get(AppUserDTO appUserDTO);
}
