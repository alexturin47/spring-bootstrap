package com.example.bootcrud.services;

import com.example.bootcrud.entities.Role;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public interface RoleService {

    Role findByname(String rolename);

    HashSet<Role> index();

    void save(Role role);

    Role read(Long id);

    void update(Role role);

    void delete(Long id);

    HashSet<Role> getRoleSet(String[] roles);
}
