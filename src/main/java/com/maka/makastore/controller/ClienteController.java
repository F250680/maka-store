package com.maka.makastore.controller;

import com.maka.makastore.model.Cliente;
import com.maka.makastore.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository repo;

    public ClienteController(ClienteRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Cliente> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente c) {
        return repo.save(c);
    }
}
