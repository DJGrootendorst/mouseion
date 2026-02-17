package com.dirkjg.mouseion.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class HistoricalPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;           // primary key, uniek per periode

    private int periodNumber;  // tijdvak 1 tot en met 10
    private String name;       // naam van het tijdvak
    private int firstYear;     // beginjaar
    private int lastYear;      // eindjaar

    @OneToMany(mappedBy = "historicalPeriod")
    private List<CharacteristicAspect> characteristicAspects;

    // Getters
    public Long getId() {
        return id;
    }

    public int getPeriodNumber() {
        return periodNumber;
    }

    public String getName() {
        return name;
    }

    public int getFirstYear() {
        return firstYear;
    }

    public int getLastYear() {
        return lastYear;
    }

    public List<CharacteristicAspect> getCharacteristicAspects() {
        return characteristicAspects;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setPeriodNumber(int periodNumber) {
        this.periodNumber = periodNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstYear(int firstYear) {
        this.firstYear = firstYear;
    }

    public void setLastYear(int lastYear) {
        this.lastYear = lastYear;
    }

    public void setCharacteristicAspects(List<CharacteristicAspect> characteristicAspects) {
        this.characteristicAspects = characteristicAspects;
    }
}
