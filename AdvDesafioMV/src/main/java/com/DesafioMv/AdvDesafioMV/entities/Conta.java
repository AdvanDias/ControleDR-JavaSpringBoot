package com.DesafioMv.AdvDesafioMV.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;


    private String nomeInstituicao;
    private String nomeProprietario;
    private Double valor;
    private Double total;
    private char tipMovimentacoes;
    private int MovimentacoesCre;
    private int MovimentacoesDeb;
    private char status;
    private LocalDate dataCriacao;
    private LocalDate dataMovimentacao;




}

