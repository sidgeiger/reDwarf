package com.MinerApp.controller;

import com.MinerApp.dto.CreateItemCommand;
import com.MinerApp.dto.CreateRuneCommand;
import com.MinerApp.dto.ItemInfo;
import com.MinerApp.dto.RuneInfo;
import com.MinerApp.service.ItemService;
import com.MinerApp.service.RuneService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
