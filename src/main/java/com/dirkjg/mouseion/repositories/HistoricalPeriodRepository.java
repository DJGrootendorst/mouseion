package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.HistoricalPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricalPeriodRepository extends JpaRepository<HistoricalPeriod, Long> {

    List<HistoricalPeriod> findAllByNameIgnoreCase(String name);
}
