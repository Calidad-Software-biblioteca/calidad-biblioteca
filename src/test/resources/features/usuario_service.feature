# language: es
Característica: Pruebas unitarias para UsuarioService

  Escenario: Crear un usuario exitosamente
    Dado que el repositorio guardará un usuario con nombre "Walter" y email "walter@email.com"
    Cuando se solicita crear el usuario con nombre "Walter" y email "walter@email.com"
    Entonces el usuario devuelto no es nulo y su nombre es "Walter"

  Escenario: Intentar crear un usuario con nombre nulo o vacío
    Cuando se intenta crear un usuario con nombre "" y email "walter@email.com"
    Entonces se debe lanzar una excepción con el mensaje "El nombre es obligatorio"

  Escenario: Intentar crear un usuario con email nulo o vacío
    Cuando se intenta crear un usuario con nombre "Walter" y email ""
    Entonces se debe lanzar una excepción con el mensaje "El email es obligatorio"

  Escenario: Intentar crear un usuario con email inválido
    Cuando se intenta crear un usuario con nombre "Walter" y email "walteremail.com"
    Entonces se debe lanzar una excepción con el mensaje "Email inválido"

  Escenario: Listar todos los usuarios
    Dado que existen usuarios registrados en el repositorio
    Cuando se solicita listar todos los usuarios
    Entonces la lista devuelta debe contener los usuarios registrados

  Escenario: Buscar un usuario por ID existente
    Dado que existe un usuario con ID 1 en el repositorio
    Cuando se busca el usuario por ID 1
    Entonces el usuario encontrado debe tener el ID 1

  Escenario: Buscar un usuario por ID inexistente
    Dado que no existe un usuario con ID 99 en el repositorio
    Cuando se busca el usuario por ID 99
    Entonces el resultado debe ser nulo

  Escenario: Eliminar un usuario por ID
    Cuando se solicita eliminar el usuario con ID 1
    Entonces se debe invocar la eliminación en el repositorio para el ID 1