package com.MinerApp.repository;

import com.MinerApp.domain.Dwarf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DwarfRepository extends JpaRepository<Dwarf, Long> {
    Optional<Dwarf> findDwarfByName(String dwarfName);

    //JPQL query to be implemented
    @Query("SELECT new list (d.name) from Dwarf d order by d.productivity desc limit 2")
    List<String> getBestDwarves();
}
