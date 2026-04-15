package com.dirkjg.mouseion.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CharacteristicAspectDto {

    private Long id;
    private int number;
    private String description;
    @JsonIgnoreProperties(value = { "characteristicAspectDtos", "id" })
    private HistoricalPeriodDto historicalPeriodDto;

    // Lege constructor
    public CharacteristicAspectDto() {
    }

    // Constructor zonder historicalPeriodDto
    public CharacteristicAspectDto(Long id, int number, String description) {
        this.id = id;
        this.number = number;
        this.description = description;
    }

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

    public HistoricalPeriodDto getHistoricalPeriodDto() {
        return historicalPeriodDto;
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

    public void setHistoricalPeriodDto(HistoricalPeriodDto historicalPeriodDto) {
        this.historicalPeriodDto = historicalPeriodDto;
    }
}
