# language: es
Característica: Carrito y facturación de Toolshop
  Como cliente registrado
  Quiero comprobar los importes de mis herramientas
  Para recibir una factura coherente con mi compra

  Antecedentes:
    Dado que un cliente nuevo se registra e inicia sesión

  @carrito
  Escenario: Recalcular el carrito al modificar una cantidad
    Cuando agrega tres productos disponibles con cantidades distintas
    Entonces el carrito coincide con los precios y cantidades capturados
    Cuando cambia la cantidad del primer producto a 4
    Entonces el carrito coincide con los precios y cantidades capturados

  @factura
  Escenario: Comprar y consultar la factura del carrito calculado
    Cuando agrega tres productos disponibles con cantidades distintas
    Entonces el carrito coincide con los precios y cantidades capturados
    Cuando cambia la cantidad del primer producto a 4
    Entonces el carrito coincide con los precios y cantidades capturados
    Cuando completa la compra con transferencia bancaria simulada
    Entonces la factura guardada pertenece a la compra y conserva su total
