package com.backend_api.money_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class CategoryTransaction {
    @Id
    private String id;
    private String name;
    private String icon;
    private String description;
    private String amount;

    @Column(name = "created_at", columnDefinition = "datetime", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "datetime")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
