package com.dirkjg.mouseion.Dtos;

public class CharacteristicAspectDto {

    private Long id;
    private int number;
    private String description;

    private HistoricalPeriodDto historicalPeriodDto;

    // Lege constructor
    public CharacteristicAspectDto() {
    }

    // Constructor met alle velden
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
