package com.example.demo;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
}
