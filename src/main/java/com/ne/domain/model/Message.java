package com.ne.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String monthYear;

    @Column(nullable = false)
    private LocalDateTime sentDate;
}
