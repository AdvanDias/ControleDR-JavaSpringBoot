package com.DesafioMv.AdvDesafioMV.repositories;

import com.DesafioMv.AdvDesafioMV.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
