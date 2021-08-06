package com.DesafioMv.AdvDesafioMV.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoTelefone {

    CASA("Residencial"),
    MOBILE("Celular"),
    COMERCIAL("Comercial");

    private final String descricao;
}
