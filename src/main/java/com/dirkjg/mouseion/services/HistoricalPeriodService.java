package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.Dtos.HistoricalPeriodDto;
import com.dirkjg.mouseion.Dtos.HistoricalPeriodInputDto;
import com.dirkjg.mouseion.exceptions.RecordNotFoundException;
import com.dirkjg.mouseion.models.HistoricalPeriod;
import com.dirkjg.mouseion.repositories.HistoricalPeriodRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoricalPeriodService {

    private final HistoricalPeriodRepository historicalPeriodRepository;

    public HistoricalPeriodService(HistoricalPeriodRepository historicalPeriodRepository) {
        this.historicalPeriodRepository = historicalPeriodRepository;
    }

    public List<HistoricalPeriodDto> getAllHistoricalPeriods() {
        List<HistoricalPeriod> list = historicalPeriodRepository.findAll();
        List<HistoricalPeriodDto> dtos = new ArrayList<>();
        for (HistoricalPeriod h : list) dtos.add(toDto(h));
        return dtos;
    }

    public List<HistoricalPeriodDto> getHistoricalPeriodsByName(String name) {
        List<HistoricalPeriod> list = historicalPeriodRepository.findAllByNameIgnoreCase(name);
        List<HistoricalPeriodDto> dtos = new ArrayList<>();
        for (HistoricalPeriod h : list) dtos.add(toDto(h));
        return dtos;
    }

    public HistoricalPeriodDto getHistoricalPeriodById(Long id) {
        HistoricalPeriod h = historicalPeriodRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen historical period gevonden"));
        return toDto(h);
    }

    public HistoricalPeriodDto addHistoricalPeriod(HistoricalPeriodInputDto dto) {
        HistoricalPeriod h = toEntity(dto);
        historicalPeriodRepository.save(h);
        return toDto(h);
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
        return toDto(updated);
    }

    public HistoricalPeriodDto updatePartialHistoricalPeriod(Long id, HistoricalPeriodInputDto dto) {
        HistoricalPeriod h = historicalPeriodRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Geen historical period gevonden"));

        if (dto.getPeriodNumber() != 0) h.setPeriodNumber(dto.getPeriodNumber());
        if (dto.getName() != null) h.setName(dto.getName());
        if (dto.getFirstYear() != 0) h.setFirstYear(dto.getFirstYear());
        if (dto.getLastYear() != 0) h.setLastYear(dto.getLastYear());

        HistoricalPeriod updated = historicalPeriodRepository.save(h);
        return toDto(updated);
    }

    public HistoricalPeriod toEntity(HistoricalPeriodInputDto dto) {
        HistoricalPeriod h = new HistoricalPeriod();
        h.setPeriodNumber(dto.getPeriodNumber());
        h.setName(dto.getName());
        h.setFirstYear(dto.getFirstYear());
        h.setLastYear(dto.getLastYear());
        return h;
    }

    public HistoricalPeriodDto toDto(HistoricalPeriod h) {
        HistoricalPeriodDto dto = new HistoricalPeriodDto();
        dto.setId(h.getId());
        dto.setPeriodNumber(h.getPeriodNumber());
        dto.setName(h.getName());
        dto.setFirstYear(h.getFirstYear());
        dto.setLastYear(h.getLastYear());
        return dto;
    }
}
