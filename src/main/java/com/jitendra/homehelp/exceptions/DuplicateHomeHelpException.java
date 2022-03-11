package com.jitendra.homehelp.exceptions;

public class DuplicateHomeHelpException extends Exception{
    public DuplicateHomeHelpException(String message) {
        super(message +" : Home Help Already Exists.");
    }

    public DuplicateHomeHelpException(String message, Throwable cause) {
        super(message +" : Home Help Already Exists.", cause);
    }
}
