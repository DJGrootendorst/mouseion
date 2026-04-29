package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.EducationContentDto;
import com.dirkjg.mouseion.Dtos.EducationContentInputDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.EducationContent;
import com.dirkjg.mouseion.repositories.EducationContentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EducationContentServiceTest {

    @Mock
    EducationContentRepository educationContentRepository;

    @InjectMocks
    EducationContentService educationContentService;

    // unit-test 8 (in de PaintingServiceTest staan unit-tests 1 t/m 7): getAllEducationContents
    @Test
    @DisplayName("Should return all education contents")
    public void getAllEducationContents() {

        // arrange
        EducationContent content = new EducationContent();
        content.setId(1L);
        content.setLearningGoal("De leerling kan benoemen wat 'impressionisme is'");
        content.setQuestion("Wat is impressionisme?");
        content.setAnswer("Impressionisme is een kunststroming die zich focust op licht en kleur");

        Mockito.when(educationContentRepository.findAll())
                .thenReturn(List.of(content));

        // act
        List<EducationContentDto> result = educationContentService.getAllEducationContents();

        // assert
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("De leerling kan benoemen wat 'impressionisme is'", result.get(0).getLearningGoal());
        assertEquals("Wat is impressionisme?", result.get(0).getQuestion());
        assertEquals("Impressionisme is een kunststroming die zich focust op licht en kleur", result.get(0).getAnswer());
    }

    // unit-test 9
    @Test
    @DisplayName("Should return education content by id")
    public void getEducationContentById() {

        // arrange
        EducationContent content = new EducationContent();
        content.setId(1L);
        content.setLearningGoal("Test");
        content.setQuestion("Q");
        content.setAnswer("A");

        Mockito.when(educationContentRepository.findById(1L))
                .thenReturn(Optional.of(content));

        // act
        EducationContentDto result = educationContentService.getEducationContentById(1L);

        // assert
        assertEquals(1L, result.getId());
        assertEquals("Test", result.getLearningGoal());
    }

    // unit-test 10
    @Test
    @DisplayName("Should add education content correctly and return DTO with id")
    public void addEducationContent() {

        // arrange
        EducationContentInputDto inputDto = new EducationContentInputDto(
                "Test learning goal",
                "Test question",
                "Test answer"
        );

        Mockito.when(educationContentRepository.save(Mockito.any(EducationContent.class)))
                .thenAnswer(invocation -> {
                    EducationContent content = invocation.getArgument(0);
                    content.setId(1L);
                    return content;
                });

        // act
        EducationContentDto result =
                educationContentService.addEducationContent(inputDto);

        // assert
        assertEquals(1L, result.getId());
        assertEquals("Test learning goal", result.getLearningGoal());
        assertEquals("Test question", result.getQuestion());
        assertEquals("Test answer", result.getAnswer());

    }

    // unit-test 11
    @Test
    @DisplayName("Should return education contents filtered by learning goal")
    public void getAllByLearningGoal() {

        // arrange
        String learningGoal = "Art History";

        EducationContent content1 = new EducationContent();
        content1.setId(1L);
        content1.setLearningGoal(learningGoal);
        content1.setQuestion("Question 1");
        content1.setAnswer("Answer 1");

        EducationContent content2 = new EducationContent();
        content2.setId(2L);
        content2.setLearningGoal(learningGoal);
        content2.setQuestion("Question 2");
        content2.setAnswer("Answer 2");

        Mockito.when(educationContentRepository.findAllByLearningGoalIgnoreCase(learningGoal))
                .thenReturn(List.of(content1, content2));

        // act
        List<EducationContentDto> result =
                educationContentService.getAllEducationContentsByLearningGoal(learningGoal);

        // assert
        assertEquals(2, result.size());
        assertEquals("Art History", result.get(0).getLearningGoal());
        assertEquals("Art History", result.get(1).getLearningGoal());
    }

    // unit-test 12
    @Test
    @DisplayName("Should partially update education content")
    public void updatePartialEducationContent() {

        // ARRANGE
        Long id = 1L;

        EducationContent existing = new EducationContent();
        existing.setId(id);
        existing.setLearningGoal("Old goal");
        existing.setQuestion("Old question");
        existing.setAnswer("Old answer");

        EducationContentInputDto patchDto = new EducationContentInputDto(
                "New goal",
                null,
                "New answer"
        );

        EducationContent saved = new EducationContent();
        saved.setId(id);
        saved.setLearningGoal("New goal");
        saved.setQuestion("Old question"); // blijft hetzelfde
        saved.setAnswer("New answer");

        Mockito.when(educationContentRepository.findById(id))
                .thenReturn(Optional.of(existing));

        Mockito.when(educationContentRepository.save(Mockito.any(EducationContent.class)))
                .thenReturn(saved);

        // ACT
        EducationContentDto result =
                educationContentService.updatePartialEducationContent(id, patchDto);

        // ASSERT
        assertEquals("New goal", result.getLearningGoal());
        assertEquals("Old question", result.getQuestion());
        assertEquals("New answer", result.getAnswer());
    }

    //unit-test 13
    @Test
    @DisplayName("Should throw exception when education content not found")
    public void shouldThrowExceptionWhenEducationContentNotFound() {

        // arrange
        Long id = 1L;

        Mockito.when(educationContentRepository.findById(id))
                .thenReturn(Optional.empty());

        // act & assert
        assertThrows(RecordNotFoundException.class, () -> {
            educationContentService.getEducationContentById(id);
        });

    }

    // unit-test 14
    @Test
    @DisplayName("Should throw exception when updating non-existing education content")
    public void updateEducationContent_notFound() {

        // arrange
        Long id = 1L;

        EducationContentInputDto inputDto = new EducationContentInputDto(
                "New goal",
                "New question",
                "New answer"
        );

        Mockito.when(educationContentRepository.findById(id))
                .thenReturn(Optional.empty());

        // act & assert
        assertThrows(RecordNotFoundException.class, () -> {
            educationContentService.updateEducationContent(id, inputDto);
        });
    }

    // unit-test
    @Test
    @DisplayName("Should delete education content by id")
    public void deleteEducationContent() {

        // arrange
        Long id = 1L;

        // act
        educationContentService.deleteEducationContent(id);

        // assert
        Mockito.verify(educationContentRepository).deleteById(id);
    }

    // unit-test
    @Test
    @DisplayName("Should update education content correctly")
    public void updateEducationContent_success() {

        // arrange
        Long id = 1L;

        EducationContent existing = new EducationContent();
        existing.setId(id);
        existing.setLearningGoal("Old");
        existing.setQuestion("Old Q");
        existing.setAnswer("Old A");

        EducationContentInputDto inputDto = new EducationContentInputDto(
                "New",
                "New Q",
                "New A"
        );

        EducationContent updated = new EducationContent();
        updated.setId(id);
        updated.setLearningGoal("New");
        updated.setQuestion("New Q");
        updated.setAnswer("New A");

        Mockito.when(educationContentRepository.findById(id))
                .thenReturn(Optional.of(existing));

        Mockito.when(educationContentRepository.save(Mockito.any(EducationContent.class)))
                .thenReturn(updated);

        // act
        EducationContentDto result =
                educationContentService.updateEducationContent(id, inputDto);

        // assert
        assertEquals("New", result.getLearningGoal());
        assertEquals("New Q", result.getQuestion());
        assertEquals("New A", result.getAnswer());
    }

    // unit-test
    @Test
    @DisplayName("Should update only question in partial update")
    public void updatePartial_onlyQuestion() {

        // arrange
        Long id = 1L;

        EducationContent existing = new EducationContent();
        existing.setId(id);
        existing.setLearningGoal("Old goal");
        existing.setQuestion("Old question");
        existing.setAnswer("Old answer");

        EducationContentInputDto patchDto = new EducationContentInputDto(
                null,
                "New question",
                null
        );

        EducationContent saved = new EducationContent();
        saved.setId(id);
        saved.setLearningGoal("Old goal");
        saved.setQuestion("New question");
        saved.setAnswer("Old answer");

        Mockito.when(educationContentRepository.findById(id))
                .thenReturn(Optional.of(existing));

        Mockito.when(educationContentRepository.save(Mockito.any(EducationContent.class)))
                .thenReturn(saved);

        // act
        EducationContentDto result =
                educationContentService.updatePartialEducationContent(id, patchDto);

        // assert
        assertEquals("Old goal", result.getLearningGoal());
        assertEquals("New question", result.getQuestion());
        assertEquals("Old answer", result.getAnswer());
    }

    // unit-test
    @Test
    @DisplayName("Should convert input DTO to entity")
    public void toEntity() {

        // arrange
        EducationContentInputDto dto = new EducationContentInputDto(
                "Goal",
                "Question",
                "Answer"
        );

        // act
        EducationContent result = educationContentService.toEntity(dto);

        // assert
        assertEquals("Goal", result.getLearningGoal());
        assertEquals("Question", result.getQuestion());
        assertEquals("Answer", result.getAnswer());
    }

    // unit-test
    @Test
    @DisplayName("Should convert entity to DTO")
    public void toDto() {

        // arrange
        EducationContent content = new EducationContent();
        content.setId(1L);
        content.setLearningGoal("Goal");
        content.setQuestion("Question");
        content.setAnswer("Answer");

        // act
        EducationContentDto result = educationContentService.toDto(content);

        // assert
        assertEquals(1L, result.getId());
        assertEquals("Goal", result.getLearningGoal());
        assertEquals("Question", result.getQuestion());
        assertEquals("Answer", result.getAnswer());
    }

}