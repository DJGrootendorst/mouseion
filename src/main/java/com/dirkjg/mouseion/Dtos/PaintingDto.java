package com.dirkjg.mouseion.Dtos;

// Verantwoordingsdocument: TECHNISCHE KEUZE
// Voor Painting, Painter en EducationContent zijn zowel een Dto als een InputDto aangemaakt,
// omdat deze entiteiten dynamische data bevatten die via de API wordt aangemaakt,
// gewijzigd en opgevraagd (response én request). De gebruiker moet hier namelijk mee kunnen werken.
//
// Voor HistoricalPeriod en CharacteristicAspect geldt het volgende:
// - Deze entiteiten staan van tevoren vast en worden niet via de API aangemaakt of aangepast.
// - De InputDto wordt technisch wel aangemaakt (HistoricalPeriodInputDto / CharacteristicAspectInputDto)
//   zodat de controller en service compileert en de POST/PUT/PATCH endpoints aanwezig kunnen zijn.
// - Functioneel worden deze InputDto’s alleen gebruikt door een admin voor eventuele aanpassingen;
//   gewone gebruikers sturen hier nooit data mee.
// - Voor gewone GET-requests is uitsluitend de response DTO nodig (HistoricalPeriodDto / CharacteristicAspectDto).

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
