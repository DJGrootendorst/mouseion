package com.dirkjg.mouseion.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EducationContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String learningGoal;
    private String question;
    private String answer;
    private LocalDateTime createdAt;

    // Dit is de target kant (niet-eigenaar) van de OneToOne-relatie met Painting.
    @OneToOne(mappedBy = "educationContent")
    private Painting painting;

    // Getters
    public Long getId() {
        return id;
    }

    public String getLearningGoal() {
        return learningGoal;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Painting getPainting() {
        return painting;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLearningGoal(String learningGoal) {
        this.learningGoal = learningGoal;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPainting(Painting painting) {
        this.painting = painting;
    }
}
