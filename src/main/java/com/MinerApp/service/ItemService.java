package com.MinerApp.service;

import com.MinerApp.domain.Dwarf;
import com.MinerApp.domain.Item;
import com.MinerApp.domain.Rune;
import com.MinerApp.dto.CreateItemCommand;
import com.MinerApp.dto.ItemInfo;
import com.MinerApp.exceptions.DwarfNotExistsWithGivenId;
import com.MinerApp.exceptions.ItemNotExistsWithGivenId;
import com.MinerApp.repository.DwarfRepository;
import com.MinerApp.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ItemService {

    private ItemRepository itemRepository;

    private DwarfService dwarfService;

    private ModelMapper modelMapper;

    private RuneService runeService;


    @Autowired
    public ItemService(ItemRepository itemRepository, DwarfService dwarfService, ModelMapper modelMapper, RuneService runeService) {
        this.itemRepository = itemRepository;
        this.dwarfService = dwarfService;
        this.modelMapper = modelMapper;
        this.runeService = runeService;
    }

    public ItemInfo itemCrafter(CreateItemCommand command) {
        Item item = modelMapper.map(command, Item.class);
        Dwarf dwarf = dwarfService.findById(command.getDwarfId());
        item.setDwarf(dwarf);
        itemRepository.save(item);
        ItemInfo itemInfo = modelMapper.map(item, ItemInfo.class);
        itemInfo.setDwarfName(dwarf.getName());
        return itemInfo;
    }

    public Item findById(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if(optionalItem.isEmpty()){
            throw new ItemNotExistsWithGivenId(itemId);
        }
        return optionalItem.get();
    }

    public ItemInfo copyItemForDwarf(Long itemId, Long dwarfId) {

        Item item = this.findById(itemId);
        Dwarf dwarf = dwarfService.findById(dwarfId);

        Item newItem = new Item();
        newItem.setName(item.getName());
        newItem.setBonus(item.getBonus());
        newItem.setValue(item.getValue());
        newItem.setDwarf(dwarf);

        List<Rune> newRunes = runeService.runeListIterator(item.getRunes());
        for (Rune newRune : newRunes) {
            newRune.setItem(newItem);
        }

        Item savedItem = itemRepository.save(newItem);

        ItemInfo itemInfo = modelMapper.map(savedItem, ItemInfo.class);
        itemInfo.setDwarfName(dwarf.getName());
        return itemInfo;
    }

    public int findAveragePrice() {
        return (int) Math.round(itemRepository.findAveragePrice());
    }
}
