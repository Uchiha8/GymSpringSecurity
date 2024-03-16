package com.epam.finalDemo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.Temporal;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TrainingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
}
