package com.MinerApp.service;

import com.MinerApp.domain.Dwarf;
import com.MinerApp.domain.Item;
import com.MinerApp.domain.Rune;
import com.MinerApp.dto.CreateDwarfCommand;
import com.MinerApp.dto.DwarfInfo;
import com.MinerApp.dto.ItemBonusWithDwarfInfo;
import com.MinerApp.dto.ItemInfo;
import com.MinerApp.exceptions.DwarfExistsWithSameNameException;
import com.MinerApp.exceptions.DwarfNotExistsWithGivenId;
import com.MinerApp.exceptions.LazyMuddaFakkaException;
import com.MinerApp.repository.DwarfRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class DwarfService {

    private DwarfRepository dwarfRepository;

    private ModelMapper modelMapper;

    @Autowired
    public DwarfService(DwarfRepository dwarfRepository, ModelMapper modelMapper) {
        this.dwarfRepository = dwarfRepository;
        this.modelMapper = modelMapper;
    }

    //this object is created without using ModelMapper configuration
    public DwarfInfo dwarfSpawner(CreateDwarfCommand command) {
        dwarfNameValidator(command.getName());
        Dwarf dwarf = new Dwarf();
        dwarf.setName(command.getName());
        dwarf.setProductivity(command.getProductivity());
        dwarfRepository.save(dwarf);

        DwarfInfo dwarfInfo = new DwarfInfo();
        dwarfInfo.setId(dwarf.getId());
        dwarfInfo.setName(dwarf.getName());
        dwarfInfo.setProductivity(dwarf.getProductivity());

        return dwarfInfo;
    }

    private void dwarfNameValidator(String dwarfName) {
        Optional<Dwarf> optionalDwarf = dwarfRepository.findDwarfByName(dwarfName);
        if(optionalDwarf.isPresent()){
            throw new DwarfExistsWithSameNameException(dwarfName);
        }
    }

    public Dwarf findById(Long dwarfId) {
        Optional<Dwarf> optionalDwarf = dwarfRepository.findById(dwarfId);
        if(optionalDwarf.isEmpty()){
            throw new DwarfNotExistsWithGivenId(dwarfId);
        }
        return optionalDwarf.get();
    }

    public ItemBonusWithDwarfInfo itemBonusIncrementer(Long id) {
        Dwarf dwarf = findById(id);

        dwarf.getItems().forEach(item -> item.setBonus(item.getBonus() + 1));

        List<ItemInfo> itemInfoList = dwarf.getItems().stream()
                .map(item -> {
                    ItemInfo itemInfo = modelMapper.map(item, ItemInfo.class);
                    itemInfo.setDwarfName(item.getDwarf().getName());
                    return itemInfo;
                })
                .collect(Collectors.toList());

        ItemBonusWithDwarfInfo itemBonusWithDwarfInfo = modelMapper.map(dwarf, ItemBonusWithDwarfInfo.class);
        itemBonusWithDwarfInfo.setItemList(itemInfoList);

        return itemBonusWithDwarfInfo;
    }

    public int getDays() {
        if (dwarfRepository.count() != 0){
            int goldToMine = 1000000;
            int productivityOfAllDwarves = 0;
            for (Dwarf dwarf : dwarfRepository.findAll()) {
                productivityOfAllDwarves += dwarf.getProductivity();
                for (Item item : dwarf.getItems()) {
                    productivityOfAllDwarves += item.getBonus();
                    for (Rune rune : item.getRunes()) {
                        productivityOfAllDwarves += rune.getBonus();
                    }
                }
            }
            return this.daysNeeded(productivityOfAllDwarves, goldToMine);
        }
        throw new LazyMuddaFakkaException();
    }

    private int daysNeeded(int productivityOfAllDwarves, int goldToMine) {
        return (int) Math.ceil((double) goldToMine / productivityOfAllDwarves);
    }
}
