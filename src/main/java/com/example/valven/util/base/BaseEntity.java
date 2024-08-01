package com.example.valven.util.base;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({ AuditingEntityListener.class })
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UUID")
    @SequenceGenerator(name = "UUID", sequenceName  = "UUIDGenerator")
    private String id;

    @CreationTimestamp
    @Column(name = "CDATE",nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "UDATE")
    private LocalDateTime modifyDate;

    @CreatedBy
    @Column(name = "CUSER", nullable = false, updatable = false)
    private String createUser;

    @LastModifiedBy
    @Column(name = "UUSER")
    private String modifyUser;

    @Version
    @Column(name = "VERSION")
    private Long version = 0L;

    @Column(name = "DELETED_DATE")
    private LocalDateTime deletedDate;


}
