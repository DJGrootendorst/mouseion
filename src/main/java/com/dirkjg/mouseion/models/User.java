package com.dirkjg.mouseion.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    String username;
    String password;
}
