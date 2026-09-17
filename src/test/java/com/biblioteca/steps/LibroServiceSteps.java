package com.biblioteca.steps;

import com.biblioteca.CucumberSpringConfiguration;
import com.biblioteca.model.Libro;
import com.biblioteca.service.LibroService;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibroServiceSteps extends CucumberSpringConfiguration {

    @Autowired
    private LibroService libroService;

    private Libro libroResultado;
    private List<Libro> listaLibros;
    private Exception excepcionCapturada;
    private Long idGenerado;

    // --- MÉTODOS EXITOSOS ---

    @Cuando("ejecuto crearLibro con título {string} y autor {string}")
    public void ejecutoCrearLibro(String titulo, String autor) {
        libroResultado = libroService.crearLibro(titulo, autor);
    }

    @Cuando("ejecuto crearLibro2 con título {string} y autor {string}")
    public void ejecutoCrearLibro2(String titulo, String autor) {
        libroResultado = libroService.crearLibro2(titulo, autor);
    }

    @Entonces("el libro resultante no es nulo y tiene título {string}")
    public void verificarLibroCreado(String titulo) {
        assertNotNull(libroResultado);
        assertEquals(titulo, libroResultado.getTitulo());
    }

    // --- PRUEBAS DE EXCEPCIONES Y NULL / BLANK ---

    @Cuando("intento crearLibro con título {string} y autor {string}")
    public void intentoCrearLibro(String titulo, String autor) {
        try {
            // Convierte la cadena vacía del feature en null si aplica
            String t = titulo.isEmpty() ? null : titulo;
            String a = autor.isEmpty() ? null : autor;
            libroService.crearLibro(t, a);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Cuando("intento crearLibro2 con título {string} y autor {string}")
    public void intentoCrearLibro2(String titulo, String autor) {
        try {
            String t = titulo.isEmpty() ? null : titulo;
            String a = autor.isEmpty() ? null : autor;
            libroService.crearLibro2(t, a);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("se lanza una excepción con mensaje {string}")
    public void verificarExcepcion(String mensajeEsperado) {
        assertNotNull(excepcionCapturada, "Se esperaba una excepción pero no se lanzó ninguna.");
        assertEquals(mensajeEsperado, excepcionCapturada.getMessage());
        excepcionCapturada = null; // Limpieza de estado
    }

    // --- CONSULTAS Y ELIMINACIÓN ---

    @Dado("que existe al menos un libro en la base de datos")
    public void cargarLibroBase() {
        libroService.crearLibro("Libro Base", "Autor Base");
    }

    @Cuando("solicito la lista de todos los libros")
    public void listarLibros() {
        listaLibros = libroService.listarLibros();
    }

    @Entonces("la lista devuelta no está vacía")
    public void verificarLista() {
        assertNotNull(listaLibros);
        assertFalse(listaLibros.isEmpty());
    }

    @Dado("que creo un libro para buscarlo posteriormente")
    public void crearLibroParaBuscar() {
        Libro l = libroService.crearLibro("Para Buscar", "Autor");
        idGenerado = l.getId();
    }

    @Cuando("busco el libro por su ID generado")
    public void buscarPorIdGenerado() {
        libroResultado = libroService.buscarLibro(idGenerado);
    }

    @Entonces("el libro encontrado no es nulo")
    public void verificarLibroEncontrado() {
        assertNotNull(libroResultado);
    }

    @Cuando("busco un libro con ID {long}")
    public void buscarPorId(Long id) {
        libroResultado = libroService.buscarLibro(id);
    }

    @Entonces("el resultado de la búsqueda es nulo")
    public void verificarLibroNulo() {
        assertNull(libroResultado);
    }

    @Dado("que creo un libro para eliminarlo posteriormente")
    public void crearLibroParaEliminar() {
        Libro l = libroService.crearLibro("Para Eliminar", "Autor");
        idGenerado = l.getId();
    }

    @Cuando("elimino el libro por su ID generado")
    public void eliminarPorIdGenerado() {
        libroService.eliminarLibro(idGenerado);
    }
}