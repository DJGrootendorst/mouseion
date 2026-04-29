package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.PaintingDto;
import com.dirkjg.mouseion.Dtos.PaintingInputDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.*;
import com.dirkjg.mouseion.repositories.CharacteristicAspectRepository;
import com.dirkjg.mouseion.repositories.EducationContentRepository;
import com.dirkjg.mouseion.repositories.PainterRepository;
import com.dirkjg.mouseion.repositories.PaintingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaintingServiceTest {

    @Mock PaintingRepository paintingRepository;
    @Mock PainterRepository painterRepository;
    @Mock EducationContentRepository educationContentRepository;
    @Mock CharacteristicAspectRepository characteristicAspectRepository;
    @Mock FileService fileService;

    @InjectMocks PaintingService paintingService;


    // CRUD TESTS


    @Test
    @DisplayName("Get all paintings")
    void getAllPaintings() {

        // arrange
        Painting p = new Painting();
        p.setId(1L);

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        List<PaintingDto> result = paintingService.getAllPaintings();

        // assert
        assertEquals(1, result.size());
    }

    // getAllPaintingsByTitle
    @Test
    @DisplayName("Get all paintings by title")
    void getAllPaintingsByTitle() {

        // arrange
        Painting p = new Painting();
        p.setTitle("Mona Lisa");

        when(paintingRepository.findAllPaintingsByTitleEqualsIgnoreCase("mona lisa"))
                .thenReturn(List.of(p));

        // act
        var result = paintingService.getAllPaintingsByTitle("mona lisa");

        // assert
        assertEquals(1, result.size());
        assertEquals("Mona Lisa", result.get(0).getTitle());
    }

    @Test
    @DisplayName("Get painting by id")
    void getById() {

        // arrange
        Painting p = new Painting();
        p.setId(1L);

        when(paintingRepository.findById(1L))
                .thenReturn(Optional.of(p));

        // act
        PaintingDto result = paintingService.getPaintingById(1L);

        // assert
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Add painting")
    void addPainting() {

        // arrange
        PaintingInputDto dto = new PaintingInputDto();
        dto.setTitle("A");

        when(paintingRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        // act
        PaintingDto result = paintingService.addPainting(dto);

        // assert
        assertEquals("A", result.getTitle());
    }

    @Test
    @DisplayName("Update painting")
    void updatePainting() {

        // arrange
        Painting p = new Painting();
        p.setId(1L);

        when(paintingRepository.findById(1L))
                .thenReturn(Optional.of(p));

        when(paintingRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        PaintingInputDto dto = new PaintingInputDto();
        dto.setTitle("New");

        // act
        PaintingDto result = paintingService.updatePainting(1L, dto);

        // assert
        assertEquals("New", result.getTitle());
    }

    @Test
    @DisplayName("Delete painting")
    void deletePainting() {

        // arrange
        Long id = 1L;

        // act
        paintingService.deletePainting(id);

        // assert
        verify(paintingRepository).deleteById(id);
    }


// ASSIGN TESTS (including failures)


    @Test
    @DisplayName("Assign painter success")
    void assignPainter() {

        // arrange
        Painting p = new Painting();
        Painter painter = new Painter();

        when(paintingRepository.findById(1L)).thenReturn(Optional.of(p));
        when(painterRepository.findById(2L)).thenReturn(Optional.of(painter));

        // act
        paintingService.assignPainterToPainting(1L, 2L);

        // assert
        assertEquals(painter, p.getPainter());
    }

    @Test
    @DisplayName("Assign painter fail")
    void assignPainterFail() {

        // arrange
        when(paintingRepository.findById(1L)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.assignPainterToPainting(1L, 2L));
    }

    @Test
    @DisplayName("Assign education success")
    void assignEducation() {

        // arrange
        Painting p = new Painting();
        EducationContent e = new EducationContent();

        when(paintingRepository.findById(1L)).thenReturn(Optional.of(p));
        when(educationContentRepository.findById(2L)).thenReturn(Optional.of(e));

        // act
        paintingService.assignEducationContentToPainting(1L, 2L);

        // assert
        assertEquals(e, p.getEducationContent());
    }

    @Test
    @DisplayName("Assign characteristic success")
    void assignAspect() {

        // arrange
        Painting p = new Painting();
        CharacteristicAspect a = new CharacteristicAspect();

        when(paintingRepository.findById(1L)).thenReturn(Optional.of(p));
        when(characteristicAspectRepository.findById(2L)).thenReturn(Optional.of(a));

        // act
        paintingService.assignCharacteristicAspectToPainting(1L, 2L);

        // assert
        assertEquals(a, p.getCharacteristicAspect());
    }

    // assignImageToPainting
    @Test
    @DisplayName("Assign image to painting")
    void assignImageToPainting() {

        // arrange
        Painting p = new Painting();
        p.setId(1L);

        when(paintingRepository.findById(1L))
                .thenReturn(Optional.of(p));

        when(paintingRepository.save(any(Painting.class)))
                .thenAnswer(i -> i.getArgument(0));

        // act
        Painting result = paintingService.assignImageToPainting("file.jpg", 1L);

        // assert
        assertEquals("file.jpg", result.getImage());
    }


// TransferToDto TESTS

    @Test
    @DisplayName("DTO mapping - all null relations")
    void dtoAllNull() {

        // arrange
        Painting p = new Painting();

        // act
        PaintingDto dto = paintingService.transferToDto(p);

        // assert
        assertNotNull(dto);
    }

    @Test
    @DisplayName("DTO mapping - education only")
    void dtoEducation() {

        // arrange
        Painting p = new Painting();
        EducationContent e = new EducationContent();
        e.setId(1L);

        p.setEducationContent(e);

        // act
        PaintingDto dto = paintingService.transferToDto(p);

        // assert
        assertEquals(1L, dto.getEducationContentDto().getId());
    }

    @Test
    @DisplayName("DTO mapping - painter only")
    void dtoPainter() {

        // arrange
        Painting p = new Painting();
        Painter painter = new Painter();
        painter.setId(2L);

        p.setPainter(painter);

        // act
        PaintingDto dto = paintingService.transferToDto(p);

        // assert
        assertEquals(2L, dto.getPainterDto().getId());
    }

    @Test
    @DisplayName("DTO mapping - characteristic only")
    void dtoAspect() {

        // arrange
        Painting p = new Painting();
        CharacteristicAspect a = new CharacteristicAspect();
        a.setId(3L);

        p.setCharacteristicAspect(a);

        // act
        PaintingDto dto = paintingService.transferToDto(p);

        // assert
        assertEquals(3L, dto.getCharacteristicAspectDto().getId());
    }

// FILTER TESTS

    @Test
    @DisplayName("Filter - no filters applied")
    void filterNone() {

        // arrange
        Painting p = new Painting();
        p.setTitle("Test");

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        // assert
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Filter - title match")
    void filterTitle() {

        // arrange
        Painting p = new Painting();
        p.setTitle("Mona Lisa");

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.of("mona"),
                Optional.empty(),
                Optional.empty()
        );

        // assert
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Filter - title miss")
    void filterTitleMiss() {

        // arrange
        Painting p = new Painting();
        p.setTitle("Rembrandt");

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.of("mona"),
                Optional.empty(),
                Optional.empty()
        );

        // assert
        assertEquals(0, result.size());
    }

// IMAGE TESTS

    @Test
    @DisplayName("Get image success")
    void getImage() {

        // arrange
        Painting p = new Painting();
        p.setImage("file.jpg");

        Resource r = mock(Resource.class);

        when(paintingRepository.findById(1L)).thenReturn(Optional.of(p));
        when(fileService.downloadFile("file.jpg")).thenReturn(r);

        // act
        Resource result = paintingService.getImageFromPainting(1L);

        // assert
        assertEquals(r, result);
    }

    @Test
    @DisplayName("Get image null fail")
    void getImageNull() {

        // arrange
        Painting p = new Painting();
        p.setImage(null);

        when(paintingRepository.findById(1L)).thenReturn(Optional.of(p));

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.getImageFromPainting(1L));
    }

// EXCEPTION TESTS

    @Test
    @DisplayName("Should throw exception when painting not found by id")
    void getPaintingByIdNotFound() {

        // arrange
        Long id = 1L;

        when(paintingRepository.findById(id))
                .thenReturn(Optional.empty());

        // act
        Executable action = () ->
                paintingService.getPaintingById(id);

        // assert
        assertThrows(RecordNotFoundException.class, action);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing painting")
    void updatePaintingNotFound() {

        // arrange
        Long id = 1L;

        PaintingInputDto dto = new PaintingInputDto();
        dto.setTitle("New");

        when(paintingRepository.findById(id))
                .thenReturn(Optional.empty());

        // act
        Executable action = () ->
                paintingService.updatePainting(id, dto);

        // assert
        assertThrows(RecordNotFoundException.class, action);
    }

    @Test
    @DisplayName("Should throw exception when assigning painter and painting not found")
    void assignPainterNotFound() {

        // arrange
        Long paintingId = 1L;
        Long painterId = 2L;

        when(paintingRepository.findById(paintingId))
                .thenReturn(Optional.empty());

        // act
        Executable action = () ->
                paintingService.assignPainterToPainting(paintingId, painterId);

        // assert
        assertThrows(RecordNotFoundException.class, action);
    }

    @Test
    @DisplayName("Should throw exception when assigning education content and painting not found")
    void assignEducationNotFound() {

        // arrange
        Long paintingId = 1L;
        Long educationId = 2L;

        when(paintingRepository.findById(paintingId))
                .thenReturn(Optional.empty());

        // act
        Executable action = () ->
                paintingService.assignEducationContentToPainting(paintingId, educationId);

        // assert
        assertThrows(RecordNotFoundException.class, action);
    }

    @Test
    @DisplayName("Should throw exception when assigning characteristic aspect and painting not found")
    void assignAspectNotFound() {

        // arrange
        Long paintingId = 1L;
        Long aspectId = 2L;

        when(paintingRepository.findById(paintingId))
                .thenReturn(Optional.empty());

        // act
        Executable action = () ->
                paintingService.assignCharacteristicAspectToPainting(paintingId, aspectId);

        // assert
        assertThrows(RecordNotFoundException.class, action);
    }

//

    // updatePainting - NOT FOUND EXCEPTION
    @Test
    @DisplayName("Should throw exception when updating non-existing painting")
    void updatePainting_notFound() {

        // arrange
        Long id = 1L;

        PaintingInputDto update = new PaintingInputDto();
        update.setTitle("Nieuwe titel");
        update.setYear(1600);
        update.setImage("nieuw.jpg");

        Mockito.when(paintingRepository.findById(id))
                .thenReturn(Optional.empty());

        // act
        Executable action = () -> paintingService.updatePainting(id, update);

        // assert
        assertThrows(RecordNotFoundException.class, action);
    }

    // assignEducationContentToPainting - NOT FOUND EXCEPTION
    @Test
    @DisplayName("Should throw exception when assigning education content fails")
    void assignEducationContent_notFound() {

        // arrange
        Long paintingId = 1L;
        Long educationId = 2L;

        Mockito.when(paintingRepository.findById(paintingId))
                .thenReturn(Optional.empty());

        Mockito.when(educationContentRepository.findById(educationId))
                .thenReturn(Optional.empty());

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.assignEducationContentToPainting(paintingId, educationId));
    }

    // assignCharacteristicAspectToPainting - NOT FOUND EXCEPTION
    @Test
    @DisplayName("Should throw exception when assigning characteristic aspect fails")
    void assignCharacteristicAspect_notFound() {

        // arrange
        Long paintingId = 1L;
        Long aspectId = 2L;

        Mockito.when(paintingRepository.findById(paintingId))
                .thenReturn(Optional.empty());

        Mockito.when(characteristicAspectRepository.findById(aspectId))
                .thenReturn(Optional.empty());

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.assignCharacteristicAspectToPainting(paintingId, aspectId));
    }

    // assignImageToPainting - NOT FOUND EXCEPTION
    @Test
    @DisplayName("Should throw exception when assigning image to non-existing painting")
    void assignImage_notFound() {

        // arrange
        Long id = 1L;

        Mockito.when(paintingRepository.findById(id))
                .thenReturn(Optional.empty());

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.assignImageToPainting("file.jpg", id));
    }

    // getImageFromPainting - NOT FOUND EXCEPTION
    @Test
    @DisplayName("Should throw exception when painting not found for image retrieval")
    void getImageFromPainting_notFound() {

        // arrange
        Long id = 1L;

        Mockito.when(paintingRepository.findById(id))
                .thenReturn(Optional.empty());

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.getImageFromPainting(id));
    }

    // getImageFromPainting - NO IMAGE PRESENT
    @Test
    @DisplayName("Should throw exception when painting has no image")
    void getImageFromPainting_noImage() {

        // arrange
        Long id = 1L;

        Painting painting = new Painting();
        painting.setId(id);
        painting.setImage(null);

        Mockito.when(paintingRepository.findById(id))
                .thenReturn(Optional.of(painting));

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.getImageFromPainting(id));
    }

    // updatePartialPainting - ONLY TITLE UPDATED
    @Test
    @DisplayName("Should partially update only title")
    void updatePartialPainting_onlyTitle() {

        // arrange
        Long id = 1L;

        Painting painting = new Painting();
        painting.setId(id);
        painting.setTitle("Old");
        painting.setYear(1900);
        painting.setImage("old.jpg");

        Mockito.when(paintingRepository.findById(id))
                .thenReturn(Optional.of(painting));

        Mockito.when(paintingRepository.save(Mockito.any(Painting.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaintingInputDto dto = new PaintingInputDto();
        dto.setTitle("New");

        // act
        PaintingDto result = paintingService.updatePartialPainting(id, dto);

        // assert
        assertEquals("New", result.getTitle());
        assertEquals(1900, result.getYear());
        assertEquals("old.jpg", result.getImage());
    }

    // updatePartialPainting - ONLY YEAR UPDATED
    @Test
    @DisplayName("Should partially update only year")
    void updatePartialPainting_onlyYear() {

        // arrange
        Long id = 1L;

        Painting painting = new Painting();
        painting.setId(id);
        painting.setTitle("Old");
        painting.setYear(1900);
        painting.setImage("old.jpg");

        Mockito.when(paintingRepository.findById(id))
                .thenReturn(Optional.of(painting));

        Mockito.when(paintingRepository.save(Mockito.any(Painting.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaintingInputDto dto = new PaintingInputDto();
        dto.setYear(2000);

        // act
        PaintingDto result = paintingService.updatePartialPainting(id, dto);

        // assert
        assertEquals("Old", result.getTitle());
        assertEquals(2000, result.getYear());
        assertEquals("old.jpg", result.getImage());
    }

    // updatePartialPainting - ONLY IMAGE UPDATED
    @Test
    @DisplayName("Should partially update only image")
    void updatePartialPainting_onlyImage() {

        // arrange
        Long id = 1L;

        Painting painting = new Painting();
        painting.setId(id);
        painting.setTitle("Old");
        painting.setYear(1900);
        painting.setImage("old.jpg");

        Mockito.when(paintingRepository.findById(id))
                .thenReturn(Optional.of(painting));

        Mockito.when(paintingRepository.save(Mockito.any(Painting.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaintingInputDto dto = new PaintingInputDto();
        dto.setImage("new.jpg");

        // act
        PaintingDto result = paintingService.updatePartialPainting(id, dto);

        // assert
        assertEquals("Old", result.getTitle());
        assertEquals(1900, result.getYear());
        assertEquals("new.jpg", result.getImage());
    }

    // transferToDto - NO RELATIONS (BRANCH COVERAGE)
    @Test
    @DisplayName("Should transfer painting to DTO without relations")
    void transferToDto_noRelations() {

        // arrange
        Painting painting = new Painting();
        painting.setId(1L);
        painting.setTitle("Test");
        painting.setYear(2000);
        painting.setImage("img.jpg");

        painting.setEducationContent(null);
        painting.setPainter(null);
        painting.setCharacteristicAspect(null);

        // act
        PaintingDto result = paintingService.transferToDto(painting);

        // assert
        assertEquals("Test", result.getTitle());
        assertEquals("img.jpg", result.getImage());
        assertNull(result.getEducationContentDto());
        assertNull(result.getPainterDto());
        assertNull(result.getCharacteristicAspectDto());
    }

    // test: filter - aspect NULL case
    @Test
    @DisplayName("Filter: painting with null characteristicAspect should be included when no filter applied")
    void filter_characteristicAspect_null() {

        // arrange
        Painting p = new Painting();
        p.setTitle("Test");

        Mockito.when(paintingRepository.findAll())
                .thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        // assert
        assertEquals(1, result.size());
    }

    // filter - characteristicAspect null branch
    @Test
    @DisplayName("Filter - characteristicAspect null should still include painting when no filter")
    void filterCharacteristicAspectNullBranch() {

        // arrange
        Painting p = new Painting();
        p.setTitle("Test");
        p.setCharacteristicAspect(null);

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        // assert
        assertEquals(1, result.size());
    }

    // filter - characteristicAspect mismatch branch
    @Test
    @DisplayName("Filter - characteristicAspect mismatch should exclude painting")
    void filterCharacteristicAspectMismatchBranch() {

        // arrange
        CharacteristicAspect aspect = new CharacteristicAspect();
        aspect.setId(1L);

        Painting p = new Painting();
        p.setCharacteristicAspect(aspect);

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.empty(),
                Optional.empty(),
                Optional.of(999L)
        );

        // assert
        assertEquals(0, result.size());
    }


    // test: DTO - only painter present (partial branch)
    @Test
    @DisplayName("transferToDto should handle only painter present")
    void transferToDto_onlyPainter() {

        // arrange
        Painting p = new Painting();
        p.setId(1L);

        Painter painter = new Painter();
        painter.setId(2L);
        painter.setName("Van Gogh");

        p.setPainter(painter);

        // act
        PaintingDto dto = paintingService.transferToDto(p);

        // assert
        assertNotNull(dto.getPainterDto());
        assertNull(dto.getEducationContentDto());
        assertNull(dto.getCharacteristicAspectDto());
    }

    // test: getImageFromPainting - SUCCES branch
    @Test
    @DisplayName("Should return image resource when painting has image")
    void getImageFromPainting_success() {

        // arrange
        Long id = 1L;

        Painting painting = new Painting();
        painting.setId(id);
        painting.setImage("test.jpg");

        Resource mockResource = Mockito.mock(Resource.class);

        Mockito.when(paintingRepository.findById(id))
                .thenReturn(Optional.of(painting));

        Mockito.when(fileService.downloadFile("test.jpg"))
                .thenReturn(mockResource);

        // act
        Resource result = paintingService.getImageFromPainting(id);

        // assert
        assertNotNull(result);
    }

    // filter - historicalPeriod mismatch
    @Test
    @DisplayName("Filter - historicalPeriod mismatch should exclude painting")
    void filterHistoricalPeriodMismatch() {

        // arrange
        CharacteristicAspect aspect = new CharacteristicAspect();
        aspect.setId(1L);

        Painting p = new Painting();
        p.setTitle("Test");
        p.setCharacteristicAspect(aspect);

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.empty(),
                Optional.of(99L), // mismatch
                Optional.empty()
        );

        // assert
        assertEquals(0, result.size());
    }

    // filter - historicalPeriod match branch
    @Test
    @DisplayName("Filter - historicalPeriod match should include painting")
    void filterHistoricalPeriodMatch() {

        // arrange
        var period = new HistoricalPeriod();
        period.setId(10L);

        var aspect = new CharacteristicAspect();
        aspect.setHistoricalPeriod(period);

        Painting p = new Painting();
        p.setTitle("Test");
        p.setCharacteristicAspect(aspect);

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.empty(),
                Optional.of(10L),
                Optional.empty()
        );

        // assert
        assertEquals(1, result.size());
    }


    // filter - characteristicAspect mismatch
    @Test
    @DisplayName("Filter - characteristicAspect mismatch should exclude painting")
    void filterCharacteristicAspectMismatch() {

        // arrange
        CharacteristicAspect aspect = new CharacteristicAspect();
        aspect.setId(1L);

        Painting p = new Painting();
        p.setTitle("Test");
        p.setCharacteristicAspect(aspect);

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.empty(),
                Optional.empty(),
                Optional.of(99L) // mismatch
        );

        // assert
        assertEquals(0, result.size());
    }

    // assign education - only painting present
    @Test
    @DisplayName("Assign education - should fail when education missing")
    void assignEducationOnlyPaintingPresent() {

        // arrange
        Painting p = new Painting();

        when(paintingRepository.findById(1L)).thenReturn(Optional.of(p));
        when(educationContentRepository.findById(2L)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.assignEducationContentToPainting(1L, 2L));
    }

    // assign painter - only painter missing
    @Test
    @DisplayName("Assign painter - should fail when painter missing")
    void assignPainterOnlyPainterMissing() {

        // arrange
        Painter painter = new Painter();

        when(paintingRepository.findById(1L)).thenReturn(Optional.empty());
        when(painterRepository.findById(2L)).thenReturn(Optional.of(painter));

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.assignPainterToPainting(1L, 2L));
    }

    // assign painter - painter missing branch
    @Test
    @DisplayName("Assign painter - painter missing should throw exception")
    void assignPainterPainterMissingBranch() {

        // arrange
        Painting p = new Painting();

        when(paintingRepository.findById(1L)).thenReturn(Optional.of(p));
        when(painterRepository.findById(2L)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.assignPainterToPainting(1L, 2L));
    }

    // assign characteristic aspect - only aspect missing
    @Test
    @DisplayName("Assign aspect - should fail when aspect missing")
    void assignAspectOnlyMissing() {

        // arrange
        when(paintingRepository.findById(1L)).thenReturn(Optional.of(new Painting()));
        when(characteristicAspectRepository.findById(2L)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.assignCharacteristicAspectToPainting(1L, 2L));
    }

    // updatePartialPainting - not found branch
    @Test
    @DisplayName("Update partial painting - should throw when painting not found")
    void updatePartialPaintingNotFound() {

        // arrange
        when(paintingRepository.findById(1L)).thenReturn(Optional.empty());

        PaintingInputDto dto = new PaintingInputDto();
        dto.setTitle("New");

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.updatePartialPainting(1L, dto));
    }

    // getImageFromPainting - null image via existing painting
    @Test
    @DisplayName("Get image - should throw when image is null")
    void getImageNullImageBranch() {

        // arrange
        Painting p = new Painting();
        p.setId(1L);
        p.setImage(null);

        when(paintingRepository.findById(1L)).thenReturn(Optional.of(p));

        // act + assert
        assertThrows(RecordNotFoundException.class,
                () -> paintingService.getImageFromPainting(1L));
    }

    // filter - historicalPeriod null aspect branch
    @Test
    @DisplayName("Filter - historicalPeriod should handle null characteristicAspect")
    void filterHistoricalPeriodNullAspectBranch() {

        // arrange
        Painting p = new Painting();
        p.setTitle("Test");
        p.setCharacteristicAspect(null); // <- BELANGRIJK

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.empty(),
                Optional.of(10L),
                Optional.empty()
        );

        // assert
        assertEquals(0, result.size());
    }

    // filter - characteristicAspect null while filter active
    @Test
    @DisplayName("Filter - characteristicAspect null should exclude when filter is active")
    void filterCharacteristicAspectNullWithActiveFilter() {

        // arrange
        Painting p = new Painting();
        p.setTitle("Test");
        p.setCharacteristicAspect(null);

        when(paintingRepository.findAll()).thenReturn(List.of(p));

        // act
        var result = paintingService.getFilteredPaintings(
                Optional.empty(),
                Optional.empty(),
                Optional.of(1L)
        );

        // assert
        assertEquals(0, result.size());
    }

}