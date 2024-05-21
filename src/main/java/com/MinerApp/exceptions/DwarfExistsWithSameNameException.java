package com.MinerApp.exceptions;

public class DwarfExistsWithSameNameException extends RuntimeException{
    private String name;

    public DwarfExistsWithSameNameException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
