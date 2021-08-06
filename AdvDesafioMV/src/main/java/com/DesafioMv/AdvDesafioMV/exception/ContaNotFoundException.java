package com.DesafioMv.AdvDesafioMV.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContaNotFoundException extends Exception{
    public ContaNotFoundException(Long id) {
        super("Conta not found with Id " + id);
    }
}
