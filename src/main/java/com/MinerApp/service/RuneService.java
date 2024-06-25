package com.MinerApp.service;

import com.MinerApp.domain.Dwarf;
import com.MinerApp.domain.Item;
import com.MinerApp.domain.Rune;
import com.MinerApp.dto.CreateRuneCommand;
import com.MinerApp.dto.ItemInfo;
import com.MinerApp.dto.RuneInfo;
import com.MinerApp.repository.RuneRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class RuneService {

    private RuneRepository runeRepository;

    private ItemService itemService;

    private ModelMapper modelMapper;

    @Autowired
    public RuneService(RuneRepository runeRepository, ItemService itemService, ModelMapper modelMapper) {
        this.runeRepository = runeRepository;
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }

    public RuneInfo runeCrafter(CreateRuneCommand command) {

        Rune rune = modelMapper.map(command, Rune.class);
        Item item = itemService.findById(command.getItemId());
        rune.setItem(item);
        runeRepository.save(rune);
        RuneInfo runeInfo = modelMapper.map(rune, RuneInfo.class);
        runeInfo.setItemId(item.getId());
        return runeInfo;

    }
}
