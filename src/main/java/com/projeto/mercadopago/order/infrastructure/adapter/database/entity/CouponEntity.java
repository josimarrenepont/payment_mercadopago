package com.projeto.mercadopago.order.infrastructure.adapter.database.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "coupons")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private BigDecimal percentage;

    @Column(nullable = false)
    private Instant expirationDate;

    @Column(nullable = false)
    private boolean active;

    public BigDecimal getPercentage() { return percentage; }
    public String getCode() { return code; }
    public Instant getExpirationDate() { return expirationDate; }
    public boolean isActive() { return active; }
}
