package com.biblioteca.service;

import com.biblioteca.model.Prestamo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrestamoServiceTest {

    @Test
    void calcularMultaConPrestamoNullDebeDarCero() {

        PrestamoService service = null;

        // Test deliberadamente simple para la versión inicial.
        // Los alumnos deberán detectar que la cobertura es insuficiente.

        assertNull(service);
    }
}