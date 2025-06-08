package com.example.software.repository;

import com.example.software.entity.Column;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ColumnRepository extends CrudRepository<Column, Long> {
    Optional<Column> findByTitle(String title);
}
