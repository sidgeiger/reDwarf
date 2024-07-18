package com.MinerApp.service;

import com.MinerApp.domain.Dwarf;
import com.MinerApp.domain.Rune;
import com.MinerApp.domain.Item;
import com.MinerApp.dto.*;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            this.isTheMineEmpty();
            int goldToMine = 1000;
            int productivityOfAllDwarves = dwarfRepository.findAll().stream()
                    .flatMap(dwarf -> Stream.concat(
                            Stream.of(dwarf.getProductivity()),
                            dwarf.getItems().stream()
                                    .flatMap(item -> Stream.concat(
                                            Stream.of(item.getBonus()),
                                            item.getRunes().stream().map(Rune::getBonus)
                                    ))
                    ))
                    .mapToInt(Integer::intValue)
                    .sum();
            return this.daysNeeded(productivityOfAllDwarves, goldToMine);
     }

    private int daysNeeded(int productivityOfAllDwarves, int goldToMine) {
        return (int) Math.ceil((double) goldToMine / productivityOfAllDwarves);
    }

    private void isTheMineEmpty() {
        if (dwarfRepository.count() == 0) {
            throw new LazyMuddaFakkaException();
        }
    }

    public List<String> bestDwarvesInMine() {
        this.isTheMineEmpty();
        List<String> bestDwarvesNames = dwarfRepository.getBestDwarves();
        return bestDwarvesNames;
    }
}
