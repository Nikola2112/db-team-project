package com.example.duallo.service;

import dto.RoleDTO;
import com.example.duallo.entity.Role;
import com.example.duallo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(RoleDTO roleDTO) {
        Role role = Role.builder()
                .name(roleDTO.getName())
                .description(roleDTO.getDescription()).build();
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

    @Transactional
    public Role updateRole(Long id, RoleDTO roleDetails) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            Role existingRole = roleOptional.get();
            existingRole.setName(roleDetails.getName());
            existingRole.setDescription(roleDetails.getDescription());
            return roleRepository.save(existingRole);
        } else {
            throw new IllegalArgumentException("Role not found with id: " + id);
        }
    }

    public void deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Role not found with id: " + id);
        }
    }
}
