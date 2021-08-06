package com.DesafioMv.AdvDesafioMV.resources;

import com.DesafioMv.AdvDesafioMV.dto.response.MessageResponseDTO;
import com.DesafioMv.AdvDesafioMV.entities.Telefone;
import com.DesafioMv.AdvDesafioMV.exception.TelefoneNotFoundException;
import com.DesafioMv.AdvDesafioMV.services.TelefoneService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/telefone")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TelefoneResource {

    private TelefoneService telefoneService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createTelefone(@RequestBody Telefone telefone){
        return telefoneService.creatTelefone(telefone);
    }


    @GetMapping
    public List<Telefone> listAll(){
        return telefoneService.listall();
    }

    @GetMapping("/{id}")
    public Telefone findById(@PathVariable Long id) throws TelefoneNotFoundException {
        return telefoneService.FindByid(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTelefone(@PathVariable Long id) throws TelefoneNotFoundException {
        telefoneService.deletartelefone(id);
    }


    @PutMapping("/{id}")
    public MessageResponseDTO UpdateByidtelefone(@PathVariable Long id, @RequestBody Telefone telefone) throws TelefoneNotFoundException {
        return telefoneService.updattelefone(id,telefone);
    }

}
