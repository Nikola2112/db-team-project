package com.example.duallo.controller;

import com.example.duallo.entity.Role;
import com.example.duallo.service.RoleService;
import dto.ErrorResponse;
import dto.RoleDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public ResponseEntity<?> createRole(@RequestBody RoleDTO roleDTO) {
        Role role = roleService.createRole(roleDTO);
        if (StringUtils.isBlank(role.getName())) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage("Role name is required and cannot be blank");
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        try {
            Role role = roleService.getRoleById(id);
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDetails) {
        try {
            Role updatedRole = roleService.updateRole(id, roleDetails);
            return ResponseEntity.ok(updatedRole);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
}
