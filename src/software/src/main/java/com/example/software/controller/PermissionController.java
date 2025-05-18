package com.example.duallo.controller;

import dto.ErrorResponse;
import dto.PermissionDTO;
import com.example.duallo.service.PermissionService;
import com.example.duallo.entity.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @PostMapping("/permissions")
    public ResponseEntity<?> createPermission(@RequestBody PermissionDTO permissionDTO) {
        try {
            Permission permission = permissionService.createPermission(permissionDTO);
            if (StringUtils.isBlank(permission.getAction())) {
                ErrorResponse badRequestResponse = new ErrorResponse();
                badRequestResponse.setMessage("permission action is required and cannot be blank");
                return ResponseEntity.badRequest().body(badRequestResponse);
            }
            return new ResponseEntity<>(permission, HttpStatus.CREATED);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermission();
        return ResponseEntity.ok(permissions);
    }
    @GetMapping("/permissions/{id}")
    public ResponseEntity<?> getPermissionById(@PathVariable Long id) {
        try {
            Permission permission = permissionService.getPermissionById(id);
            return ResponseEntity.ok(permission);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
    @PutMapping("/permissions/{id}")
    public ResponseEntity<?> updatePermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO) {
        try {
            Permission updatedPermission = permissionService.updatePermission(id, permissionDTO);
            return ResponseEntity.ok(updatedPermission);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Long id) {
        try {
            permissionService.deletePermission(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
}
