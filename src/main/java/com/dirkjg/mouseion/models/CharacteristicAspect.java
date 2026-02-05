package com.dirkjg.mouseion.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CharacteristicAspect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // primary key

    private int number;       // Er zijn 49 Kenmerkende Aspecten
    private String description; // De inhoud van het kenmerk

    // Getters
    public Long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
