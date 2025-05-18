package com.example.duallo.repository;

import com.example.duallo.entity.Permission;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface PermissionRepository extends CrudRepository<Permission, Long> {
    Optional<Permission> findByAction(String action);
}
