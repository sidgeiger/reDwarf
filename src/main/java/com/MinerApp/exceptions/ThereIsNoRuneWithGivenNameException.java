package com.MinerApp.exceptions;

public class ThereIsNoRuneWithGivenNameException extends RuntimeException {

    private String name;

    public ThereIsNoRuneWithGivenNameException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
