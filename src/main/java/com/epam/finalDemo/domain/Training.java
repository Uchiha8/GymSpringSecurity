package com.epam.finalDemo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Trainee trainee;
    @ManyToOne(cascade = CascadeType.ALL)
    private Trainer trainer;
    @Column(nullable = false)
    private String trainingName;
    @ManyToOne(cascade = CascadeType.ALL)
    private TrainingType trainingType;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date trainingDate;
    @Column(nullable = false)
    private Duration duration;
}
