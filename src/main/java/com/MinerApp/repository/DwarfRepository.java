package com.MinerApp.repository;

import com.MinerApp.domain.Dwarf;
import com.MinerApp.dto.BestDwarvesNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DwarfRepository extends JpaRepository<Dwarf, Long> {
    Optional<Dwarf> findDwarfByName(String dwarfName);

    //JPQL query to be implemented
    @Query("SELECT new com.MinerApp.dto.BestDwarvesNames()")
    BestDwarvesNames getBestDwarves();
}
