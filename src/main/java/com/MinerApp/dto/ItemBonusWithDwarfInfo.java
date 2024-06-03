package com.MinerApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemBonusWithDwarfInfo {

    private Long id;

    private String name;

    private int productivity;

    private List<ItemInfo> itemList;

}
