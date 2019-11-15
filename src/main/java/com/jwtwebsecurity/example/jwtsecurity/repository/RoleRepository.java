package com.jwtwebsecurity.example.jwtsecurity.repository;

import java.util.Optional;

import com.jwtwebsecurity.example.jwtsecurity.model.Role;
import com.jwtwebsecurity.example.jwtsecurity.model.RoleName;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(RoleName roleName);
}