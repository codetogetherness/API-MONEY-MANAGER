package com.backend_api.money_manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
public class Payment implements Serializable {

    @Id
    private String id;

    private String name;
    private String icon;
    private String code;

    @ManyToOne
    @JoinColumn(name = "payment_category_id")
    private PaymentCategory paymentCategory;
}
