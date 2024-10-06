package com.zee.graphqlcourse.entity;

import com.zee.graphqlcourse.codegen.types.Division;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 06 Oct, 2024
 */

@Getter
@Setter
@Entity
@Table(name = "department", indexes = {
        @Index(name = "dept_comp_rc_num", columnList = "company_name, rc_number, department_no", unique = true)
})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String departmentNo;
    @Column(nullable = false)
    private String companyName;
    @Column(length = 20, nullable = false)
    private String rcNumber;
    @Column(length = 20, nullable = false)
    private Division division;
    @CreatedDate
    private Timestamp createdAt= new Timestamp(System.currentTimeMillis());
    @LastModifiedDate
    private Timestamp updatedAt;
}
