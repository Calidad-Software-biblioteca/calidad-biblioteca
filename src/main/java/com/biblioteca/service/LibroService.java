package com.biblioteca.service;

import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Libro crearLibro(String titulo, String autor) {

        if (titulo == null || titulo.isBlank()) {
            throw new RuntimeException("El título es obligatorio");
        }

        if (autor == null || autor.isBlank()) {
            throw new RuntimeException("El autor es obligatorio");
        }

        Libro libro = new Libro(titulo, autor);

        return libroRepository.save(libro);
    }

    public Libro crearLibro2(String titulo, String autor) {

        if (titulo == null || titulo.isBlank()) {
            throw new RuntimeException("El título es obligatorio");
        }

        if (autor == null || autor.isBlank()) {
            throw new RuntimeException("El autor es obligatorio");
        }

        Libro libro = new Libro(titulo, autor);

        return libroRepository.save(libro);
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public Libro buscarLibro(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    public void eliminarLibro(Long id) {
        libroRepository.deleteById(id);
    }
}

