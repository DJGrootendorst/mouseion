package com.dirkjg.mouseion.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class CharacteristicAspect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // primary key

    private int number;       // Er zijn 49 Kenmerkende Aspecten
    private String description; // De inhoud van het kenmerk

    // OneToMany relatie (inverse side) naar Painting,
    // want in Painting staat de ManyToOne relatie naar CharacteristicAspect.
    @OneToMany(mappedBy = "characteristicAspect")
    private List<Painting> paintings;

    @ManyToOne
    @JoinColumn(name = "historical_period_id")
    private HistoricalPeriod historicalPeriod;

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

    public List<Painting> getPaintings() {
        return paintings;
    }

    public HistoricalPeriod getHistoricalPeriod() {
        return historicalPeriod;
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

    public void setPaintings(List<Painting> paintings) {
        this.paintings = paintings;
    }

    public void setHistoricalPeriod(HistoricalPeriod historicalPeriod) {
        this.historicalPeriod = historicalPeriod;
    }
}
