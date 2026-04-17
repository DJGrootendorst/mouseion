package com.dirkjg.mouseion.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    Long id;
    String roleName;
}
