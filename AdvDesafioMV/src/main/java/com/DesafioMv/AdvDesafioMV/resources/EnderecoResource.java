package com.DesafioMv.AdvDesafioMV.resources;

import com.DesafioMv.AdvDesafioMV.dto.response.MessageResponseDTO;
import com.DesafioMv.AdvDesafioMV.entities.Endereco;
import com.DesafioMv.AdvDesafioMV.exception.EnderecoNotFoundException;
import com.DesafioMv.AdvDesafioMV.services.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/endereco")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoResource {

    private EnderecoService enderecoService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createEmpresa(@RequestBody Endereco endereco){
        return enderecoService.creatEndereco(endereco);
    }


    @GetMapping
    public List<Endereco> listAll(){
        return enderecoService.listall();
    }

    @GetMapping("/{id}")
    public Endereco findById(@PathVariable Long id) throws EnderecoNotFoundException {
        return enderecoService.FindByid(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEndereco(@PathVariable Long id) throws EnderecoNotFoundException {
        enderecoService.deletarendereco(id);
    }


    @PutMapping("/{id}")
    public MessageResponseDTO UpdateByidendereco(@PathVariable Long id, @RequestBody Endereco endereco) throws EnderecoNotFoundException {
        return enderecoService.updatendereco(id,endereco);
    }

}
