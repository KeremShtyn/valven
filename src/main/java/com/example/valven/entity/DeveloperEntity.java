package com.example.valven.entity;

import com.example.valven.util.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;


@SQLDelete(sql = "UPDATE Developer SET DELETED_AT = CURRENT_TIMESTAMP WHERE id =? and version =? ")
@Where(clause = "DELETED_DATE is null")
@Entity(name = "Developer")
@Data
@Table(name= "Developer", indexes = {@Index(columnList = "EMAIL", name = "developer_email_indx")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"EMAIL"})})
@EqualsAndHashCode(callSuper = false, exclude = {"commits"})
@ToString(callSuper = true, includeFieldNames = true, exclude = {"commits"})
public class DeveloperEntity extends BaseEntity {

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @OneToMany(mappedBy = "developer", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<CommitEntity> commits;

}
