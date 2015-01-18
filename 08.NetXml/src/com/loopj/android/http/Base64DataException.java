package com.loopj.android.http;

import java.io.IOException;

@SuppressWarnings("serial")
public class Base64DataException extends IOException {
    public Base64DataException(String detailMessage) {
        super(detailMessage);
    }
}