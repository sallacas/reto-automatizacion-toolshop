# Guía de sustentación del reto Toolshop

El proyecto demuestra que los importes de una compra se mantienen coherentes desde la captura del precio hasta la consulta posterior de la factura. Las comprobaciones de catálogo y acceso cubren los otros comportamientos solicitados. La suite utiliza Selenium a través de Serenity y no crea datos mediante API.

## Explicación del diseño

Comenzar por una característica Gherkin, seguir su paso Java y mostrar la Task que ejecuta el objetivo del cliente. Después abrir una Interaction, una Question y los Targets de su pantalla. Esto permite explicar la separación sin recorrer todas las clases.

- El actor representa al cliente y tiene la habilidad `BrowseTheWeb`.
- Las Tasks expresan objetivos: registrar un usuario, agregar herramientas, comprar y consultar una factura.
- Las Interactions operan controles concretos y aplican esperas explícitas.
- Las Questions leen el estado del sistema. Las aserciones comparan ese estado con los datos esperados.
- Los Targets concentran los localizadores para que un cambio de interfaz tenga un lugar de mantenimiento claro.

Mostrar `Memoria`: los datos se comparten entre pasos con `remember` y `recall`, dentro de un único escenario. Los modelos son inmutables; al cambiar una cantidad se genera una nueva versión del producto. No hay variables estáticas mutables con información de usuarios o compras.

## Decisiones que conviene defender

**UUID para el correo.** Cada registro recibe un UUID v4 completo. No depende de un contador local, una fecha o una cuenta previa. Se conserva el correo en el actor para iniciar sesión durante ese escenario. Las contraseñas generadas tienen mayúsculas, minúsculas, números y símbolo, y respetan el máximo de 40 caracteres del login.

**Escenarios independientes.** La prueba de factura prepara su propio carrito y verifica el cálculo antes y después del cambio de cantidad. Puede ejecutarse sola, sin depender del resultado u orden de otra prueba. Se reutilizan las mismas Tasks y pasos para evitar duplicar la lógica de preparación.

**Resultado esperado independiente.** El total esperado se calcula a partir de precios capturados antes de agregar los productos y sus cantidades. El total del carrito se utiliza como valor observado. La factura se compara contra el cálculo original, no solamente contra otro valor de la web.

**BigDecimal.** Se construye desde texto para evitar errores binarios de `double`. Las comparaciones usan el valor numérico y no exigen la misma escala. Las pruebas unitarias cubren decimales como 0.10, importes con miles, actualización de cantidad, empates y orden numérico.

**Selección de productos.** Los IDs y precios cambian en el sitio público. Se descubren durante la ejecución y se eligen productos disponibles sin promociones y con calificación CO₂ C, D o E. Esto evita el descuento ecológico automático y permite comprobar exactamente la fórmula solicitada. La cobertura de promociones y descuentos requeriría escenarios y reglas de cálculo propios.

**Paginación.** El orden se comprueba sobre una sola lista numérica que reúne todas las páginas. Por ello también se valida el último precio de una página frente al primero de la siguiente. Para la categoría se recorren los resultados y se consulta el detalle real de cada producto; comprobar solamente que el checkbox está marcado sería insuficiente.

**Esperas.** Mostrar la espera de cambio de página y la actualización de cantidad. Esperar solo que un control exista permitiría leer datos anteriores. El código espera una transición verificable y falla al vencer el tiempo. No hay pausas fijas ni reintentos automáticos de escenarios fallidos.

**Pago ficticio.** La transferencia utiliza banco, titular y cuenta sintéticos dentro de la aplicación demo. Se valida primero el pago y luego se confirma la compra. No se usan datos financieros reales.

**Dirección coherente.** El backend contrasta ciudad y estado con el servicio postal. Por eso el checkout conserva esos valores autocompletados y captura la dirección enviada en la memoria del actor. El detalle de factura debe conservar esa misma dirección.

## Demostración sugerida

1. Ejecutar `mvn test` para demostrar las verificaciones de cálculos y datos sin navegador.
2. Ejecutar `mvn clean verify "-Dcucumber.filter.tags=@factura"` y observar registro, carrito, cambio de cantidad, compra y factura.
3. Abrir `target/serenity-reports/index.html`, revisar los pasos y el resultado. Una ejecución seleccionada requiere `clean` para que no aparezcan escenarios de ejecuciones anteriores.
4. Ejecutar la suite completa con `mvn clean verify "-Dheadless.mode=true"`.
5. Mostrar la rama de desarrollo, los commits en español y el PR integrado mediante merge, conservando su historial.

## Límites y diagnóstico

La aplicación pública se comparte con otras personas y puede reiniciar su catálogo. También depende de un servicio de autocompletado de direcciones. Si una ejecución falla, revisar primero la captura, el paso y el mensaje de Serenity. Distinguir una discrepancia funcional de un cambio de localizador o una indisponibilidad del ambiente, y documentar lo observado.

La suite no analiza el PDF de factura, no cubre todos los métodos de pago y no valida promociones o descuentos. No incluye CI. Estas decisiones mantienen el alcance acordado y permiten explicar cada validación implementada.
