package com.dirkjg.mouseion.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PainterInputDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Birth year is required")
    private Integer birthYear;

    private Integer deathYear;

    // Lege constructor
    public PainterInputDto() {
    }

    // Constructor met alle velden behalve id
    public PainterInputDto(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }
}
