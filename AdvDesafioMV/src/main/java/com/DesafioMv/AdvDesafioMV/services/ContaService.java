package com.DesafioMv.AdvDesafioMV.services;


import com.DesafioMv.AdvDesafioMV.dto.response.MessageResponseDTO;
import com.DesafioMv.AdvDesafioMV.entities.Conta;
import com.DesafioMv.AdvDesafioMV.exception.ContaNotFoundException;
import com.DesafioMv.AdvDesafioMV.repositories.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContaService {

    private ContaRepository clienteRepository;

    public List<Conta> listall() {
        return  clienteRepository.findAll();
    }


    public MessageResponseDTO creatConta(Conta cliente) {
        Conta saveConta = clienteRepository.save(cliente);
        return MessageResponseDTO
                .builder()
                .message("Created Empresa with ID "+ saveConta.getId())
                .build();
    }

    public Conta FindByid(Long id) throws ContaNotFoundException {
        Conta cliente = verifyIfExists(id);
        return cliente;
    }

    public void deletarconta(Long id) throws ContaNotFoundException {
        Conta cliente = verifyIfExists(id);
        clienteRepository.deleteById(id);
    }

    private Conta verifyIfExists(Long id) throws ContaNotFoundException{
        return clienteRepository.findById(id).orElseThrow(() -> new ContaNotFoundException(id));
    }

}
