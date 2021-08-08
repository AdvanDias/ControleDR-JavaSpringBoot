package com.DesafioMv.AdvDesafioMV.services;


import com.DesafioMv.AdvDesafioMV.dto.response.MessageResponseDTO;
import com.DesafioMv.AdvDesafioMV.entities.Conta;
import com.DesafioMv.AdvDesafioMV.exception.ContaNotFoundException;
import com.DesafioMv.AdvDesafioMV.repositories.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContaService {

    private ContaRepository contaRepository;

    public List<Conta> listall() {
        List<Conta> contas = contaRepository.findAll();
        List<Conta> filtro = contas.stream().filter(c -> c.getStatus() == 'A').collect(Collectors.toList());
        return  filtro;
    }


    public MessageResponseDTO creatConta(Conta conta) {
        Conta saveConta = contaRepository.save(conta);
        return MessageResponseDTO
                .builder()
                .message("Created Conta with ID "+ saveConta.getId())
                .build();
    }

    public Conta FindByid(Long id) throws ContaNotFoundException {
        Conta conta = verifyIfExists(id);
        return conta;
    }

    public MessageResponseDTO deletarconta(Long id) throws ContaNotFoundException {
        Conta contabyId = verifyIfExists(id);
        contabyId.setStatus('C');
        Conta exclusaoLogicaConta = contaRepository.save(contabyId);
        return MessageResponseDTO
                .builder()
                .message("Conta with ID "+ exclusaoLogicaConta.getId())
                .build();
    }

    private Conta verifyIfExists(Long id) throws ContaNotFoundException{
        return contaRepository.findById(id).orElseThrow(() -> new ContaNotFoundException(id));
    }

}
