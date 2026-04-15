package com.dirkjg.mouseion.Dtos;

public class HistoricalPeriodInputDto {

    private int periodNumber;
    private String name;
    private int firstYear;
    private int lastYear;

    public HistoricalPeriodInputDto() {
    }

    public HistoricalPeriodInputDto(int periodNumber, String name, int firstYear, int lastYear) {
        this.periodNumber = periodNumber;
        this.name = name;
        this.firstYear = firstYear;
        this.lastYear = lastYear;
    }

    // Getters
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

