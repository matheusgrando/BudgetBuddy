package br.com.fiap.budgetbuddy.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.budgetbuddy.model.Movimentacao;
import br.com.fiap.budgetbuddy.repository.MovimentacaoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("movimentacao")
public class MovimentacaoController {

    @Autowired
    MovimentacaoRepository repository;

    @GetMapping
    public Page<Movimentacao> index(
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) Integer mes,
        @PageableDefault(size = 5, sort = "data", direction = Direction.DESC) Pageable pageable
    ){

        if (mes != null && categoria !=null){
            return repository.findByCategoriaNomeAndMes(categoria, mes, pageable); 
        }

        if (mes != null){
            return repository.findByMes(mes, pageable);
        }

        if (categoria != null){
            return repository.findByCategoriaNome(categoria, pageable);
        }
        
        return repository.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Movimentacao create(@RequestBody @Valid Movimentacao movimentacao){
        return repository.save(movimentacao);
    }

    @GetMapping
    public List<Movimentacao> index(){
        return repository.findAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id){
        repository.deleteById(id);
    }
    
}
