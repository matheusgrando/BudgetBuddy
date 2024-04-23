package br.com.fiap.budgetbuddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.budgetbuddy.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    Page<Movimentacao> findByCategoriaNome(String categoria, Pageable pageable);

    @Query("SELECT m FROM Movimentacao m WHERE MONTH(m.data) = ?1") 
    Page<Movimentacao> findByMes(Integer mes, Pageable pageable);
    
    @Query("SELECT m FROM Movimentacao m WHERE MONTH(m.data) = ?2 AND m.categoria.nome = ?1 ") 
    Page<Movimentacao> findByCategoriaNomeAndMes(String categoria, Integer mes, Pageable pageable);


    
}
