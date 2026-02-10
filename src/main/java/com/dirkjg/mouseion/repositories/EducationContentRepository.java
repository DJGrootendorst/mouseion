package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.EducationContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationContentRepository extends JpaRepository<EducationContent, Long> {

    List<EducationContent> findAllByLearningGoalIgnoreCase(String learningGoal);
}
