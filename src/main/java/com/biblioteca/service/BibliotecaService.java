package com.biblioteca.service;

import com.biblioteca.model.Libro;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class BibliotecaService {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    public BibliotecaService(
            LibroRepository libroRepository,
            UsuarioRepository usuarioRepository,
            PrestamoRepository prestamoRepository) {

        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public void prestarLibro(Long libroId, Long usuarioId) {

        Usuario usuario = buscarUsuario(usuarioId);

        if (usuario == null || !usuario.isActivo()) {
            return;
        }

        Libro libro = buscarLibro(libroId);

        if (libro == null || libro.isPrestado()) {
            return;
        }

        marcarComoPrestado(libro);

        guardarPrestamo(libro, usuario);
    }

    private Usuario buscarUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElse(null);
    }

    private Libro buscarLibro(Long libroId) {
        return libroRepository.findById(libroId).orElse(null);
    }

    private void marcarComoPrestado(Libro libro) {
        libro.setPrestado(true);
        libroRepository.save(libro);
    }

    private void guardarPrestamo(Libro libro, Usuario usuario) {
        Prestamo prestamo = new Prestamo();

        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);

        prestamoRepository.save(prestamo);
    }
}