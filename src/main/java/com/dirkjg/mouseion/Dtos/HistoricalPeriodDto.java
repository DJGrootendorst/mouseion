package com.dirkjg.mouseion.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class HistoricalPeriodDto {

    private Long id;
    private int periodNumber;
    private String name;
    private int firstYear;
    private int lastYear;
    @JsonIgnoreProperties(value = { "id", "historicalPeriodDto" })
    private List<CharacteristicAspectDto> characteristicAspectDtos;

    // Lege constructor
    public HistoricalPeriodDto() {
    }

    // Constructor met alle velden
    public HistoricalPeriodDto(Long id, int periodNumber, String name, int firstYear, int lastYear) {
        this.id = id;
        this.periodNumber = periodNumber;
        this.name = name;
        this.firstYear = firstYear;
        this.lastYear = lastYear;
    }

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

    public List<CharacteristicAspectDto> getCharacteristicAspectDtos() {
        return characteristicAspectDtos;
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

    public void setCharacteristicAspectDtos(
            List<CharacteristicAspectDto> characteristicAspectDtos) {
        this.characteristicAspectDtos = characteristicAspectDtos;
    }
}
