package br.com.fiap.budgetbuddy.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.budgetbuddy.model.Categoria;
import br.com.fiap.budgetbuddy.repository.CategoriaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("categoria")
@CacheConfig(cacheNames = "categorias")
@Slf4j
public class CategoriaController {

    @Autowired
    CategoriaRepository repository;


    @GetMapping
    @Cacheable
    public List<Categoria> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    public Categoria create(@RequestBody @Valid Categoria categoria) {
        log.info("cadastrando categoria " + categoria);
        return repository.save(categoria);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Categoria> show(@PathVariable Long id) {
        log.info("buscar categoria por id {} ", id);
        
        return repository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
        
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando categoria com id {} ", id);
        
        verifyIfExists(id);
        repository.deleteById(id);
    }
    
    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    public Categoria atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        log.info("atualizando categoria com id {} para {}", id, categoria);

        verifyIfExists(id);
        categoria.setId(id);
        return repository.save(categoria);

    }

    private void verifyIfExists(Long id) {
        repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe categoria com o id informado"));
    }

}
