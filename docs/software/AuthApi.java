package com.example.software.api;

import com.example.software.dto.AuthDTO;
import com.example.software.dto.ErrorResponse;
import com.example.software.entity.Auth;
import com.example.software.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.util.List;

@RestController
@RequestMapping("/api/auths")
public class AuthApi {

    private final AuthService authService;

    @Autowired
    public AuthApi(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> createAuth(@RequestBody AuthDTO dto) {
        try {
            Auth auth = authService.createAuth(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(auth);
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Auth>> getAllAuths() {
        return ResponseEntity.ok(authService.getAllAuths());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuth(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(authService.getAuthById(id));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuth(@PathVariable Long id, @RequestBody AuthDTO dto) {
        try {
            return ResponseEntity.ok(authService.updateAuth(id, dto));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuth(@PathVariable Long id) {
        try {
            authService.deleteAuth(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    @GetMapping("/by-board/{boardId}")
    public ResponseEntity<?> getByBoard(@PathVariable Long boardId) {
        try {
            return ResponseEntity.ok(authService.getAuthsByBoardId(boardId));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    @GetMapping("/by-column/{columnId}")
    public ResponseEntity<?> getByColumn(@PathVariable Long columnId) {
        try {
            return ResponseEntity.ok(authService.getAuthsByColumnId(columnId));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    private ResponseEntity<ErrorResponse> fail(String msg) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(msg);
        return ResponseEntity.badRequest().body(error);
    }
}
