package com.MinerApp.controller;

import com.MinerApp.dto.CreateDwarfCommand;
import com.MinerApp.dto.DwarfInfo;
import com.MinerApp.service.DwarfService;
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
@RequestMapping("/api/dwarves/")
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
}
