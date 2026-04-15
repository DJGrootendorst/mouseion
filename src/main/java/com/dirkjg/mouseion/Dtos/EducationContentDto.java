package com.dirkjg.mouseion.Dtos;

import java.time.LocalDateTime;

public class EducationContentDto {

    private Long id;
    private String learningGoal;
    private String question;
    private String answer;
    private LocalDateTime createdAt;

    // lege constructor
    public EducationContentDto() {
    }

    // Constructor met alle velden
    public EducationContentDto(Long id, String LearningGoal, String question, String answer, LocalDateTime createdAt) {
        this.id = id;
        this.learningGoal = LearningGoal;
        this.question = question;
        this.answer = answer;
        this.createdAt = createdAt;
    }

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
}
