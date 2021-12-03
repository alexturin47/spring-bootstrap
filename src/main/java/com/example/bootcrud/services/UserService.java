package com.example.bootcrud.services;

import com.example.bootcrud.dto.UserDto;
import com.example.bootcrud.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto) throws ValidationException;
    void deleteUser(Long id);
 //   UserDto findByUsername(String username);

    UserDto findByEmail(String email);

    List<UserDto> findAll();
    void updateUser(UserDto userDto) throws ValidationException;

}
