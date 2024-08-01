package com.example.valven.entity;

import com.example.valven.util.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.security.Timestamp;

@SQLDelete(sql = "UPDATE Commit SET DELETED_AT = CURRENT_TIMESTAMP WHERE id =? and version =? ")
@Where(clause = "DELETED_DATE is null")
@Entity(name = "Commit")
@Data
@Table(name= "Commit", indexes = {@Index(columnList = "HASH", name = "commit_hash_indx")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"HASH"})})
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class CommitEntity extends BaseEntity {

    @Column(name = "HASH", unique = true)
    private String hash;

    @Column(name = "TIMESTAMP")
    private Timestamp timestamp;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PATCH")
    private String patch;

    @ManyToOne
    @JoinColumn(name = "DEVELOPER_ID")
    private DeveloperEntity developer;
}
