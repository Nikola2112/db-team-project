package com.example.duallo.service;

import com.example.duallo.entity.Grant;
import com.example.duallo.entity.Permission;
import com.example.duallo.entity.Role;
import com.example.duallo.repository.GrantRepository;
import com.example.duallo.repository.PermissionRepository;
import com.example.duallo.repository.RoleRepository;
import dto.GrantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class GrantService {

    private final GrantRepository grantRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public GrantService(GrantRepository grantRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.grantRepository = grantRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Grant createGrant(GrantDTO grantDTO) {
        Role role = roleRepository.findById(grantDTO.getRoleId()).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Permission permission = permissionRepository.findById(grantDTO.getPermissionId()).orElseThrow(() -> new IllegalArgumentException("Permission not found"));
        Optional<Grant> existingGrant = grantRepository.findByRoleIdAndPermissionId(grantDTO.getRoleId(), grantDTO.getPermissionId());
        if (existingGrant.isPresent()) {
            throw new IllegalArgumentException("Grant already exists for the given role and permission");
        }
        Grant grant = Grant.builder()
                .role(role)
                .permission(permission)
                .build();
        return grantRepository.save(grant);
    }

    public List<Grant> getAllGrants() {
        return StreamSupport.stream(grantRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Grant getGrantById(Long id) {
        return grantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Grant not found"));
    }

    @Transactional
    public Grant updateGrant(Long id, GrantDTO grantDTO) {
        Grant grant = grantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Grant not found with id: " + id));
        Role role = roleRepository.findById(grantDTO.getRoleId()).orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + grantDTO.getRoleId()));
        Permission permission = permissionRepository.findById(grantDTO.getPermissionId()).orElseThrow(() -> new IllegalArgumentException("Permission not found with id: " + grantDTO.getPermissionId()));
        grant.setRole(role);
        grant.setPermission(permission);
        return grantRepository.save(grant);
    }

    public void deleteGrant(Long id) {
        if (grantRepository.existsById(id)) {
            grantRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Grant not found with id: " + id);
        }
    }

    public List<Grant> getGrantsByRoleId(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new IllegalArgumentException("Role not found with id: " + roleId);
        }
        return grantRepository.findByRoleId(roleId);
    }

    public List<Grant> getGrantsByPermissionId(Long permissionId) {
        if (!permissionRepository.existsById(permissionId)) {
            throw new IllegalArgumentException("Permission not found with id: " + permissionId);
        }
        return grantRepository.findByPermissionId(permissionId);
    }
}

