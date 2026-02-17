package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.PaintingDto;
import com.dirkjg.mouseion.Dtos.PaintingInputDto;
import com.dirkjg.mouseion.Dtos.EducationContentDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.Painting;
import com.dirkjg.mouseion.repositories.EducationContentRepository;
import com.dirkjg.mouseion.repositories.PaintingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final EducationContentRepository educationContentRepository;

    public PaintingService(PaintingRepository paintingRepository,
                           EducationContentRepository educationContentRepository) {
        this.paintingRepository = paintingRepository;
        this.educationContentRepository = educationContentRepository;
    }

    // CRUD-methodes
    public List<PaintingDto> getAllPaintings() {
        List<Painting> paintingList = paintingRepository.findAll();
        List<PaintingDto> paintingDtoList = new ArrayList<>();
        for(Painting painting : paintingList) {
            PaintingDto dto = transferToDto(painting);
            paintingDtoList.add(dto);
        }
        return paintingDtoList;
    }

    // Is het in onderstaande methode nodig om alle schilderijen per titel op te vragen?
    public List<PaintingDto> getAllPaintingsByTitle(String title) {
        List<Painting> paintingList = paintingRepository.findAllPaintingsByTitleEqualsIgnoreCase(title);
        List<PaintingDto> paintingDtoList = new ArrayList<>();
        for(Painting painting : paintingList) {
            PaintingDto dto = transferToDto(painting);
            paintingDtoList.add(dto);
        }
        return paintingDtoList;
    }

    public PaintingDto getPaintingById(Long id) {
        Optional<Painting> paintingOptional = paintingRepository.findById(id);
        if (paintingOptional.isPresent()) {
            Painting painting = paintingOptional.get();
            return transferToDto(painting);
        } else {
            throw new RecordNotFoundException("geen schilderij gevonden");
        }
    }

    public PaintingDto addPainting(PaintingInputDto dto) {
        Painting painting = transferToPainting(dto);
        paintingRepository.save(painting);
        return transferToDto(painting);
    }

    public void deletePainting(@RequestBody Long id) {
        paintingRepository.deleteById(id);
    }

    public PaintingDto updatePainting(Long id, PaintingInputDto newPainting) {
        Optional<Painting> paintingOptional = paintingRepository.findById(id);
        if (paintingOptional.isPresent()) {
            Painting painting1 = paintingOptional.get();
            painting1.setTitle(newPainting.getTitle());
            painting1.setYear(newPainting.getYear());
            painting1.setImage(newPainting.getImage());
            Painting returnPainting = paintingRepository.save(painting1);
            return transferToDto(returnPainting);
        } else {
            throw new RecordNotFoundException("geen schilderij gevonden");
        }
    }

    public Painting transferToPainting(PaintingInputDto dto){
        var painting = new Painting();
        painting.setTitle(dto.getTitle());
        painting.setYear(dto.getYear());
        painting.setImage(dto.getImage());
        return painting;
    }

    public PaintingDto transferToDto(Painting painting){
        PaintingDto dto = new PaintingDto();
        dto.setId(painting.getId());
        dto.setTitle(painting.getTitle());
        dto.setYear(painting.getYear());
        dto.setImage(painting.getImage());

        // Verbetering na test in Postman: waarbij de EducationContent niet aan het Schilderij werd gekoppeld.
        // Met deze verbetering wordt gekoppelde EducationContent toegevoegd aan DTO.
        if (painting.getEducationContent() != null) {
            var educationContent = painting.getEducationContent();
            var educationDto = new EducationContentDto();
            educationDto.setId(educationContent.getId());
            educationDto.setLearningGoal(educationContent.getLearningGoal());
            educationDto.setQuestion(educationContent.getQuestion());
            educationDto.setAnswer(educationContent.getAnswer());
            educationDto.setCreatedAt(educationContent.getCreatedAt());

            dto.setEducationContentDto(educationDto);
        }

        return dto;
    }

    // Methode om EducationContent aan Painting te koppelen
    public void assignEducationContentToPainting(Long paintingId, Long educationContentId) {
        var optionalPainting = paintingRepository.findById(paintingId);
        var optionalEducationContent = educationContentRepository.findById(educationContentId);

        if (optionalPainting.isPresent() && optionalEducationContent.isPresent()) {
            Painting painting = optionalPainting.get();
            painting.setEducationContent(optionalEducationContent.get());
            paintingRepository.save(painting);
        } else {
            throw new RecordNotFoundException("Painting of EducactionContent niet gevonden");
        }
    }


    // Onderstaande methode is voor de @Patch methode,
    // voor het maken van een gedeeltelijke update van
    // een resource. Bijvoorbeeld als ik alleen de titel
    // van een schilderij wil aanpassen, maar niet het hele object.

    public PaintingDto updatePartialPainting(Long id, PaintingInputDto newPainting) {
        Optional<Painting> paintingOptional = paintingRepository.findById(id);
        if (paintingOptional.isPresent()) {
            Painting painting = paintingOptional.get();

            // Alleen de velden updaten die niet null zijn
            if (newPainting.getTitle() != null) {
                painting.setTitle(newPainting.getTitle());
            }
            if (newPainting.getYear() != null) {
                painting.setYear(newPainting.getYear());
            }
            if (newPainting.getImage() != null) {
                painting.setImage(newPainting.getImage());
            }

            Painting updatedPainting = paintingRepository.save(painting);
            return transferToDto(updatedPainting);
        } else {
            throw new RecordNotFoundException("geen schilderij gevonden");
        }
    }
}
