package com.dirkjg.mouseion.Dtos;

public class CharacteristicAspectInputDto {

    private int number;
    private String description;

    // Lege constructor
    public CharacteristicAspectInputDto() {
    }

    // Constructor met alle velden
    public CharacteristicAspectInputDto(int number, String description) {
        this.number = number;
        this.description = description;
    }

    // Getters
    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setNumber(int number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

