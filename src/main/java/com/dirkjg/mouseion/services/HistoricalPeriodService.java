package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.HistoricalPeriodDto;
import com.dirkjg.mouseion.Dtos.HistoricalPeriodInputDto;
import com.dirkjg.mouseion.Dtos.CharacteristicAspectDto;
import com.dirkjg.mouseion.Dtos.CharacteristicAspectInputDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.HistoricalPeriod;
import com.dirkjg.mouseion.models.CharacteristicAspect;
import com.dirkjg.mouseion.repositories.HistoricalPeriodRepository;
import com.dirkjg.mouseion.repositories.CharacteristicAspectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoricalPeriodService {

    private final HistoricalPeriodRepository historicalPeriodRepository;
    private final CharacteristicAspectRepository characteristicAspectRepository;

    public HistoricalPeriodService(
            HistoricalPeriodRepository historicalPeriodRepository,
            CharacteristicAspectRepository characteristicAspectRepository) {
        this.historicalPeriodRepository = historicalPeriodRepository;
        this.characteristicAspectRepository = characteristicAspectRepository;
    }

    public List<HistoricalPeriodDto> getAllHistoricalPeriods() {
        List<HistoricalPeriod> list = historicalPeriodRepository.findAll();
        List<HistoricalPeriodDto> dtos = new ArrayList<>();
        for (HistoricalPeriod h : list) dtos.add(transferToDto(h));
        return dtos;
    }

    public List<HistoricalPeriodDto> getHistoricalPeriodsByName(String name) {
        List<HistoricalPeriod> list = historicalPeriodRepository.findAllByNameIgnoreCase(name);
        List<HistoricalPeriodDto> dtos = new ArrayList<>();
        for (HistoricalPeriod h : list) dtos.add(transferToDto(h));
        return dtos;
    }

    public HistoricalPeriodDto getHistoricalPeriodById(Long id) {
        HistoricalPeriod h = historicalPeriodRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen historical period gevonden"));
        return transferToDto(h);
    }

    public HistoricalPeriodDto addHistoricalPeriod(HistoricalPeriodInputDto dto) {
        HistoricalPeriod h = toEntity(dto);
        historicalPeriodRepository.save(h);
        return transferToDto(h);
    }

    public void deleteHistoricalPeriod(Long id) {
        historicalPeriodRepository.deleteById(id);
    }

    public HistoricalPeriodDto updateHistoricalPeriod(Long id, HistoricalPeriodInputDto dto) {
        HistoricalPeriod h = historicalPeriodRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen historical period gevonden"));

        h.setPeriodNumber(dto.getPeriodNumber());
        h.setName(dto.getName());
        h.setFirstYear(dto.getFirstYear());
        h.setLastYear(dto.getLastYear());

        HistoricalPeriod updated = historicalPeriodRepository.save(h);
        return transferToDto(updated);
    }

    public HistoricalPeriodDto updatePartialHistoricalPeriod(Long id, HistoricalPeriodInputDto dto) {
        HistoricalPeriod h = historicalPeriodRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen historical period gevonden"));

        if (dto.getPeriodNumber() != 0) h.setPeriodNumber(dto.getPeriodNumber());
        if (dto.getName() != null) h.setName(dto.getName());
        if (dto.getFirstYear() != 0) h.setFirstYear(dto.getFirstYear());
        if (dto.getLastYear() != 0) h.setLastYear(dto.getLastYear());

        HistoricalPeriod updated = historicalPeriodRepository.save(h);
        return transferToDto(updated);
    }

    public HistoricalPeriod toEntity(HistoricalPeriodInputDto dto) {
        HistoricalPeriod h = new HistoricalPeriod();
        h.setPeriodNumber(dto.getPeriodNumber());
        h.setName(dto.getName());
        h.setFirstYear(dto.getFirstYear());
        h.setLastYear(dto.getLastYear());
        return h;
    }

    // DTO converter (transferToDto)
    public HistoricalPeriodDto transferToDto(HistoricalPeriod period) {
        HistoricalPeriodDto dto = new HistoricalPeriodDto();
        dto.setId(period.getId());
        dto.setPeriodNumber(period.getPeriodNumber());
        dto.setName(period.getName());
        dto.setFirstYear(period.getFirstYear());
        dto.setLastYear(period.getLastYear());

        // Voeg CharacteristicAspects toe aan DTO
        if (period.getCharacteristicAspects() != null && !period.getCharacteristicAspects().isEmpty()) {
            List<CharacteristicAspectDto> aspectDtos = new ArrayList<>();
            for (CharacteristicAspect aspect : period.getCharacteristicAspects()) {
                CharacteristicAspectDto aspectDto = new CharacteristicAspectDto();
                aspectDto.setId(aspect.getId());
                aspectDto.setNumber(aspect.getNumber());
                aspectDto.setDescription(aspect.getDescription());

                aspectDtos.add(aspectDto);
            }
            dto.setCharacteristicAspectDtos(aspectDtos);
        }

        return dto;
    }

    // Assign één CharacteristicAspect aan een HistoricalPeriod (omgekeerd van CharacteristicAspectService)
    public void assignCharacteristicAspectToHistoricalPeriod(Long periodId, Long aspectId) {
        var periodOptional = historicalPeriodRepository.findById(periodId);
        var aspectOptional = characteristicAspectRepository.findById(aspectId);

        if (periodOptional.isPresent() && aspectOptional.isPresent()) {
            HistoricalPeriod period = periodOptional.get();
            CharacteristicAspect aspect = aspectOptional.get();

            // Koppel het aspect aan de periode
            aspect.setHistoricalPeriod(period);
            characteristicAspectRepository.save(aspect);
        } else {
            throw new RecordNotFoundException("HistoricalPeriod of CharacteristicAspect niet gevonden");
        }
    }

    public void assignMultipleAspectsToHistoricalPeriod(Long periodId, List<Long> aspectIds) {
        HistoricalPeriod period = historicalPeriodRepository.findById(periodId)
                .orElseThrow(() -> new RecordNotFoundException("HistoricalPeriod niet gevonden"));

        for (Long aspectId : aspectIds) {
            CharacteristicAspect aspect = characteristicAspectRepository.findById(aspectId)
                    .orElseThrow(() -> new RecordNotFoundException("CharacteristicAspect niet gevonden: " + aspectId));
            aspect.setHistoricalPeriod(period);
            characteristicAspectRepository.save(aspect);
        }
    }

}
