package com.example.duallo.controller;

import dto.ErrorResponse;
import dto.GrantDTO;
import com.example.duallo.entity.Grant;
import com.example.duallo.service.GrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grants")
public class GrantController {
    private final GrantService grantService;

    @Autowired
    public GrantController(GrantService grantService) {
        this.grantService = grantService;
    }
    @PostMapping
    public ResponseEntity<?> createGrant(@RequestBody GrantDTO grantDTO) {
        try {
            Grant grant = grantService.createGrant(grantDTO);
            return new ResponseEntity<>(grant, HttpStatus.CREATED);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
    @GetMapping
    public ResponseEntity<List<Grant>> getAllGrants() {
        List<Grant> grants = grantService.getAllGrants();
        return ResponseEntity.ok(grants);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getGrantById(@PathVariable Long id) {
        try {
            Grant grant = grantService.getGrantById(id);
            return ResponseEntity.ok(grant);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGrant(@PathVariable Long id, @RequestBody GrantDTO grantDTO) {
        try {
            Grant updatedGrant = grantService.updateGrant(id, grantDTO);
            return ResponseEntity.ok(updatedGrant);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGrant(@PathVariable Long id) {
        try {
            grantService.deleteGrant(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
    @GetMapping("/by-role/{roleId}")
    public ResponseEntity<?> getGrantsByRoleId(@PathVariable Long roleId) {
        try {
            List<Grant> grants = grantService.getGrantsByRoleId(roleId);
            return ResponseEntity.ok(grants);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
    @GetMapping("/by-permission/{permissionId}")
    public ResponseEntity<?> getGrantsByPermissionId(@PathVariable Long permissionId) {
        try {
            List<Grant> grants = grantService.getGrantsByPermissionId(permissionId);
            return ResponseEntity.ok(grants);
        } catch (Exception e) {
            ErrorResponse badRequestResponse = new ErrorResponse();
            badRequestResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(badRequestResponse);
        }
    }
}
