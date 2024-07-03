package com.MinerApp.exceptions;

public class LazyMuddaFakkaException extends RuntimeException {

    private final String _MESSAGE = "There is no dwarf in the mine...";

    public String get_MESSAGE() {
        return _MESSAGE;
    }

    public LazyMuddaFakkaException() {
    }
}
