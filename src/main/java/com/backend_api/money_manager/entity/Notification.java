package com.backend_api.money_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Notification implements Serializable {
    @Id
    private String id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String detail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
