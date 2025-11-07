package com.quannm.hsf_licenseshop_app.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name = "shop")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    Long userId;

    @Column(name = "shop_name", nullable = false, length = 100)
    String shopName;

    @Column(name = "cccd")
    String cccd;

    @Column(name = "bank_account_id", nullable = false)
    Long bankAccountId;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    Status status = Status.ACTIVE;

    public enum Status {
        ACTIVE, INACTIVE
    }

}
