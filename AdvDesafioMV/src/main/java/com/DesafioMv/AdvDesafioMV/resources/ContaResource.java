package com.DesafioMv.AdvDesafioMV.resources;


import com.DesafioMv.AdvDesafioMV.dto.response.MessageResponseDTO;
import com.DesafioMv.AdvDesafioMV.entities.Conta;
import com.DesafioMv.AdvDesafioMV.entities.Endereco;
import com.DesafioMv.AdvDesafioMV.exception.ContaNotFoundException;
import com.DesafioMv.AdvDesafioMV.services.ContaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conta")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContaResource {

    private ContaService contaService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createConta(@RequestBody Conta conta){
        return contaService.creatConta(conta);
    }


    @GetMapping
    public List<Conta> listAll(){
        return contaService.listall();
    }

    @GetMapping("/{id}")
    public Conta findById(@PathVariable Long id) throws ContaNotFoundException {
        return contaService.FindByid(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponseDTO deleteConta(@PathVariable Long id) throws ContaNotFoundException {
       return contaService.deletarconta(id);
    }


}
