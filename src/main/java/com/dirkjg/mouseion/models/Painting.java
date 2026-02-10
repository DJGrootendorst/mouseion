package com.dirkjg.mouseion.models;

// Verantwoordingsdocument: ik gebruik hier bewust drie specifieke imports
// in plaats van de import jakarta.persistence.* omdat ik daarmee alleen
// de classes importeer die ik daadwerkelijk nodig heb, waardoor IDE's en
// andere ontwikkelaars meteen zien welke annotaties/types ik gebruik.
import jakarta.persistence.*;

@Entity
public class Painting {

    @Id
    // De GenerationType.IDENTITY zorgt ervoor dat hibernate het id-veld vult met een
    // steeds oplopende waarde.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // variabele declaraties, de variabele Painter en CharacteristicAspects moet nog worden gemaakt, dat doe ik zodra ik de relaties ga leggen
    private String title;
    private Integer year;
    // bij image wordt een url ingevuld
    private String image;

    // OneToOne-relatie naar EducationContent
    @OneToOne
    @JoinColumn(name = "education_content_id")
    private EducationContent educationContent;

    // Alle variable getters
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

    public EducationContent getEducationContent() {
        return educationContent;
    }

    // Alle variabele setters
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

    public void setEducationContent(EducationContent educationContent) {
        this.educationContent = educationContent;
    }
}
