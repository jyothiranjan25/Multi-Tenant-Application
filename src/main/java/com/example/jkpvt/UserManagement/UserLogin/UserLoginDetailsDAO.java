package com.example.jkpvt.UserManagement.UserLogin;

import java.util.List;

public interface UserLoginDetailsDAO {
    List<UserLoginDetailsDTO> get(UserLoginDetailsDTO userLoginDetailsDTO);
}