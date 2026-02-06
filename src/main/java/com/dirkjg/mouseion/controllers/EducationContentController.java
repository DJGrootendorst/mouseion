package com.dirkjg.mouseion.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.dirkjg.mouseion.services.EducationContentService;
import com.dirkjg.mouseion.Dtos.EducationContentDto;
import com.dirkjg.mouseion.Dtos.EducationContentInputDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class EducationContentController {

    private final EducationContentService educationContentService;

    public EducationContentController(EducationContentService educationContentService) {
        this.educationContentService = educationContentService;
    }

    // Get all education contents
    @GetMapping("/educationContents")
    public ResponseEntity<List<EducationContentDto>> getAllEducationContents(
            @RequestParam(value = "learningGoal", required = false) Optional<String> learningGoal) {

        List<EducationContentDto> dtos;

        if (learningGoal.isEmpty()) {
            dtos = educationContentService.getAllEducationContents();
        } else {
            dtos = educationContentService.getAllEducationContentsByLearningGoal(learningGoal.get());
        }

        return ResponseEntity.ok().body(dtos);
    }

    // Get education content by id
    @GetMapping("/educationContents/{id}")
    public ResponseEntity<EducationContentDto> getEducationContent(@PathVariable Long id) {
        EducationContentDto dto = educationContentService.getEducationContentById(id);
        return ResponseEntity.ok().body(dto);
    }

    // Post a new education content
    @PostMapping("/educationContents")
    public ResponseEntity<EducationContentDto> addEducationContent(
            @Valid @RequestBody EducationContentInputDto inputDto) {

        EducationContentDto dto = educationContentService.addEducationContent(inputDto);
        return ResponseEntity.created(null).body(dto);
    }

    // Delete education content by id
    @DeleteMapping("/educationContents/{id}")
    public ResponseEntity<Object> deleteEducationContent(@PathVariable Long id) {
        educationContentService.deleteEducationContent(id);
        return ResponseEntity.noContent().build();
    }

    // Full update of education content
    @PutMapping("/educationContents/{id}")
    public ResponseEntity<EducationContentDto> updateEducationContent(
            @PathVariable Long id,
            @Valid @RequestBody EducationContentInputDto newContent) {

        EducationContentDto dto = educationContentService.updateEducationContent(id, newContent);
        return ResponseEntity.ok().body(dto);
    }

    // Partial update (Patch)
    @PatchMapping("/educationContents/{id}")
    public ResponseEntity<EducationContentDto> updatePartialEducationContent(
            @PathVariable Long id,
            @RequestBody EducationContentInputDto newContent) {

        EducationContentDto dto = educationContentService.updatePartialEducationContent(id, newContent);
        return ResponseEntity.ok().body(dto);
    }
}
