package com.example.jkpvt.UserManagement.UserLogin;

import java.util.List;

public interface UserLoginDetailsDAO {
    List<UserLoginDetails> get(UserLoginDetailsDTO userLoginDetailsDTO);
}