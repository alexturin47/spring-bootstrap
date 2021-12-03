package com.example.bootcrud.dao;

import com.example.bootcrud.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao  {
    User findByEmail(String email);
    List<User> index();
    void save(User user);
    User read(Long  id);
    void update(Long id, User user);
    void delete(Long id);
}
