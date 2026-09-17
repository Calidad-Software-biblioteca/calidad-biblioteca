# language: es
Característica: Cobertura PrestamoService

  # --- PRESTAR LIBRO (VALIDACIONES Y ÉXITO) ---
  Escenario: Error al prestar libro con usuario inexistente
    Cuando intento prestar un libro con usuario id 999 y libro id 1
    Entonces se lanza una excepción en prestamo con mensaje "Usuario inexistente"

  Escenario: Error al prestar libro con libro inexistente
    Dado que existe un usuario activo de id 10
    Cuando intento prestar un libro con usuario id 10 y libro id 999
    Entonces se lanza una excepción en prestamo con mensaje "Libro inexistente"

  Escenario: Error al prestar libro con usuario inactivo
    Dado que existe un usuario inactivo de id 11
    Dado que existe un libro disponible de id 20
    Cuando intento prestar un libro con usuario id 11 y libro id 20
    Entonces se lanza una excepción en prestamo con mensaje "El usuario está inactivo"

  Escenario: Error al prestar libro con usuario moroso
    Dado que existe un usuario moroso de id 12
    Dado que existe un libro disponible de id 21
    Cuando intento prestar un libro con usuario id 12 y libro id 21
    Entonces se lanza una excepción en prestamo con mensaje "El usuario tiene una deuda"

  Escenario: Error al prestar un libro que ya está prestado
    Dado que existe un usuario activo de id 13
    Dado que existe un libro ya prestado de id 22
    Cuando intento prestar un libro con usuario id 13 y libro id 22
    Entonces se lanza una excepción en prestamo con mensaje "El libro ya está prestado"

  Escenario: Error al prestar libro a usuario con máximo de préstamos
    Dado que existe un usuario activo con tres prestamos de id 14
    Dado que existe un libro disponible de id 23
    Cuando intento prestar un libro con usuario id 14 y libro id 23
    Entonces se lanza una excepción en prestamo con mensaje "El usuario alcanzó el máximo de préstamos"

  Escenario: Prestar libro exitosamente
    Dado que existe un usuario activo de id 15
    Dado que existe un libro disponible de id 24
    Cuando presto el libro con usuario id 15 y libro id 24
    Entonces el préstamo creado no es nulo y el libro queda prestado

  # --- CALCULAR RECARGO A ---
  Escenario: Calcular recargo A para préstamo nulo
    Cuando calculo el recargo A para un préstamo nulo
    Entonces el recargo A devuelto es 0.0

  Escenario: Calcular recargo A para tramo 0 a 7 días
    Cuando calculo el recargo A para un préstamo de hace 5 días
    Entonces el recargo A devuelto es 0.0

  Escenario: Calcular recargo A para tramo 8 a 14 días
    Cuando calculo el recargo A para un préstamo de hace 10 días
    Entonces el recargo A devuelto es 300.0

  Escenario: Calcular recargo A para tramo 15 a 21 días
    Cuando calculo el recargo A para un préstamo de hace 20 días
    Entonces el recargo A devuelto es 1950.0

  Escenario: Calcular recargo A para tramo 22 a 30 días
    Cuando calculo el recargo A para un préstamo de hace 25 días
    Entonces el recargo A devuelto es 3600.0

  Escenario: Calcular recargo A para tramo 31 a 45 días
    Cuando calculo el recargo A para un préstamo de hace 40 días
    Entonces el recargo A devuelto es 9900.0

  Escenario: Calcular recargo A para tramo 46 a 60 días
    Cuando calculo el recargo A para un préstamo de hace 50 días
    Entonces el recargo A devuelto es 17200.0

  Escenario: Calcular recargo A para tramo 61 a 90 días
    Cuando calculo el recargo A para un préstamo de hace 70 días
    Entonces el recargo A devuelto es 31500.0

  Escenario: Calcular recargo A para tramo mayor a 90 días
    Cuando calculo el recargo A para un préstamo de hace 100 días
    Entonces el recargo A devuelto es 69750.0

  # --- CALCULAR RECARGO B ---
  Escenario: Calcular recargo B para préstamo nulo
    Cuando calculo el recargo B para un préstamo nulo
    Entonces el recargo B devuelto es 0.0

  Escenario: Calcular recargo B para tramo 0 a 7 días
    Cuando calculo el recargo B para un préstamo de hace 5 días
    Entonces el recargo B devuelto es 0.0

  Escenario: Calcular recargo B para tramo 8 a 14 días
    Cuando calculo el recargo B para un préstamo de hace 10 días
    Entonces el recargo B devuelto es 300.0

  Escenario: Calcular recargo B para tramo 15 a 21 días
    Cuando calculo el recargo B para un préstamo de hace 20 días
    Entonces el recargo B devuelto es 1950.0

  Escenario: Calcular recargo B para tramo 22 a 30 días
    Cuando calculo el recargo B para un préstamo de hace 25 días
    Entonces el recargo B devuelto es 3600.0

  Escenario: Calcular recargo B para tramo 31 a 45 días
    Cuando calculo el recargo B para un préstamo de hace 40 días
    Entonces el recargo B devuelto es 9900.0

  Escenario: Calcular recargo B para tramo 46 a 60 días
    Cuando calculo el recargo B para un préstamo de hace 50 días
    Entonces el recargo B devuelto es 17200.0

  Escenario: Calcular recargo B para tramo 61 a 90 días
    Cuando calculo el recargo B para un préstamo de hace 70 días
    Entonces el recargo B devuelto es 31500.0

  Escenario: Calcular recargo B para tramo mayor a 90 días
    Cuando calculo el recargo B para un préstamo de hace 100 días
    Entonces el recargo B devuelto es 69750.0

  # --- DEVOLVER LIBRO Y DEVOLVER LIBRO ANTIGUO ---
  Escenario: Error al devolver préstamo inexistente
    Cuando intento devolver el préstamo con id 9999
    Entonces se lanza una excepción en prestamo con mensaje "Préstamo inexistente"

  Escenario: Error al devolver préstamo ya devuelto
    Dado que existe un préstamo devuelto de id 50
    Cuando intento devolver el préstamo con id 50
    Entonces se lanza una excepción en prestamo con mensaje "El préstamo ya fue devuelto"

  Escenario: Devolver préstamo exitosamente
    Dado que existe un préstamo activo de id 51
    Cuando devuelvo el préstamo con id 51
    Entonces la devolución finaliza correctamente

  Escenario: Error al devolver préstamo antiguo inexistente
    Cuando intento devolver un préstamo antiguo con id 9999
    Entonces se lanza una excepción en prestamo con mensaje "Préstamo inexistente"

  Escenario: Error al devolver préstamo antiguo ya devuelto
    Dado que existe un préstamo devuelto de id 52
    Cuando intento devolver un préstamo antiguo con id 52
    Entonces se lanza una excepción en prestamo con mensaje "El préstamo ya fue devuelto"

  Escenario: Devolver préstamo antiguo exitosamente
    Dado que existe un préstamo activo de id 53
    Cuando devuelvo el préstamo antiguo con id 53
    Entonces la devolución finaliza correctamente

  # --- CALCULAR MULTA (NO DEVUELTO Y DEVUELTO) ---
  Escenario: Calcular multa para préstamo nulo
    Cuando calculo la multa para un préstamo nulo
    Entonces la multa devuelta es 0.0

  # No devuelto (isDevuelto() = false)
  Escenario: Calcular multa no devuelto tramo 0 a 7 días
    Cuando calculo la multa no devuelto de hace 5 días
    Entonces la multa devuelta es 0.0

  Escenario: Calcular multa no devuelto tramo 8 a 14 días
    Cuando calculo la multa no devuelto de hace 10 días
    Entonces la multa devuelta es 300.0

  Escenario: Calcular multa no devuelto tramo 15 a 30 días
    Cuando calculo la multa no devuelto de hace 20 días
    Entonces la multa devuelta es 2600.0

  Escenario: Calcular multa no devuelto mayor a 30 días
    Cuando calculo la multa no devuelto de hace 40 días
    Entonces la multa devuelta es 16500.0

  # Devuelto (isDevuelto() = true)
  Escenario: Calcular multa devuelto tramo 0 a 7 días
    Cuando calculo la multa devuelto con diferencia de 5 días
    Entonces la multa devuelta es 0.0

  Escenario: Calcular multa devuelto tramo 8 a 14 días
    Cuando calculo la multa devuelto con diferencia de 10 días
    Entonces la multa devuelta es 300.0

  Escenario: Calcular multa devuelto tramo 15 a 30 días
    Cuando calculo la multa devuelto con diferencia de 20 días
    Entonces la multa devuelta es 2600.0

  Escenario: Calcular multa devuelto mayor a 30 días
    Cuando calculo la multa devuelto con diferencia de 40 días
    Entonces la multa devuelta es 16500.0

  # --- LISTAR PRÉSTAMOS ---
  Escenario: Listar todos los préstamos
    Cuando solicito la lista de todos los préstamos
    Entonces la lista de préstamos devuelta no es nula