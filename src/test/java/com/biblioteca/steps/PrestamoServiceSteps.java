package com.biblioteca.steps;

import com.biblioteca.CucumberSpringConfiguration;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.PrestamoRepository;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.PrestamoService;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrestamoServiceSteps extends CucumberSpringConfiguration {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    private Exception excepcionCapturada;
    private Prestamo prestamoCreado;
    private double resultadoCalculo;
    private List<Prestamo> listaPrestamos;

    // --- PREPARACIÓN DE DATOS (DADO) ---

    @Dado("que existe un usuario activo de id {long}")
    public void crearUsuarioActivo(Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setActivo(true);
        usuario.setMoroso(false);
        usuario.setPrestamos(new ArrayList<>());
        usuarioRepository.save(usuario);
    }

    @Dado("que existe un usuario inactivo de id {long}")
    public void crearUsuarioInactivo(Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setActivo(false);
        usuario.setMoroso(false);
        usuario.setPrestamos(new ArrayList<>());
        usuarioRepository.save(usuario);
    }

    @Dado("que existe un usuario moroso de id {long}")
    public void crearUsuarioMoroso(Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setActivo(true);
        usuario.setMoroso(true);
        usuario.setPrestamos(new ArrayList<>());
        usuarioRepository.save(usuario);
    }

    @Dado("que existe un usuario activo con tres prestamos de id {long}")
    public void crearUsuarioConMaximoPrestamos(Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setActivo(true);
        usuario.setMoroso(false);
        List<Prestamo> prestamos = new ArrayList<>();
        prestamos.add(new Prestamo());
        prestamos.add(new Prestamo());
        prestamos.add(new Prestamo());
        usuario.setPrestamos(prestamos);
        usuarioRepository.save(usuario);
    }

    @Dado("que existe un libro disponible de id {long}")
    public void crearLibroDisponible(Long id) {
        Libro libro = new Libro();
        libro.setId(id);
        libro.setPrestado(false);
        libroRepository.save(libro);
    }

    @Dado("que existe un libro ya prestado de id {long}")
    public void crearLibroPrestado(Long id) {
        Libro libro = new Libro();
        libro.setId(id);
        libro.setPrestado(true);
        libroRepository.save(libro);
    }

    @Dado("que existe un préstamo activo de id {long}")
    public void crearPrestamoActivo(Long id) {
        Libro libro = new Libro();
        libro.setPrestado(true);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setId(id);
        prestamo.setDevuelto(false);
        prestamo.setLibro(libro);
        prestamoRepository.save(prestamo);
    }

    @Dado("que existe un préstamo devuelto de id {long}")
    public void crearPrestamoDevuelto(Long id) {
        Libro libro = new Libro();
        libro.setPrestado(false);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setId(id);
        prestamo.setDevuelto(true);
        prestamo.setLibro(libro);
        prestamoRepository.save(prestamo);
    }

    // --- ACCIONES (CUANDO) ---

    @Cuando("intento prestar un libro con usuario id {long} y libro id {long}")
    public void intentoPrestarLibro(Long usuarioId, Long libroId) {
        try {
            prestamoService.prestarLibro(usuarioId, libroId);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Cuando("presto el libro con usuario id {long} y libro id {long}")
    public void prestarLibroExito(Long usuarioId, Long libroId) {
        prestamoCreado = prestamoService.prestarLibro(usuarioId, libroId);
    }

    @Cuando("calculo el recargo A para un préstamo nulo")
    public void calcularRecargoANulo() {
        resultadoCalculo = prestamoService.calcularRecargoA(null);
    }

    @Cuando("calculo el recargo A para un préstamo de hace {int} días")
    public void calcularRecargoAConDias(int dias) {
        Prestamo p = new Prestamo();
        p.setFechaPrestamo(LocalDate.now().minusDays(dias));
        resultadoCalculo = prestamoService.calcularRecargoA(p);
    }

    @Cuando("calculo el recargo B para un préstamo nulo")
    public void calcularRecargoBNulo() {
        resultadoCalculo = prestamoService.calcularRecargoB(null);
    }

    @Cuando("calculo el recargo B para un préstamo de hace {int} días")
    public void calcularRecargoBConDias(int dias) {
        Prestamo p = new Prestamo();
        p.setFechaPrestamo(LocalDate.now().minusDays(dias));
        resultadoCalculo = prestamoService.calcularRecargoB(p);
    }

    @Cuando("intento devolver el préstamo con id {long}")
    public void intentoDevolverLibro(Long id) {
        try {
            prestamoService.devolverLibro(id);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Cuando("devuelvo el préstamo con id {long}")
    public void devolverLibroExito(Long id) {
        prestamoService.devolverLibro(id);
    }

    @Cuando("intento devolver un préstamo antiguo con id {long}")
    public void intentoDevolverLibroAntiguo(Long id) {
        try {
            prestamoService.devolverLibroAntiguo(id);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Cuando("devuelvo el préstamo antiguo con id {long}")
    public void devolverLibroAntiguoExito(Long id) {
        prestamoService.devolverLibroAntiguo(id);
    }

    @Cuando("calculo la multa para un préstamo nulo")
    public void calcularMultaNulo() {
        resultadoCalculo = prestamoService.calcularMulta(null);
    }

    @Cuando("calculo la multa no devuelto de hace {int} días")
    public void calcularMultaNoDevuelto(int dias) {
        Prestamo p = new Prestamo();
        p.setDevuelto(false);
        p.setFechaPrestamo(LocalDate.now().minusDays(dias));
        resultadoCalculo = prestamoService.calcularMulta(p);
    }

    @Cuando("calculo la multa devuelto con diferencia de {int} días")
    public void calcularMultaDevuelto(int dias) {
        Prestamo p = new Prestamo();
        p.setDevuelto(true);
        LocalDate inicio = LocalDate.now().minusDays(dias);
        p.setFechaPrestamo(inicio);
        p.setFechaDevolucion(LocalDate.now());
        resultadoCalculo = prestamoService.calcularMulta(p);
    }

    @Cuando("solicito la lista de todos los préstamos")
    public void listarPrestamos() {
        listaPrestamos = prestamoService.listarPrestamos();
    }

    // --- VERIFICACIONES (ENTONCES) ---

    @Entonces("se lanza una excepción en prestamo con mensaje {string}")
    public void verificarExcepcion(String mensajeEsperado) {
        assertNotNull(excepcionCapturada, "Se esperaba una excepción pero no ocurrió.");
        assertEquals(mensajeEsperado, excepcionCapturada.getMessage());
        excepcionCapturada = null;
    }

    @Entonces("el préstamo creado no es nulo y el libro queda prestado")
    public void verificarPrestamoCreado() {
        assertNotNull(prestamoCreado);
        assertTrue(prestamoCreado.getLibro().isPrestado());
    }

    @Entonces("el recargo A devuelto es {double}")
    public void verificarRecargoA(double esperado) {
        assertEquals(esperado, resultadoCalculo, 0.01);
    }

    @Entonces("el recargo B devuelto es {double}")
    public void verificarRecargoB(double esperado) {
        assertEquals(esperado, resultadoCalculo, 0.01);
    }

    @Entonces("la devolución finaliza correctamente")
    public void verificarDevolucionExito() {
        assertNull(excepcionCapturada);
    }

    @Entonces("la multa devuelta es {double}")
    public void verificarMulta(double esperado) {
        assertEquals(esperado, resultadoCalculo, 0.01);
    }

    @Entonces("la lista de préstamos devuelta no es nula")
    public void verificarListaPrestamos() {
        assertNotNull(listaPrestamos);
    }
}