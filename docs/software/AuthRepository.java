package com.example.software.repository;

import com.example.software.entity.Auth;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthRepository extends CrudRepository<Auth, Long> {
    List<Auth> findByBoardId(Long boardId);
    List<Auth> findByColumnId(Long columnId);
    Optional<Auth> findByBoardIdAndColumnId(Long boardId, Long columnId);
}
