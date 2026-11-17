# language: es
Característica: Ordenamiento y categorías del catálogo
  Como visitante de la tienda
  Quiero ordenar y filtrar las herramientas
  Para encontrar productos acordes a mi búsqueda

  @ordenamiento
  Escenario: Ordenar todos los productos por precio ascendente
    Dado que un visitante abre el catálogo
    Cuando ordena por precio ascendente y recorre todas las páginas
    Entonces los precios están ordenados de menor a mayor entre todas las páginas

  @categoria
  Escenario: Comprobar la categoría real de los productos filtrados
    Dado que un visitante abre el catálogo
    Cuando filtra una subcategoría disponible y revisa todos sus productos
    Entonces todos los productos pertenecen a la categoría seleccionada
