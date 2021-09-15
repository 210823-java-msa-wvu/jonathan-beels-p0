package com.revature.utils;

public class NoneOverdueException  extends Exception {
    public NoneOverdueException(String errorMessage) {
        super(errorMessage);
    }
}
