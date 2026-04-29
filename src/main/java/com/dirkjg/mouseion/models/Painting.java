package com.dirkjg.mouseion.models;

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

    @Column(name = "painting_year")
    private Integer year;
    // bij image wordt een url ingevuld
    private String image;

    // OneToOne-relatie naar EducationContent
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_content_id")
    private EducationContent educationContent;

    // ManyToOne-relatie naar Painter
    // Vanuit Painter naar Painting is dit dus een OneToMany-relatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "painter_id")
    private Painter painter;
    // Verantwoordingsdocument: de relatie tussen Painting en Painter is ManyToOne,
    // omdat Painting de foreign key bevat en dus de owner side is. Hierdoor blijft de
    // database structuur simpel en consistent. Hetzelfde geldt voor Painting en CharacteristicAspect.

    // ManyToOne-relatie naar CharacteristicAspect
    // Vanuit CharacteristicAspect naar Painting is dit dus een OneToMany-relatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characteristic_aspect_id")
    private CharacteristicAspect characteristicAspect;

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

    public Painter getPainter() {
        return painter;
    }

    public CharacteristicAspect getCharacteristicAspect() {
        return characteristicAspect;
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

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    public void setCharacteristicAspect(CharacteristicAspect characteristicAspect) {
        this.characteristicAspect = characteristicAspect;
    }
}
