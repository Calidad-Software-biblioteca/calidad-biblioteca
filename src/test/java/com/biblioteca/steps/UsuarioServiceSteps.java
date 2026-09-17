package com.biblioteca.steps;

import com.biblioteca.CucumberSpringConfiguration;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.UsuarioRepository;
import com.biblioteca.service.UsuarioService;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioServiceSteps extends CucumberSpringConfiguration {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioResultado;
    private List<Usuario> listaResultado;
    private Exception excepcionCapturada;

    public UsuarioServiceSteps() {
        MockitoAnnotations.openMocks(this);
    }

    // --- CREAR USUARIO ---

    @Dado("que el repositorio guardará un usuario con nombre {string} y email {string}")
    public void mockGuardarUsuario(String nombre, String email) {
        Usuario usuarioGuardado = new Usuario(nombre, email);
        usuarioGuardado.setId(1L);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);
    }

    @Cuando("se solicita crear el usuario con nombre {string} y email {string}")
    public void crearUsuarioExito(String nombre, String email) {
        usuarioResultado = usuarioService.crearUsuario(nombre, email);
    }

    @Entonces("el usuario devuelto no es nulo y su nombre es {string}")
    public void verificarUsuarioCreado(String nombreEsperado) {
        assertNotNull(usuarioResultado);
        assertEquals(nombreEsperado, usuarioResultado.getNombre());
    }

    // --- EXCEPCIONES AL CREAR ---

    @Cuando("se intenta crear un usuario con nombre {string} y email {string}")
    public void crearUsuarioConException(String nombre, String email) {
        try {
            // Manejar la conversión manual para pasar nulls en los escenarios de prueba
            String nombreFinal = nombre.equals("null") ? null : nombre;
            String emailFinal = email.equals("null") ? null : email;
            usuarioService.crearUsuario(nombreFinal, emailFinal);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("se debe lanzar una excepción con el mensaje {string}")
    public void verificarMensajeExcepcion(String mensajeEsperado) {
        assertNotNull(excepcionCapturada);
        assertEquals(mensajeEsperado, excepcionCapturada.getMessage());
    }

    // --- LISTAR USUARIOS ---

    @Dado("que existen usuarios registrados en el repositorio")
    public void mockListarUsuarios() {
        Usuario u1 = new Usuario("Walter", "walter@email.com");
        Usuario u2 = new Usuario("Ariel", "ariel@email.com");
        when(usuarioRepository.findAll()).thenReturn(List.of(u1, u2));
    }

    @Cuando("se solicita listar todos los usuarios")
    public void listarUsuarios() {
        listaResultado = usuarioService.listarUsuarios();
    }

    @Entonces("la lista devuelta debe contener los usuarios registrados")
    public void verificarListaUsuarios() {
        assertNotNull(listaResultado);
        assertEquals(2, listaResultado.size());
    }

    // --- BUSCAR USUARIO ---

    @Dado("que existe un usuario con ID {long} en el repositorio")
    public void mockBuscarUsuarioExistente(Long id) {
        Usuario u = new Usuario("Walter", "walter@email.com");
        u.setId(id);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(u));
    }

    @Dado("que no existe un usuario con ID {long} en el repositorio")
    public void mockBuscarUsuarioInexistente(Long id) {
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());
    }

    @Cuando("se busca el usuario por ID {long}")
    public void buscarUsuarioPorId(Long id) {
        usuarioResultado = usuarioService.buscarUsuario(id);
    }

    @Entonces("el usuario encontrado debe tener el ID {long}")
    public void verificarUsuarioEncontrado(Long idEsperado) {
        assertNotNull(usuarioResultado);
        assertEquals(idEsperado, usuarioResultado.getId());
    }

    @Entonces("el resultado debe ser nulo")
    public void verificarUsuarioNulo() {
        assertNull(usuarioResultado);
    }

    // --- ELIMINAR USUARIO ---

    @Cuando("se solicita eliminar el usuario con ID {long}")
    public void eliminarUsuario(Long id) {
        usuarioService.eliminarUsuario(id);
    }

    @Entonces("se debe invocar la eliminación en el repositorio para el ID {long}")
    public void verificarEliminacion(Long id) {
        verify(usuarioRepository, times(1)).deleteById(id);
    }
}