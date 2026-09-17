package com.biblioteca.service;

import com.biblioteca.model.Libro;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PrestamoService {

    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public PrestamoService(
            UsuarioRepository usuarioRepository,
            LibroRepository libroRepository,
            PrestamoRepository prestamoRepository) {

        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo prestarLibro(Long usuarioId, Long libroId) {

        Usuario usuario =
                usuarioRepository.findById(usuarioId).orElse(null);

        Libro libro =
                libroRepository.findById(libroId).orElse(null);

        if (usuario == null) {
            throw new RuntimeException("Usuario inexistente");
        }

        if (libro == null) {
            throw new RuntimeException("Libro inexistente");
        }

        if (!usuario.isActivo()) {
            throw new RuntimeException("El usuario está inactivo");
        }

        if (usuario.isMoroso()) {
            throw new RuntimeException("El usuario tiene una deuda");
        }

        if (libro.isPrestado()) {
            throw new RuntimeException("El libro ya está prestado");
        }

        if (usuario.getPrestamos().size() >= 3) {
            throw new RuntimeException(
                    "El usuario alcanzó el máximo de préstamos");
        }

        Prestamo prestamo = new Prestamo(usuario, libro);

        libro.setPrestado(true);

        usuario.getPrestamos().add(prestamo);

        libroRepository.save(libro);
        usuarioRepository.save(usuario);

        return prestamoRepository.save(prestamo);
    }

	public double calcularRecargoA(Prestamo prestamo) {

    if (prestamo == null) {
        return 0;
    }

    long dias = ChronoUnit.DAYS.between(
            prestamo.getFechaPrestamo(),
            LocalDate.now());

    if (dias <= 7) {
        return 0;
    }

    if (dias <= 14) {
        return (dias - 7) * 100;
    }

    if (dias <= 21) {
        return (dias - 7) * 150;
    }

    if (dias <= 30) {
        return (dias - 7) * 200;
    }

    if (dias <= 45) {
        return (dias - 7) * 300;
    }

    if (dias <= 60) {
        return (dias - 7) * 400;
    }

    if (dias <= 90) {
        return (dias - 7) * 500;
    }

    return (dias - 7) * 750;
}


public double calcularRecargoB(Prestamo prestamo) {

    if (prestamo == null) {
        return 0;
    }

    long dias = ChronoUnit.DAYS.between(
            prestamo.getFechaPrestamo(),
            LocalDate.now());

    if (dias <= 7) {
        return 0;
    }

    if (dias <= 14) {
        return (dias - 7) * 100;
    }

    if (dias <= 21) {
        return (dias - 7) * 150;
    }

    if (dias <= 30) {
        return (dias - 7) * 200;
    }

    if (dias <= 45) {
        return (dias - 7) * 300;
    }

    if (dias <= 60) {
        return (dias - 7) * 400;
    }

    if (dias <= 90) {
        return (dias - 7) * 500;
    }

    return (dias - 7) * 750;
}	

    public void devolverLibro(Long prestamoId) {

        Prestamo prestamo =
                prestamoRepository.findById(prestamoId).orElse(null);

        if (prestamo == null) {
            throw new RuntimeException("Préstamo inexistente");
        }

        if (prestamo.isDevuelto()) {
            throw new RuntimeException("El préstamo ya fue devuelto");
        }

        prestamo.devolver();

        Libro libro = prestamo.getLibro();

        libro.setPrestado(false);

        libroRepository.save(libro);
        prestamoRepository.save(prestamo);
    }

    public void devolverLibroAntiguo(Long prestamoId) {


        Prestamo prestamo =
                prestamoRepository.findById(prestamoId).orElse(null);

        if (prestamo == null) {
            throw new RuntimeException("Préstamo inexistente");
        }

        if (prestamo.isDevuelto()) {
            throw new RuntimeException("El préstamo ya fue devuelto");
        }

        prestamo.devolver();

        Libro libro = prestamo.getLibro();

        libro.setPrestado(false);

        libroRepository.save(libro);
        prestamoRepository.save(prestamo);
    }

    public double calcularMulta(Prestamo prestamo) {

        if (prestamo == null) {
            return 0;
        }

        if (!prestamo.isDevuelto()) {

            long dias = ChronoUnit.DAYS.between(
                    prestamo.getFechaPrestamo(),
                    LocalDate.now());

            if (dias <= 7) {
                return 0;
            }

            if (dias <= 14) {
                return (dias - 7) * 100;
            }

            if (dias <= 30) {
                return (dias - 7) * 200;
            }

            return (dias - 7) * 500;
        }

        long dias = ChronoUnit.DAYS.between(
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucion());

        // DUPLICACIÓN INTENCIONAL
        if (dias <= 7) {
            return 0;
        }

        if (dias <= 14) {
            return (dias - 7) * 100;
        }

        if (dias <= 30) {
            return (dias - 7) * 200;
        }

        return (dias - 7) * 500;
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }
}