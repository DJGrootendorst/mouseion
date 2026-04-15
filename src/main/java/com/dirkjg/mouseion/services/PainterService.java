package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.PainterDto;
import com.dirkjg.mouseion.Dtos.PainterInputDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.Painter;
import com.dirkjg.mouseion.repositories.PainterRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PainterService {

    private final PainterRepository painterRepository;

    public PainterService(PainterRepository painterRepository) {
        this.painterRepository = painterRepository;
    }

    public List<PainterDto> getAllPainters() {
        List<Painter> painters = painterRepository.findAll();
        List<PainterDto> dtos = new ArrayList<>();
        for (Painter p : painters) {
            dtos.add(toDto(p));
        }
        return dtos;
    }

    public List<PainterDto> getAllPaintersByName(String name) {
        List<Painter> painters = painterRepository.findAllByNameIgnoreCase(name);
        List<PainterDto> dtos = new ArrayList<>();
        for (Painter p : painters) {
            dtos.add(toDto(p));
        }
        return dtos;
    }

    public PainterDto getPainterById(Long id) {
        Painter painter = painterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen painter gevonden"));
        return toDto(painter);
    }

    public PainterDto addPainter(PainterInputDto dto) {
        Painter painter = toEntity(dto);
        painterRepository.save(painter);
        return toDto(painter);
    }

    public void deletePainter(Long id) {
        painterRepository.deleteById(id);
    }

    public PainterDto updatePainter(Long id, PainterInputDto dto) {
        Painter painter = painterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen painter gevonden"));

        painter.setName(dto.getName());
        painter.setBirthYear(dto.getBirthYear());
        painter.setDeathYear(dto.getDeathYear());

        Painter updated = painterRepository.save(painter);
        return toDto(updated);
    }

    public PainterDto updatePartialPainter(Long id, PainterInputDto dto) {
        Painter painter = painterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen painter gevonden"));

        if (dto.getName() != null) painter.setName(dto.getName());
        if (dto.getBirthYear() != null) painter.setBirthYear(dto.getBirthYear());
        if (dto.getDeathYear() != null) painter.setDeathYear(dto.getDeathYear());

        Painter updated = painterRepository.save(painter);
        return toDto(updated);
    }

    public Painter toEntity(PainterInputDto dto) {
        Painter p = new Painter();
        p.setName(dto.getName());
        p.setBirthYear(dto.getBirthYear());
        p.setDeathYear(dto.getDeathYear());
        return p;
    }

    public PainterDto toDto(Painter painter) {
        PainterDto dto = new PainterDto();
        dto.setId(painter.getId());
        dto.setName(painter.getName());
        dto.setBirthYear(painter.getBirthYear());
        dto.setDeathYear(painter.getDeathYear());
        return dto;
    }
}
