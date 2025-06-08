package com.example.software.service;

import com.example.software.dto.ColumnDTO;
import com.example.software.entity.Column;
import com.example.software.repository.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ColumnService {
    private final ColumnRepository columnRepository;

    @Autowired
    public ColumnService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    public Column createColumn(ColumnDTO columnDTO) {
        Optional<Column> existing = columnRepository.findByTitle(columnDTO.getTitle());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Column with title '" + columnDTO.getTitle() + "' already exists");
        }

        Column column = Column.builder()
                .title(columnDTO.getTitle())
                .build();
        return columnRepository.save(column);
    }

    public List<Column> getAllColumns() {
        return StreamSupport.stream(columnRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Column getColumnById(Long id) {
        return columnRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Column not found"));
    }

    @Transactional
    public Column updateColumn(Long id, ColumnDTO columnDTO) {
        Column column = columnRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Column not found with id: " + id));
        column.setTitle(columnDTO.getTitle());
        return columnRepository.save(column);
    }

    public void deleteColumn(Long id) {
        if (columnRepository.existsById(id)) {
            columnRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Column not found with id: " + id);
        }
    }
}
