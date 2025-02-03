package com.github.juli220620.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "administration", name = "role")
public class RoleEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    private String id;
}
