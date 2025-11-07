package com.quannm.hsf_licenseshop_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class) // Vô hiệu hóa Auditing
@Getter
@Setter
public abstract class BaseEntity {
    
    @Transient // Bỏ qua trường này, không ánh xạ vào DB
    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;
    
    //@CreatedBy
    @Transient // Bỏ qua trường này, không ánh xạ vào DB
    @Column(name = "created_by", length = 50)
    private String createdBy;
    
    //@CreatedDate
    @Transient // Bỏ qua trường này, không ánh xạ vào DB
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    //@LastModifiedDate
    @Transient // Bỏ qua trường này, không ánh xạ vào DB
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Transient // Bỏ qua trường này, không ánh xạ vào DB
    @Column(name = "deleted_by", length = 50)
    private String deletedBy;
}
