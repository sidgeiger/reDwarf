package com.MinerApp.controller;

import com.MinerApp.dto.CreateDwarfCommand;
import com.MinerApp.dto.DwarfInfo;
import com.MinerApp.dto.ItemBonusWithDwarfInfo;
import com.MinerApp.service.DwarfService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/dwarves")
@Slf4j
public class DwarfController {
    private DwarfService dwarfService;

    @Autowired
    public DwarfController(DwarfService dwarfService) {
        this.dwarfService = dwarfService;
    }

    @PostMapping
    public ResponseEntity<DwarfInfo> createDwarf(@Valid @RequestBody CreateDwarfCommand command) {
        log.info("Creating Dwarf with dto command...");
        DwarfInfo dwarfInfo = dwarfService.dwarfSpawner(command);
        log.info("Dwarf created successful!");
        return new ResponseEntity<>(dwarfInfo, HttpStatusCode.valueOf(201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemBonusWithDwarfInfo> itemBonus(@PathVariable Long id) {
        log.info("Incrementing item bonuses for Dwarf with given ID...");
        ItemBonusWithDwarfInfo itemBonusWithDwarfInfo = dwarfService.itemBonusIncrementer(id);
        log.info("Incrementing item bonuses done successful!");
        return new ResponseEntity<>(itemBonusWithDwarfInfo, HttpStatusCode.valueOf(202));
    }

    @GetMapping
    public ResponseEntity<String> getDays() {
        log.info("Calculating Dwarf productivity in mine...");
        int numberOfDays = dwarfService.getDays();
        return new ResponseEntity<>("The number of days to mine 1 000 000 Gold is: "+ numberOfDays,  HttpStatusCode.valueOf(201));
    }
}
