package com.MinerApp.controller;

import com.MinerApp.dto.CreateDwarfCommand;
import com.MinerApp.dto.CreateItemCommand;
import com.MinerApp.dto.DwarfInfo;
import com.MinerApp.dto.ItemInfo;
import com.MinerApp.service.ItemService;
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
}
