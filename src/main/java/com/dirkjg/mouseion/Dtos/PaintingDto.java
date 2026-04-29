package com.dirkjg.mouseion.Dtos;

public class PaintingDto {
    private Long id;
    private String title;
    private Integer year;
    private String image;

    private EducationContentDto educationContentDto;
    private PainterDto painterDto;
    private CharacteristicAspectDto characteristicAspectDto;

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public String getImage() {
        return image;
    }

    public EducationContentDto getEducationContentDto() {
        return educationContentDto;
    }

    public PainterDto getPainterDto() {
        return painterDto;
    }

    public CharacteristicAspectDto getCharacteristicAspectDto() {
        return characteristicAspectDto;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
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

    public void setEducationContentDto(EducationContentDto educationContentDto) {
        this.educationContentDto = educationContentDto;
    }

    public void setPainterDto(PainterDto painterDto) {
        this.painterDto = painterDto;
    }

    public void setCharacteristicAspectDto(CharacteristicAspectDto characteristicAspectDto) {
        this.characteristicAspectDto = characteristicAspectDto;
    }
}
