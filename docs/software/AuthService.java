package com.example.software.service;

import com.example.software.dto.AuthDTO;
import com.example.software.entity.Auth;
import com.example.software.entity.Board;
import com.example.software.entity.Column;
import com.example.software.repository.AuthRepository;
import com.example.software.repository.BoardRepository;
import com.example.software.repository.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;

    @Autowired
    public AuthService(AuthRepository authRepository, BoardRepository boardRepository, ColumnRepository columnRepository) {
        this.authRepository = authRepository;
        this.boardRepository = boardRepository;
        this.columnRepository = columnRepository;
    }

    public Auth createAuth(AuthDTO authDTO) {
        Board board = boardRepository.findById(authDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        Column column = columnRepository.findById(authDTO.getColumnId())
                .orElseThrow(() -> new IllegalArgumentException("Column not found"));

        Optional<Auth> existing = authRepository.findByBoardIdAndColumnId(authDTO.getBoardId(), authDTO.getColumnId());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Auth already exists for the given board and column");
        }

        Auth auth = Auth.builder()
                .board(board)
                .column(column)
                .build();

        return authRepository.save(auth);
    }

    public List<Auth> getAllAuths() {
        return StreamSupport.stream(authRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Auth getAuthById(Long id) {
        return authRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Auth not found"));
    }

    @Transactional
    public Auth updateAuth(Long id, AuthDTO authDTO) {
        Auth auth = authRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Auth not found with id: " + id));

        Board board = boardRepository.findById(authDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + authDTO.getBoardId()));
        Column column = columnRepository.findById(authDTO.getColumnId())
                .orElseThrow(() -> new IllegalArgumentException("Column not found with id: " + authDTO.getColumnId()));

        auth.setBoard(board);
        auth.setColumn(column);

        return authRepository.save(auth);
    }

    public void deleteAuth(Long id) {
        if (authRepository.existsById(id)) {
            authRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Auth not found with id: " + id);
        }
    }

    public List<Auth> getAuthsByBoardId(Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new IllegalArgumentException("Board not found with id: " + boardId);
        }
        return authRepository.findByBoardId(boardId);
    }

    public List<Auth> getAuthsByColumnId(Long columnId) {
        if (!columnRepository.existsById(columnId)) {
            throw new IllegalArgumentException("Column not found with id: " + columnId);
        }
        return authRepository.findByColumnId(columnId);
    }
}
