package com.MinerApp.service;

import com.MinerApp.dto.CreateRuneCommand;
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
        return null;
    }
}
