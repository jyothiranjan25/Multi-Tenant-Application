package com.example.jkpvt.Entities.UserManagement.UserLogin;

import java.util.List;

public interface UserLoginDetailsDAO {
    List<UserLoginDetails> get(UserLoginDetailsDTO userLoginDetailsDTO);
}