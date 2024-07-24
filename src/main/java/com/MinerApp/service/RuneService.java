package com.MinerApp.service;

import com.MinerApp.domain.Item;
import com.MinerApp.domain.Rune;
import com.MinerApp.dto.CreateRuneCommand;
import com.MinerApp.dto.RuneInfo;
import com.MinerApp.exceptions.RuneRepositoryIsEmptyException;
import com.MinerApp.exceptions.ThereIsNoRuneWithGivenNameException;
import com.MinerApp.repository.RuneRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return runeList.stream()
                .map(rune -> {
                    Rune newRune = new Rune();
                    newRune.setBanned(rune.isBanned());
                    newRune.setBonus(rune.getBonus());
                    newRune.setName(rune.getName());
                    newRune.setItem(rune.getItem());
                    return this.runeSave(newRune);
                })
                .collect(Collectors.toList());
    }

    private Rune runeSave(Rune rune) {
        runeRepository.save(rune);
        return rune;
    }

    public String runeBanner(String runeName) {
        if (runeRepository.count() == 0) {
            throw new RuneRepositoryIsEmptyException();
        }
        List<Rune> runesForBan = new ArrayList<>();
        for (Rune rune : runeRepository.findAll()) {
            if (rune.getName().equals(runeName)) {
                runesForBan.add(rune);
            }
        }
        if (runesForBan.isEmpty()) {
            throw new ThereIsNoRuneWithGivenNameException(runeName);
        }
        for (Rune rune : runesForBan) {
            rune.setBanned(true);
            rune.setItem(null);

        }
        return "Banned " + runesForBan.size() + " rune(s)!";
    }
}
