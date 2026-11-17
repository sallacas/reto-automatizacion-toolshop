# language: es
Característica: Rechazo de credenciales inválidas
  Como visitante de la tienda
  Quiero recibir un error al usar credenciales inválidas
  Para impedir el acceso a una cuenta inexistente

  @login_negativo
  Escenario: Rechazar el ingreso de un usuario no registrado
    Dado que un visitante dispone de credenciales de un usuario no registrado
    Cuando intenta iniciar sesión con esas credenciales
    Entonces la tienda muestra el error de credenciales inválidas y mantiene el login
