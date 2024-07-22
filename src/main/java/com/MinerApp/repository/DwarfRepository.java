package com.MinerApp.repository;

import com.MinerApp.domain.Dwarf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DwarfRepository extends JpaRepository<Dwarf, Long> {
    Optional<Dwarf> findDwarfByName(String dwarfName);

    //JPQL query to be implemented
//    In JPQL (Java Persistence Query Language), the ?1 syntax is used to refer to the first positional parameter of the query. This means that the query will use the first argument passed to the method to replace ?1 in the query string.
//
//    Here’s a breakdown of how to use positional parameters in a JPQL query:
//    Positional Parameters: They are indicated by a ? followed by a number (?1, ?2, etc.). The number corresponds to the position of the parameter in the method signature.
    @Query("SELECT new list (d.name) from Dwarf d where d.productivity = ?1")
    List<String> getBestDwarves(int highestProductivity);

    @Query("SELECT MAX(d.productivity) from Dwarf d")
    int findTheDwarfWithTheHighestProductivity();

}
