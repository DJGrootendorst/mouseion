package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.EducationContentDto;
import com.dirkjg.mouseion.Dtos.EducationContentInputDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.EducationContent;
import com.dirkjg.mouseion.repositories.EducationContentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EducationContentService {

    private final EducationContentRepository educationContentRepository;

    public EducationContentService(EducationContentRepository educationContentRepository) {
        this.educationContentRepository = educationContentRepository;
    }

    public List<EducationContentDto> getAllEducationContents() {
        List<EducationContent> list = educationContentRepository.findAll();
        List<EducationContentDto> dtos = new ArrayList<>();
        for (EducationContent e : list) dtos.add(toDto(e));
        return dtos;
    }

    public List<EducationContentDto> getAllEducationContentsByLearningGoal(String learningGoal) {
        List<EducationContent> list = educationContentRepository.findAllByLearningGoalIgnoreCase(learningGoal);
        List<EducationContentDto> dtos = new ArrayList<>();
        for (EducationContent e : list) dtos.add(toDto(e));
        return dtos;
    }

    public EducationContentDto getEducationContentById(Long id) {
        EducationContent content = educationContentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen education content gevonden"));
        return toDto(content);
    }

    public EducationContentDto addEducationContent(EducationContentInputDto dto) {
        EducationContent content = toEntity(dto);
        content.setCreatedAt(LocalDateTime.now());
        educationContentRepository.save(content);
        return toDto(content);
    }

    public void deleteEducationContent(Long id) {
        educationContentRepository.deleteById(id);
    }

    public EducationContentDto updateEducationContent(Long id, EducationContentInputDto dto) {
        EducationContent content = educationContentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen education content gevonden"));

        content.setLearningGoal(dto.getLearningGoal());
        content.setQuestion(dto.getQuestion());
        content.setAnswer(dto.getAnswer());

        EducationContent updated = educationContentRepository.save(content);
        return toDto(updated);
    }

    public EducationContentDto updatePartialEducationContent(Long id, EducationContentInputDto dto) {
        EducationContent content = educationContentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen education content gevonden"));

        if (dto.getLearningGoal() != null) content.setLearningGoal(dto.getLearningGoal());
        if (dto.getQuestion() != null) content.setQuestion(dto.getQuestion());
        if (dto.getAnswer() != null) content.setAnswer(dto.getAnswer());

        EducationContent updated = educationContentRepository.save(content);
        return toDto(updated);
    }

    public EducationContent toEntity(EducationContentInputDto dto) {
        EducationContent e = new EducationContent();
        e.setLearningGoal(dto.getLearningGoal());
        e.setQuestion(dto.getQuestion());
        e.setAnswer(dto.getAnswer());
        return e;
    }

    public EducationContentDto toDto(EducationContent content) {
        EducationContentDto dto = new EducationContentDto();
        dto.setId(content.getId());
        dto.setLearningGoal(content.getLearningGoal());
        dto.setQuestion(content.getQuestion());
        dto.setAnswer(content.getAnswer());
        dto.setCreatedAt(content.getCreatedAt());
        return dto;
    }
}

