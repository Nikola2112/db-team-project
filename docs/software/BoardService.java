package com.example.software.service;

import com.example.software.dto.BoardDTO;
import com.example.software.entity.Board;
import com.example.software.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board createBoard(BoardDTO boardDTO) {
        Board board = Board.builder()
                .name(boardDTO.getName())
                .description(boardDTO.getDescription())
                .build();
        return boardRepository.save(board);
    }

    public List<Board> getAllBoards() {
        return StreamSupport.stream(boardRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Board not found"));
    }

    @Transactional
    public Board updateBoard(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + id));
        board.setName(boardDTO.getName());
        board.setDescription(boardDTO.getDescription());
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Board not found with id: " + id);
        }
    }
}
