package com.DesafioMv.AdvDesafioMV.services;

import com.DesafioMv.AdvDesafioMV.dto.response.MessageResponseDTO;
import com.DesafioMv.AdvDesafioMV.entities.Endereco;
import com.DesafioMv.AdvDesafioMV.exception.EnderecoNotFoundException;
import com.DesafioMv.AdvDesafioMV.repositories.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoService {

    private EnderecoRepository enderecoRepository;

    public List<Endereco> listall() {
        return  enderecoRepository.findAll();
    }


    public MessageResponseDTO creatEndereco(Endereco endereco) {
        Endereco saveEndereco = enderecoRepository.save(endereco);
        return MessageResponseDTO
                .builder()
                .message("Created Endereco with ID "+ saveEndereco.getId())
                .build();
    }

    public Endereco FindByid(Long id) throws EnderecoNotFoundException {
        Endereco endereco = verifyIfExists(id);
        return endereco;
    }

    public void deletarendereco(Long id) throws EnderecoNotFoundException {
        Endereco endereco = verifyIfExists(id);
        enderecoRepository.deleteById(id);
    }

    public MessageResponseDTO updatendereco(Long id, Endereco endereco) throws EnderecoNotFoundException {
        verifyIfExists(id);
        Endereco enderecoUpdate = enderecoRepository.save(endereco);
        return MessageResponseDTO
                .builder()
                .message("Update Endereco with ID "+ enderecoUpdate.getId())
                .build();
    }

    private Endereco verifyIfExists(Long id) throws EnderecoNotFoundException{
        return enderecoRepository.findById(id).orElseThrow(() -> new EnderecoNotFoundException(id));
    }

}
