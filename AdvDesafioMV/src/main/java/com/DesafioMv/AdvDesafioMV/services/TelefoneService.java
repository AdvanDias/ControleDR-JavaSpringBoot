package com.DesafioMv.AdvDesafioMV.services;


import com.DesafioMv.AdvDesafioMV.dto.response.MessageResponseDTO;
import com.DesafioMv.AdvDesafioMV.entities.Telefone;
import com.DesafioMv.AdvDesafioMV.exception.TelefoneNotFoundException;
import com.DesafioMv.AdvDesafioMV.repositories.TelefoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TelefoneService {

    private TelefoneRepository telefoneRepository;

    public List<Telefone> listall() {
        return  telefoneRepository.findAll();
    }


    public MessageResponseDTO creatTelefone(Telefone telefone) {
        Telefone saveTelefone = telefoneRepository.save(telefone);
        return MessageResponseDTO
                .builder()
                .message("Created Telefone with ID "+ saveTelefone.getId())
                .build();
    }

    public Telefone FindByid(Long id) throws TelefoneNotFoundException {
        Telefone telefone = verifyIfExists(id);
        return telefone;
    }

    public void deletartelefone(Long id) throws TelefoneNotFoundException {
        Telefone telefone = verifyIfExists(id);
        telefoneRepository.deleteById(id);
    }

    public MessageResponseDTO updattelefone(Long id, Telefone telefone) throws TelefoneNotFoundException {
        verifyIfExists(id);
        Telefone telefoneUpdate = telefoneRepository.save(telefone);
        return MessageResponseDTO
                .builder()
                .message("Created Telefone with ID "+ telefoneUpdate.getId())
                .build();
    }

    private Telefone verifyIfExists(Long id) throws TelefoneNotFoundException{
        return telefoneRepository.findById(id).orElseThrow(() -> new TelefoneNotFoundException(id));
    }

}
