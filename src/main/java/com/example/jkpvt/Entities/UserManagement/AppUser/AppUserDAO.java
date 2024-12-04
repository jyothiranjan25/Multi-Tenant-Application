package com.example.jkpvt.Entities.UserManagement.AppUser;

import java.util.List;

public interface AppUserDAO {
    List<AppUser> get(AppUserDTO appUserDTO);
}
