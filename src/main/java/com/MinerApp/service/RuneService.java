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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class RuneService {

    private RuneRepository runeRepository;

    private ItemService itemService;

    private ModelMapper modelMapper;

    @Autowired
    public RuneService(RuneRepository runeRepository, @Lazy ItemService itemService, ModelMapper modelMapper) {
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

    public List<Rune> runeListIterator(List<Rune> runeList) {
        List<Rune> newRuneListForSettingItemId = null;
        if (!runeList.isEmpty()) {
            newRuneListForSettingItemId = new ArrayList<>();
            for (Rune rune : runeList) {
                Rune newRune = new Rune();
                newRune.setBanned(rune.isBanned());
                newRune.setBonus(rune.getBonus());
                newRune.setName(rune.getName());
                newRune.setItem(rune.getItem());
                Rune savedRune = this.runeSave(newRune);
                newRuneListForSettingItemId.add(savedRune);
            }

        }
        return newRuneListForSettingItemId;
    }

    private Rune runeSave(Rune rune) {
        runeRepository.save(rune);
        return rune;
    }
}
