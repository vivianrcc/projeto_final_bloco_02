package com.generation.projeto_final_bloco_02.projeto_final_bloco_02.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.projeto_final_bloco_02.projeto_final_bloco_02.model.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    public List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
}