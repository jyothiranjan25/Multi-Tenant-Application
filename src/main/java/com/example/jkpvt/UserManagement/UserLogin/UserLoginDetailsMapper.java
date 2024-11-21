package com.example.jkpvt.UserManagement.UserLogin;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserLoginDetailsMapper {

    UserLoginDetails map(UserLoginDetailsDTO userLoginDetailsDTO);

    @InheritConfiguration
    List<UserLoginDetailsDTO> map(List<UserLoginDetails> userLoginDetails);

    @InheritInverseConfiguration
    UserLoginDetailsDTO map(UserLoginDetails userLoginDetails);
}