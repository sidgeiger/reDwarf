package com.MinerApp.repository;

import com.MinerApp.domain.Rune;
import com.MinerApp.dto.RunesWithAvgBonusesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RuneRepository extends JpaRepository<Rune, Long> {

    Optional<Rune> findRuneByName(String runeName);
    Optional<Rune> findRuneByBonus(int runeBonus);
    Optional<Rune> findRuneByBanned(boolean banned);

    @Query("SELECT DISTINCT new com.MinerApp.dto.RunesWithAvgBonusesInfo (r.name, avg(r.bonus)) FROM Rune as r WHERE r.banned = false AND r.item IS NOT null GROUP BY r.name ORDER BY r.name DESC")
    List<RunesWithAvgBonusesInfo> runesWithAvgBonusesDistinctNamesJQPL();
}
