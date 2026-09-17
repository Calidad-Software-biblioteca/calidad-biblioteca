package com.biblioteca.controller;

import com.biblioteca.model.Prestamo;
import com.biblioteca.service.PrestamoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public Prestamo prestar(
            @RequestParam Long usuarioId,
            @RequestParam Long libroId) {

        return prestamoService.prestarLibro(usuarioId, libroId);
    }

    @PutMapping("/{id}/devolver")
    public void devolver(@PathVariable Long id) {
        prestamoService.devolverLibro(id);
    }

    @GetMapping
    public List<Prestamo> listar() {
        return prestamoService.listarPrestamos();
    }

    @GetMapping("/{id}/multa")
    public double multa(@PathVariable Long id) {

        Prestamo prestamo = prestamoService
                .listarPrestamos()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        return prestamoService.calcularMulta(prestamo);
    }
}