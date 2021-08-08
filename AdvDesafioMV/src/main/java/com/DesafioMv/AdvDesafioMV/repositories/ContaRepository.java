package com.DesafioMv.AdvDesafioMV.repositories;

import com.DesafioMv.AdvDesafioMV.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ContaRepository extends JpaRepository<Conta, Long> {

}
