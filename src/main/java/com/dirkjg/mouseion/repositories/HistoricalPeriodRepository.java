package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.HistoricalPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalPeriodRepository extends JpaRepository<HistoricalPeriod, Long> {
}
