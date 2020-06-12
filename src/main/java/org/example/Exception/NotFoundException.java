package org.example.Exception;

import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundException extends Exception {
    public NotFoundException(String msg)
    {
        super(msg);
    }
}
