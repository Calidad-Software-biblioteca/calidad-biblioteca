package com.biblioteca.model;

import jakarta.persistence.*;

@Entity
public class Libro {

    private static final String NO_SE_PUEDE_PRESTAR = "No se puede prestar";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String titulo;
    private String autor;
    private boolean prestado;


    public Libro() {
    }

    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = false;
    }

    // --- GETTERS ---

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isPrestado() {
        return prestado;
    }

    // --- SETTERS ---

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }



    public String prestar() {

        if (titulo == null || titulo.isEmpty()
                || autor == null || autor.isEmpty()) {
            return NO_SE_PUEDE_PRESTAR;
        }

        if (prestado) {
            return "El libro ya está prestado";
        }

        prestado = true;
        return "Libro prestado correctamente";
    }

}

