package com.arturlogan.projeto_mod32.repositories;

import com.arturlogan.projeto_mod32.entities.Cliente;
import com.arturlogan.projeto_mod32.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    @Query("SELECT DISTINCT v FROM Venda v LEFT JOIN FETCH v.cliente LEFT JOIN FETCH v.produtos WHERE v.id = :id")
    Optional<Venda> findByIdWithCollections(Long id);
    Optional<Venda> findByCodigo(String codigo);
}
