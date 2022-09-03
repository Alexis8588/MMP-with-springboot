package com.hmh.mmp.entity;

import lombok.Getter;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime startTime;

    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime nowTime;

    @CreationTimestamp
    @Column
    private LocalDateTime calTime;
    
    @CreatedDate
    private LocalDate calDate; // 날짜만 기록할 수 있는 어노테이션인지 확인 필요
}
