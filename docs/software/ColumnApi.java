package com.example.software.api;

import com.example.software.dto.ColumnDTO;
import com.example.software.dto.ErrorResponse;
import com.example.software.entity.Column;
import com.example.software.service.ColumnService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/columns")
public class ColumnApi {

    private final ColumnService columnService;

    @Autowired
    public ColumnApi(ColumnService columnService) {
        this.columnService = columnService;
    }

    @PostMapping
    public ResponseEntity<?> createColumn(@RequestBody ColumnDTO dto) {
        try {
            Column created = columnService.createColumn(dto);
            if (StringUtils.isBlank(created.getTitle())) {
                return bad("Column title must not be blank.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception ex) {
            return bad(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Column>> getAllColumns() {
        return ResponseEntity.ok(columnService.getAllColumns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getColumn(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(columnService.getColumnById(id));
        } catch (Exception ex) {
            return bad(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateColumn(@PathVariable Long id, @RequestBody ColumnDTO dto) {
        try {
            Column updated = columnService.updateColumn(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            return bad(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteColumn(@PathVariable Long id) {
        try {
            columnService.deleteColumn(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return bad(ex.getMessage());
        }
    }

    private ResponseEntity<ErrorResponse> bad(String msg) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(msg);
        return ResponseEntity.badRequest().body(error);
    }
}
