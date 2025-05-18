package com.example.duallo.service;

import dto.PermissionDTO;
import com.example.duallo.entity.Permission;
import com.example.duallo.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission createPermission(PermissionDTO permissionDTO) {
        Optional<Permission> existingPermission = permissionRepository.findByAction(permissionDTO.getAction());
        if (existingPermission.isPresent()) {
            throw new IllegalArgumentException("Permission with action '" + permissionDTO.getAction() + "' already exists");
        }
        Permission permission = Permission.builder()
                .action(permissionDTO.getAction())
                .build();
        return permissionRepository.save(permission);
    }
    public List<Permission> getAllPermission() {
        return StreamSupport.stream(permissionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Permission not found"));
    }
    @Transactional
    public Permission updatePermission(Long id, PermissionDTO permissionDTO) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            permission.setAction(permissionDTO.getAction());
            return permissionRepository.save(permission);
        } else {
            throw new IllegalArgumentException("Permission not found with id: " + id);
        }
    }
    public void deletePermission(Long id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Permission not found with id: " + id);
        }
    }
}
