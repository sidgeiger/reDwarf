package com.MinerApp.controller;

import com.MinerApp.dto.*;
import com.MinerApp.service.RuneService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/runes")
@Slf4j
public class RuneController {

    private RuneService runeService;

    @Autowired
    public RuneController(RuneService runeService) { this.runeService = runeService; }

    @PostMapping
    public ResponseEntity<RuneInfo> createRune(@Valid @RequestBody CreateRuneCommand command) {
        log.info("Creating rune for an item with dto command...");
        RuneInfo runeInfo = runeService.runeCrafter(command);
        log.info("Rune created successful!");
        return new ResponseEntity<>(runeInfo, HttpStatusCode.valueOf(201));
    }

    @DeleteMapping("/{runeName}")
    public ResponseEntity<String> deleteRune(@PathVariable String runeName) {
        log.info("Searching for rune(s) to delete...");
        String response = runeService.runeBanner(runeName);
        log.info("Rune(s) deleted successfully!");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<List<RunesWithAvgBonusesInfo>> getRunesWithAvgBonuses() {
        log.info("Searching for the runes with avg bonus values...");
        List<RunesWithAvgBonusesInfo> runesWithAvgBonusesInfo = runeService.runesWithAvgBonusesDistinctNames();
        log.info("Got the rune list with AVG bonus values!");
        return new ResponseEntity<>(runesWithAvgBonusesInfo,  HttpStatusCode.valueOf(200));
    }

}
