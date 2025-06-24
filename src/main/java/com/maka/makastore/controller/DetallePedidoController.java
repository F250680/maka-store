package com.maka.makastore.controller;

import com.maka.makastore.model.DetallePedido;
import com.maka.makastore.repository.DetallePedidoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-pedido")
public class DetallePedidoController {

    private final DetallePedidoRepository repo;

    public DetallePedidoController(DetallePedidoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<DetallePedido> listar() {
        return repo.findAll();
    }

    @PostMapping
    public DetallePedido crear(@RequestBody DetallePedido detalle) {
        return repo.save(detalle);
    }
}
