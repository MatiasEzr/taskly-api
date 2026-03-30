package com.matias.taskly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    // STRING is used to store "LOW", "MEDIUM", "HIGH" instead of 0, 1, 2
    // Avoids data corruption if enum order changes in the future
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    private Boolean completed = false;

    // Automatically set in @PrePersist, never comes from the client
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Only set when completed changes to true, handled by the Service layer
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "date_limit")
    private LocalDateTime dateLimit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

    }

}