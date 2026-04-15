package com.dirkjg.mouseion.Dtos;

import jakarta.validation.constraints.NotBlank;

public class EducationContentInputDto {

    @NotBlank(message = "Learning goal is required")
    private String learningGoal;

    @NotBlank(message = "Question is required")
    private String question;

    @NotBlank(message = "Answer is required")
    private String answer;

    // Lege constructor
    public EducationContentInputDto() {
    }

    // Constructor met velden
    public EducationContentInputDto(String learningGoal, String question, String answer) {
        this.learningGoal = learningGoal;
        this.question = question;
        this.answer = answer;
    }

    // Getters
    public String getLearningGoal() {
        return learningGoal;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    // Setters
    public void setLearningGoal(String learningGoal) {
        this.learningGoal = learningGoal;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
