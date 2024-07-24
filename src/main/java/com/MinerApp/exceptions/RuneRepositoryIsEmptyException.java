package com.MinerApp.exceptions;

public class RuneRepositoryIsEmptyException extends RuntimeException{

    private final String _MESSAGE = "There is no rune in carved to items...";

    public String get_MESSAGE() {
        return _MESSAGE;
    }

    public RuneRepositoryIsEmptyException() {
    }

}
