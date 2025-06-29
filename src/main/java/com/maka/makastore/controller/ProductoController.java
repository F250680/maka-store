package com.maka.makastore.controller;

import com.maka.makastore.model.Producto;
import com.maka.makastore.repository.ProductoRepository;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository repo;

    public ProductoController(ProductoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Producto> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Producto> buscarPorId(@PathVariable Long id) {
        return repo.findById(id);
    }

    @PostMapping
    public Producto crear(@RequestBody Producto p) {
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto p) {
        p.setIdProducto(id);
        return repo.save(p);
    }

    @PatchMapping("/{id}")
    public Producto actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Producto producto = repo.findById(id).orElseThrow();

        campos.forEach((clave, valor) -> {
            Field campo = ReflectionUtils.findField(Producto.class, clave);
            if (campo != null) {
                campo.setAccessible(true);
                ReflectionUtils.setField(campo, producto, valor);
            }
        });

        return repo.save(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
