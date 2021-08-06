package com.DesafioMv.AdvDesafioMV.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Id_clinte")
    private Cliente cliente;

    private String rua;
    private String bairro;
    private String complemento;
    private String cidade;
    private String cep;
    private String uf;
    private int numero;

}
