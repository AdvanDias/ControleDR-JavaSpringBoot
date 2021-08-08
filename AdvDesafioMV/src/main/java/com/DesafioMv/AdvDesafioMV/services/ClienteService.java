package com.DesafioMv.AdvDesafioMV.services;

import com.DesafioMv.AdvDesafioMV.dto.response.MessageResponseDTO;
import com.DesafioMv.AdvDesafioMV.entities.Cliente;
import com.DesafioMv.AdvDesafioMV.exception.ClienteNotFoundException;
import com.DesafioMv.AdvDesafioMV.repositories.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteService {

    private ClienteRepository clienteRepository;

    public List<Cliente> listall() {
        return  clienteRepository.findAll();
    }


    public MessageResponseDTO creatCliente(Cliente cliente) {
        Cliente saveCliente = clienteRepository.save(cliente);
        return MessageResponseDTO
                .builder()
                .message("Created Cliente with ID "+ saveCliente.getId())
                .build();
    }

    public Cliente FindByid(Long id) throws ClienteNotFoundException {
        Cliente cliente = verifyIfExists(id);
        return cliente;
    }

    public void deletarcliente(Long id) throws ClienteNotFoundException {
        Cliente cliente = verifyIfExists(id);
        clienteRepository.deleteById(id);
    }

    public MessageResponseDTO updatcliente(Long id, Cliente cliente) throws ClienteNotFoundException {
        verifyIfExists(id);
        Cliente clienteUpdate = clienteRepository.save(cliente);
        return MessageResponseDTO
                .builder()
                .message("Created Cliente with ID "+ clienteUpdate.getId())
                .build();
    }

    private Cliente verifyIfExists(Long id) throws ClienteNotFoundException{
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
    }

}

