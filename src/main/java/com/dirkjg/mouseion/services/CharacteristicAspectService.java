package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.CharacteristicAspectDto;
import com.dirkjg.mouseion.Dtos.CharacteristicAspectInputDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.CharacteristicAspect;
import com.dirkjg.mouseion.repositories.CharacteristicAspectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacteristicAspectService {

    private final CharacteristicAspectRepository repository;

    public CharacteristicAspectService(CharacteristicAspectRepository repository) {
        this.repository = repository;
    }

    public List<CharacteristicAspectDto> getAllAspects() {
        List<CharacteristicAspect> list = repository.findAll();
        List<CharacteristicAspectDto> dtos = new ArrayList<>();
        for (CharacteristicAspect c : list) dtos.add(toDto(c));
        return dtos;
    }

    public List<CharacteristicAspectDto> getAspectsByNumber(int number) {
        List<CharacteristicAspect> list = repository.findAllByNumber(number);
        List<CharacteristicAspectDto> dtos = new ArrayList<>();
        for (CharacteristicAspect c : list) dtos.add(toDto(c));
        return dtos;
    }

    public CharacteristicAspectDto getAspectById(Long id) {
        CharacteristicAspect c = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen aspect gevonden"));
        return toDto(c);
    }

    public CharacteristicAspectDto addAspect(CharacteristicAspectInputDto dto) {
        CharacteristicAspect c = toEntity(dto);
        repository.save(c);
        return toDto(c);
    }

    public void deleteAspect(Long id) {
        repository.deleteById(id);
    }

    public CharacteristicAspectDto updateAspect(Long id, CharacteristicAspectInputDto dto) {
        CharacteristicAspect c = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen aspect gevonden"));

        c.setNumber(dto.getNumber());
        c.setDescription(dto.getDescription());

        CharacteristicAspect updated = repository.save(c);
        return toDto(updated);
    }

    public CharacteristicAspectDto updatePartialAspect(Long id, CharacteristicAspectInputDto dto) {
        CharacteristicAspect c = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen aspect gevonden"));

        if (dto.getNumber() != 0) c.setNumber(dto.getNumber());
        if (dto.getDescription() != null) c.setDescription(dto.getDescription());

        CharacteristicAspect updated = repository.save(c);
        return toDto(updated);
    }

    public CharacteristicAspect toEntity(CharacteristicAspectInputDto dto) {
        CharacteristicAspect c = new CharacteristicAspect();
        c.setNumber(dto.getNumber());
        c.setDescription(dto.getDescription());
        return c;
    }

    public CharacteristicAspectDto toDto(CharacteristicAspect c) {
        CharacteristicAspectDto dto = new CharacteristicAspectDto();
        dto.setId(c.getId());
        dto.setNumber(c.getNumber());
        dto.setDescription(c.getDescription());
        return dto;
    }
}
