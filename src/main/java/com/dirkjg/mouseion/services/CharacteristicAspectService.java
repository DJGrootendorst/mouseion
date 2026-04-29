package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.CharacteristicAspectDto;
import com.dirkjg.mouseion.Dtos.CharacteristicAspectInputDto;
import com.dirkjg.mouseion.Dtos.HistoricalPeriodDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.CharacteristicAspect;
import com.dirkjg.mouseion.models.HistoricalPeriod;
import com.dirkjg.mouseion.repositories.CharacteristicAspectRepository;
import com.dirkjg.mouseion.repositories.HistoricalPeriodRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacteristicAspectService {

    private final CharacteristicAspectRepository characteristicAspectRepository;
    private final HistoricalPeriodRepository historicalPeriodRepository;

    public CharacteristicAspectService(
            CharacteristicAspectRepository characteristicAspectRepository,
            HistoricalPeriodRepository historicalPeriodRepository) {
        this.characteristicAspectRepository = characteristicAspectRepository;
        this.historicalPeriodRepository = historicalPeriodRepository;
    }

    // Alle CharacteristicAspects ophalen
    public List<CharacteristicAspectDto> getAllAspects() {
        List<CharacteristicAspect> aspectList = characteristicAspectRepository.findAll();
        List<CharacteristicAspectDto> dtos = new ArrayList<>();
        for (CharacteristicAspect aspect : aspectList) {
            dtos.add(transferToDto(aspect));
        }
        return dtos;
    }

    // Alle CharacteristicAspects op basis van number
    public List<CharacteristicAspectDto> getAspectsByNumber(int number) {
        List<CharacteristicAspect> aspectList = characteristicAspectRepository.findAllByNumber(number);
        List<CharacteristicAspectDto> dtos = new ArrayList<>();
        for (CharacteristicAspect aspect : aspectList) {
            dtos.add(transferToDto(aspect));
        }
        return dtos;
    }

    // Eén CharacteristicAspect ophalen
    public CharacteristicAspectDto getAspectById(Long id) {
        CharacteristicAspect aspect = characteristicAspectRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen aspect gevonden"));
        return transferToDto(aspect);
    }

    // Nieuw CharacteristicAspect toevoegen
    public CharacteristicAspectDto addAspect(CharacteristicAspectInputDto dto) {
        CharacteristicAspect aspect = toEntity(dto);
        characteristicAspectRepository.save(aspect);
        return transferToDto(aspect);
    }

    // CharacteristicAspect verwijderen
    public void deleteAspect(Long id) {
        characteristicAspectRepository.deleteById(id);
    }

    // Volledige update
    public CharacteristicAspectDto updateAspect(Long id, CharacteristicAspectInputDto dto) {
        CharacteristicAspect aspect = characteristicAspectRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen aspect gevonden"));

        aspect.setNumber(dto.getNumber());
        aspect.setDescription(dto.getDescription());

        CharacteristicAspect updated = characteristicAspectRepository.save(aspect);
        return transferToDto(updated);
    }

    // Gedeeltelijke update
    public CharacteristicAspectDto updatePartialAspect(Long id, CharacteristicAspectInputDto dto) {
        CharacteristicAspect aspect = characteristicAspectRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen aspect gevonden"));

        if (dto.getNumber() != 0) aspect.setNumber(dto.getNumber());
        if (dto.getDescription() != null) aspect.setDescription(dto.getDescription());

        CharacteristicAspect updated = characteristicAspectRepository.save(aspect);
        return transferToDto(updated);
    }

    // Entity converter
    public CharacteristicAspect toEntity(CharacteristicAspectInputDto dto) {
        CharacteristicAspect aspect = new CharacteristicAspect();
        aspect.setNumber(dto.getNumber());
        aspect.setDescription(dto.getDescription());
        return aspect;
    }

    // DTO converter (transferToDto)
    public CharacteristicAspectDto transferToDto(CharacteristicAspect aspect) {
        CharacteristicAspectDto dto = new CharacteristicAspectDto();
        dto.setId(aspect.getId());
        dto.setNumber(aspect.getNumber());
        dto.setDescription(aspect.getDescription());

        // HistoricalPeriod koppelen aan DTO
        if (aspect.getHistoricalPeriod() != null) {
            HistoricalPeriod period = aspect.getHistoricalPeriod();

            HistoricalPeriodDto periodDto =
                    new HistoricalPeriodDto(
                            period.getId(),
                            period.getPeriodNumber(),
                            period.getName(),
                            period.getFirstYear(),
                            period.getLastYear()
                    );
            dto.setHistoricalPeriodDto(periodDto);
        }

        return dto;
    }

    // Assign HistoricalPeriod aan CharacteristicAspect
    public void assignHistoricalPeriodToCharacteristicAspect(Long aspectId, Long periodId) {
        var aspectOptional = characteristicAspectRepository.findById(aspectId);
        var periodOptional = historicalPeriodRepository.findById(periodId);

        if (aspectOptional.isPresent() && periodOptional.isPresent()) {
            CharacteristicAspect aspect = aspectOptional.get();
            HistoricalPeriod period = periodOptional.get();

            aspect.setHistoricalPeriod(period);
            characteristicAspectRepository.save(aspect);
        } else {
            throw new RecordNotFoundException("CharacteristicAspect of HistoricalPeriod niet gevonden");
        }
    }
}
