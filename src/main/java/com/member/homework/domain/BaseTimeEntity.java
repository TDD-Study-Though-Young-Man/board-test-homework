package com.member.homework.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "reg_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "mod_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime modDate;

}
