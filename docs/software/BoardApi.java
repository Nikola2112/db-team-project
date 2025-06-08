package com.example.software.api;

import com.example.software.dto.BoardDTO;
import com.example.software.dto.ErrorResponse;
import com.example.software.entity.Board;
import com.example.software.service.BoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardApi {

    private final BoardService boardService;

    @Autowired
    public BoardApi(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody BoardDTO dto) {
        Board created = boardService.createBoard(dto);
        if (StringUtils.isBlank(created.getName())) {
            return fail("Board name is required.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(boardService.getBoardById(id));
        } catch (Exception ex) {
            return fail(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Long id, @RequestBody BoardDTO dto) {
        try {
            Board updated = boardService.updateBoard(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            return fail(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        try {
            boardService.deleteBoard(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return fail(ex.getMessage());
        }
    }

    private ResponseEntity<ErrorResponse> fail(String msg) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(msg);
        return ResponseEntity.badRequest().body(error);
    }
}
