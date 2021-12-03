package com.example.bootcrud.dao;


import com.example.bootcrud.entities.Role;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface RoleDao {
    Role findByname(String name);
    HashSet<Role> index();
    void save(Role role);
    Role read(Long id);
    void update(Long id, Role role);
    void delete(Long id);
}
