package com.MinerApp.repository;

import com.MinerApp.domain.Rune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RuneRepository extends JpaRepository<Rune, Long> {

    Optional<Rune> findRuneByName(String runeName);
    Optional<Rune> findRuneByBonus(int runeBonus);
    Optional<Rune> findRuneByBanned(boolean banned);

}
