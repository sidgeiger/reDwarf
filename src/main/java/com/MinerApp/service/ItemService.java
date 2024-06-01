package com.MinerApp.service;

import com.MinerApp.domain.Dwarf;
import com.MinerApp.domain.Item;
import com.MinerApp.dto.CreateItemCommand;
import com.MinerApp.dto.ItemInfo;
import com.MinerApp.repository.DwarfRepository;
import com.MinerApp.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ItemService {

    private ItemRepository itemRepository;

    private DwarfService dwarfService;

    private ModelMapper modelMapper;


    @Autowired
    public ItemService(ItemRepository itemRepository, DwarfService dwarfService, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.dwarfService = dwarfService;
        this.modelMapper = modelMapper;
    }

    public ItemInfo itemCrafter(CreateItemCommand command) {
        Item item = modelMapper.map(command, Item.class);
        Dwarf dwarf = dwarfService.findById(command.getDwarfId());
    }
}
