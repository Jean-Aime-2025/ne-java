package com.ne.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Deduction {
    @Id
    private String code;

    @Column(nullable = false)
    private String deductionName;

    @Column(nullable = false)
    private double percentage;
}
