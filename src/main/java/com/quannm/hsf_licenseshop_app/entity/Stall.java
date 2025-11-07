package com.quannm.hsf_licenseshop_app.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name = "stall")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "shop_id", nullable = false)
    Long shopId;

    @Column(name = "stall_name", nullable = false, length = 100)
    String stallName;

    @Column(name = "business_type", length = 50)
    String businessType;

    @Column(name = "stall_category", length = 50)
    String stallCategory;

    @Column(name = "discount_percentage")
    Double discountPercentage;

    @Column(name = "short_description", length = 500)
    String shortDescription;

    @Column(name = "detailed_description", columnDefinition = "TEXT")
    String detailedDescription;

    @Lob
    @Column(name = "stall_image_data", columnDefinition = "LONGBLOB")
    byte[] stallImageData;

    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    String status = "PENDING";

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    boolean isActive = false;

    @Column(name = "approval_reason", columnDefinition = "TEXT")
    String approvalReason;

    @Column(name = "approved_at")
    Instant approvedAt;

    @Column(name = "approved_by")
    Long approvedBy;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "is_delete", nullable = false)
    private boolean isDelete = false;

    @Transient
    @Builder.Default
    int productCount = 0;
    
    @Transient
    @Builder.Default
    String priceRange = "";
}
