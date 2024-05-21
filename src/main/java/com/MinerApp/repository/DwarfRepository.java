package com.MinerApp.repository;

import com.MinerApp.domain.Dwarf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DwarfRepository extends JpaRepository<Dwarf, Long> {
}
