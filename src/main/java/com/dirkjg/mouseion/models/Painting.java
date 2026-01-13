package com.dirkjg.mouseion.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Painting {

    @Id
    @GeneratedValue
    Long id;

    // variabele declaraties, de variabele Painter en CharacteristicAspects moet nog worden gemaakt, dat doe ik zodra ik de relaties ga leggen
    private String title;
    private Integer year;
    // bij image wordt een url ingevuld
    private String image;

    // Alle variabele getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public String getImage() {
        return image;
    }

    // Alle variabele setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
