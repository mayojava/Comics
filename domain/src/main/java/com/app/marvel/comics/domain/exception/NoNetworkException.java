package com.app.marvel.comics.domain.exception;

public class NoNetworkException extends RuntimeException {
    public NoNetworkException() { super("No internet connection"); }
}
