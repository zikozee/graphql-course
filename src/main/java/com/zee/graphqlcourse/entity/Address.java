package com.zee.graphqlcourse.entity;

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
@Table(name = "address", indexes = {
        @Index(name = "ent_id_idx", columnList = "entity_id"),
        @Index(name = "ent_id_street", columnList = "entity_id, street", unique = true)
})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String entityId;
    private String street;
    private String city;
    private String state;
    private int zipCode;
    @CreatedDate
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @LastModifiedDate
    private Timestamp updatedAt;
}
