package com.example.javaspringauthentication.services.mapper;

import com.example.javaspringauthentication.entities.User;
import com.example.javaspringauthentication.services.dto.LoginCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    public LoginCreateDTO userToLoginCreateDTO(User user);
    public User loginCreateDTOToUser(LoginCreateDTO loginCreateDTO);

}
