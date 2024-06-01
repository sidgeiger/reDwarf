package com.MinerApp.exceptions;

public class DwarfNotExistsWithGivenId extends RuntimeException {

    private Long id;

    public DwarfNotExistsWithGivenId(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
