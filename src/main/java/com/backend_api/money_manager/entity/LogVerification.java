package com.backend_api.money_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class LogVerification implements Serializable {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @Column(name = "code")
    private String code;

//    private LocalDate expired; //jangan lupa dirubah ke Localdatetime

    private LocalDateTime expired;

    private Boolean isVerify = false;

    @Column(name = "created_at", columnDefinition = "datetime", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "datetime")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
