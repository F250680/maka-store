package com.maka.makastore.controller;

import com.maka.makastore.model.Pedido;
import com.maka.makastore.repository.PedidoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository repo;

    public PedidoController(PedidoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Pedido> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Pedido crear(@RequestBody Pedido p) {
        return repo.save(p);
    }
}
