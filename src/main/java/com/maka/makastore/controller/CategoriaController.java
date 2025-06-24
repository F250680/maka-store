package com.maka.makastore.controller;

import com.maka.makastore.model.Categoria;
import com.maka.makastore.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository repo;

    public CategoriaController(CategoriaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Categoria> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Categoria crear(@RequestBody Categoria c) {
        return repo.save(c);
    }
}
