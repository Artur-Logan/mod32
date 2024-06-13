package com.arturlogan.projeto_mod32.repositories;

import com.arturlogan.projeto_mod32.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByCodigo(String codigo);
}
