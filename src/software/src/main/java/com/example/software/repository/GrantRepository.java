package com.example.duallo.repository;

import com.example.duallo.entity.Grant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GrantRepository extends CrudRepository<Grant, Long> {
    List<Grant> findByRoleId(Long roleId);
    List<Grant> findByPermissionId(Long permissionId);
    Optional<Grant> findByRoleIdAndPermissionId(Long roleId, Long permissionId);

}
