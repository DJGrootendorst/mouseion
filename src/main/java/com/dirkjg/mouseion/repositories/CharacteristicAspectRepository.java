package com.dirkjg.mouseion.repositories;

import com.dirkjg.mouseion.models.CharacteristicAspect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacteristicAspectRepository extends JpaRepository<CharacteristicAspect, Long> {

    List<CharacteristicAspect> findAllByNumber(int number);
}
