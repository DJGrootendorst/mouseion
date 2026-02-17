package com.dirkjg.mouseion.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Painter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer birthYear;
    private Integer deathYear;

    // OneToMany relatie (inverse side) naar Painting,
    // want in Painting staat de ManyToOne relatie naar Painter.
    @OneToMany(mappedBy = "painter")
    private List<Painting> paintings;

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public List<Painting> getPaintings() {
        return paintings;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public void setPaintings(List<Painting> paintings) {
        this.paintings = paintings;
    }
}
