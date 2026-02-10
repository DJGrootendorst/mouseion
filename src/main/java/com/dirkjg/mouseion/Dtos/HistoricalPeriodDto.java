package com.dirkjg.mouseion.Dtos;

public class HistoricalPeriodDto {

    private Long id;
    private int periodNumber;
    private String name;
    private int firstYear;
    private int lastYear;

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
}
