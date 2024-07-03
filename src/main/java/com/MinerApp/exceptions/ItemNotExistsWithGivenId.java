package com.MinerApp.exceptions;

public class ItemNotExistsWithGivenId extends RuntimeException {

    private Long id;

    public ItemNotExistsWithGivenId(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
