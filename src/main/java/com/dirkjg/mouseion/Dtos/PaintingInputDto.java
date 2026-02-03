package com.dirkjg.mouseion.Dtos;

import jakarta.validation.constraints.*;

public class PaintingInputDto {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be 100 characters at most")
    private String title;

    @NotNull(message = "Year is required")
    private Integer year; // mag negatief zijn v.Chr.

    private String image;


    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
