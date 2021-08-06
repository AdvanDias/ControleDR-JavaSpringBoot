package com.DesafioMv.AdvDesafioMV.resources;

import com.DesafioMv.AdvDesafioMV.dto.response.MessageResponseDTO;
import com.DesafioMv.AdvDesafioMV.entities.Cliente;
import com.DesafioMv.AdvDesafioMV.exception.ClienteNotFoundException;
import com.DesafioMv.AdvDesafioMV.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cliente")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteResource {

    private ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createEmpresa(@RequestBody Cliente cliente){
        return clienteService.creatCliente(cliente);
    }


    @GetMapping
    public List<Cliente> listAll(){
        return clienteService.listall();
    }

    @GetMapping("/{id}")
    public Cliente findById(@PathVariable Long id) throws ClienteNotFoundException {
        return clienteService.FindByid(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable Long id) throws ClienteNotFoundException{
        clienteService.deletarcliente(id);
    }


    @PutMapping("/{id}")
    public MessageResponseDTO UpdateByidcliente(@PathVariable Long id, @RequestBody Cliente cliente) throws ClienteNotFoundException {
        return clienteService.updatcliente(id,cliente);
    }


}
