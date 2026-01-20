package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.PaintingDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.Painting;
import com.dirkjg.mouseion.repositories.PaintingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaintingService {
    private final PaintingRepository paintingRepository;
    public PaintingService(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

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
        List<Painting> paintingList = PaintingRepository.findAllPaintingsByTitleEqualsIgnoreCase(title);
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

    public Painting = transferToPainting(PaintingInputDto dto){
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

        return dto;
    }
}
