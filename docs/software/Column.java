package com.example.software.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "column_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
}
