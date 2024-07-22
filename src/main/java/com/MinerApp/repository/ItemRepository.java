package com.MinerApp.repository;

import com.MinerApp.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByName(String itemName);
    Optional<Item> findItemByValue(int value);
    Optional<Item> findItemByBonus(int bonus);

    @Query("SELECT avg(i.value) from Item i")
    double findAveragePrice();
}
