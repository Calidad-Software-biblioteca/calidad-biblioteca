# language: es
Característica: Cobertura de LibroService

  # --- CREAR LIBRO ---

  #--  Ejercicio 1 (Crear un libro exitosamente con crearLibro)
  # Escenario:
  #  Cuando
  #  Entonces

  #-- Ejercicio 2 (Mostrar un Error cuando el titulo está en blanco (isBlank())
  # Escenario:
  #  Cuando
  #  Entonces

  #-- Ejercicio 3 (Mostrar un Error cuando el titulo es nulo isNull())
  # Escenario:
  #  Cuando
  #  Entonces

  Escenario: Error en crearLibro cuando el autor es nulo
    Cuando intento crearLibro con título "El Aleph" y autor nulo
    Entonces se lanza una excepción con mensaje "El autor es obligatorio"

  Escenario: Error en crearLibro cuando el autor está en blanco
    Cuando intento crearLibro con título "El Aleph" y autor "   "
    Entonces se lanza una excepción con mensaje "El autor es obligatorio"

  # --- CONSULTAS Y ELIMINACIÓN ---
  Escenario: Listar todos los libros
    Dado que existe al menos un libro en la base de datos
    Cuando solicito la lista de todos los libros
    Entonces la lista devuelta no está vacía

  Escenario: Buscar libro por ID existente
    Dado que creo un libro para buscarlo posteriormente
    Cuando busco el libro por su ID generado
    Entonces el libro encontrado no es nulo

  Escenario: Buscar libro por ID inexistente
    Cuando busco un libro con ID 999999
    Entonces el resultado de la búsqueda es nulo

  Escenario: Eliminar libro por ID
    Dado que creo un libro para eliminarlo posteriormente
    Cuando elimino el libro por su ID generado
    Entonces al buscar ese ID el resultado es nulo