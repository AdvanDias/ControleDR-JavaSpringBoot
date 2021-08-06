package com.DesafioMv.AdvDesafioMV.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TelefoneNotFoundException extends Exception{
    public TelefoneNotFoundException(Long id) {
        super("Telefone not found with Id " + id);
    }
}
