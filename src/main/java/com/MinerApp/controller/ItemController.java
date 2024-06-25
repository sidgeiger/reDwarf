package com.MinerApp.controller;

import com.MinerApp.dto.*;
import com.MinerApp.service.ItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/items")
@Slf4j
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) { this.itemService = itemService; }

    @PostMapping
    public ResponseEntity<ItemInfo> createItem(@Valid @RequestBody CreateItemCommand command) {
        log.info("Creating item for a dwarf with dto command...");
        ItemInfo itemInfo = itemService.itemCrafter(command);
        log.info("Item created successful!");
        return new ResponseEntity<>(itemInfo, HttpStatusCode.valueOf(201));
    }

    @PutMapping("/{itemId}/dwarf/{dwarfId}")
    public ResponseEntity<ItemInfo> copyItem(@PathVariable Long itemId, Long dwarfId) {
        log.info("Copy item for a Dwarf with all characteristics with given Dwarf ID...");
        ItemInfo itemInfo = itemService.copyItemForDwarf(itemId, dwarfId);
        log.info("Incrementing item bonuses done successful!");
        return new ResponseEntity<>(itemInfo, HttpStatusCode.valueOf(201));
    }
}
