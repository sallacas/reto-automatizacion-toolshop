# Guía de sustentación del reto Toolshop

El proyecto demuestra que los importes de una compra se mantienen coherentes desde la captura del precio hasta la consulta posterior de la factura. Las comprobaciones de catálogo y acceso cubren los otros comportamientos solicitados. La suite utiliza Selenium a través de Serenity y no crea datos mediante API.

## Material de apoyo y preparación

- Para empezar desde cero, abrir [la guía HTML](guia-principiantes.html): explica los conceptos, permite practicar con un carrito didáctico y ofrece una autoevaluación de seis preguntas.
- Para exponer, utilizar [la presentación de quince diapositivas](presentacion-toolshop.pptx). Sus notas incluyen el desarrollo de cada explicación y las fuentes del código.
- Para adaptar el discurso, editar [el guion de las diapositivas](presentacion-toolshop.md) y mantenerlo coherente con el PowerPoint y el código.

El objetivo de la exposición es explicar qué se comprueba, de dónde sale el resultado esperado, cómo se reparten las responsabilidades y qué evidencia respalda el resultado. Las Tasks y sus fábricas se mantienen en español; las carpetas y las pantallas siguen las convenciones de la referencia.

Antes de la demostración, comprobar JDK 21, Maven, Chrome y conectividad. Abrir la terminal en la carpeta de `pom.xml` y dejar preparados los archivos del recorrido. Guardar el reporte si se quiere conservar: `mvn clean` elimina los resultados anteriores de `target`.

## Orden de exposición

| Tiempo sugerido | Diapositivas | Contenido |
| --- | --- | --- |
| 0:00 a 1:00 | 1 a 3 | Presentar la compra y la diferencia entre resultado esperado y observado. Resumir los cinco escenarios. |
| 1:00 a 3:00 | 4 a 6 | Ubicar las herramientas y explicar Actor, Ability, Task, Interaction, Question, Target y memoria. |
| 3:00 a 5:00 | 7 y 8 | Seguir una frase Gherkin hasta Java y explicar la fábrica de una Task y su método `performAs`. |
| 5:00 a 7:00 | 9 a 11 | Calcular el carrito, explicar BigDecimal, UUID, esperas y estructura del repositorio. |
| 7:00 a 9:00 | 12 y 13 | Mostrar evidencia registrada o una ejecución, indicando siempre su fecha y ambiente. |
| 9:00 a 10:00 | 14 y 15 | Explicar cómo practicar el ejercicio y delimitar su cobertura. |

Los tiempos son una propuesta de ensayo. Una ejecución completa puede requerir más tiempo según el ambiente; conviene reservarlo o utilizar un reporte guardado e identificarlo como evidencia anterior.

## Explicación del diseño

Comenzar por una característica Gherkin, seguir su paso Java y mostrar la Task que ejecuta el objetivo del cliente. Después abrir una Interaction, una Question y los Targets de su pantalla. Esto permite explicar la separación sin recorrer todas las clases.

- El actor representa al cliente y tiene la habilidad `BrowseTheWeb`.
- Las Tasks expresan objetivos: registrar un usuario, agregar herramientas, comprar y consultar una factura.
- Las Interactions operan controles concretos y aplican esperas explícitas.
- Las Questions leen el estado del sistema. Las aserciones comparan ese estado con los datos esperados.
- Los Targets concentran los localizadores para que un cambio de interfaz tenga un lugar de mantenimiento claro.

Mostrar `ActorMemory`: los datos se comparten entre pasos con `remember` y `recall`, dentro de un único escenario. Los modelos son inmutables; al cambiar una cantidad se genera una nueva versión del producto. No hay variables estáticas mutables con información de usuarios o compras.

## Convenciones de estructura y llamadas

La estructura sigue el repositorio de referencia `sallacas/prueba-tecnica-web`: capas reutilizables en `src/main/java/com/co/toolshop` y ejecutor, definiciones de pasos, Hooks y pruebas unitarias en `src/test/java/com/co/toolshop`.

Las carpetas se llaman `tasks`, `interactions`, `questions`, `userinterfaces`, `models` y `utils`. Las Tasks conservan clases y fábricas en español: `RegistrarUsuario.con(usuario)`, `IniciarSesion.enLaTiendaCon(usuario)`, `AgregarProductos.alCarritoConCantidades(1, 2, 3)` y `ConsultarFactura.porNumero(numero)`. Así las llamadas expresan el objetivo y los datos de entrada.

Mostrar `ModificarCantidad.delPrimerProductoA(4)`: la Task prepara el nuevo `ProductDTO` y recalcula el total en la memoria del actor; `ChangeQuantity.ofProduct(nombre).to(4)` realiza la escritura del control. Mostrar luego `CartTotal.value()`, una clase que implementa `Question<BigDecimal>`.

El recorrido concreto de archivos es:

1. `src/test/resources/features/compra.feature`: localizar la frase que cambia una cantidad.
2. `src/test/java/com/co/toolshop/stepdefinitions/CartStepDefinitions.java`: mostrar cómo `{int}` recibe el valor y el paso delega en la Task.
3. `src/main/java/com/co/toolshop/tasks/ModificarCantidad.java`: explicar el nuevo DTO, la memoria y el cálculo esperado.
4. `src/main/java/com/co/toolshop/interactions/ChangeQuantity.java`: mostrar la operación sobre el control.
5. `src/main/java/com/co/toolshop/questions/CartTotal.java` y `userinterfaces/CartPage.java`: mostrar la lectura y su Target.
6. Volver a `CartStepDefinitions.verificarCarrito`: señalar las aserciones de líneas y total.

Una Question devuelve el dato observado. La aserción compara ese dato con lo esperado. La Task contiene el objetivo y coordina las acciones. `Tasks.instrumented` construye una instancia compatible con la instrumentación de Serenity, mientras que `performAs` define su ejecución.

Los localizadores están en pantallas con sufijo `Page` y prefijos por tipo de control, por ejemplo `LoginPage.TXT_EMAIL`, `LoginPage.BTN_LOGIN` y `CartPage.LBL_TOTAL`. El código combina acciones nativas de Serenity con Interactions específicas para las particularidades de Toolshop. Los DTOs siguen siendo inmutables.

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

## Preguntas para ensayar

| Pregunta | Respuesta que conviene respaldar con código |
| --- | --- |
| ¿De dónde sale el total esperado? | De multiplicar precios capturados por cantidades y sumar en `Amounts`, usando BigDecimal. El total de la web es el observado. |
| ¿Por qué `@factura` repite preparación? | Para ejecutarse por sí solo, con su usuario y carrito, reutilizando las mismas Tasks. |
| ¿Por qué UUID? | Para generar correos con una probabilidad de repetición extremadamente baja, sin depender de cuentas existentes. |
| ¿Qué comprueba el filtro? | La categoría real del detalle de todos los productos resultantes, incluyendo las páginas presentadas. |
| ¿Qué diferencia hay entre Task e Interaction? | `ModificarCantidad` coordina datos y cálculo; `ChangeQuantity` escribe en el campo. |
| ¿Qué significa que las pruebas pasaron? | Que las comprobaciones cubiertas coincidieron en esa ejecución y ambiente. La evidencia no demuestra ausencia de todos los defectos posibles. |

## Cómo estudiar sin memorizar

Empezar por la compra manual y hacer la suma a mano. Después estudiar clases, objetos, métodos, parámetros, listas y records de Java. Seguir un solo paso entre archivos antes de recorrer toda la suite. Ejecutar primero las pruebas unitarias y después un escenario web. Por último, modificar una cantidad en una rama de práctica, explicar el efecto esperado y comprobarlo en el reporte.

La última ejecución completa registrada del código refactorizado corresponde al 17 de noviembre de 2026 a las 19:20:05, hora de Colombia. Los materiales de aprendizaje emplean esa evidencia e identifican los cálculos ilustrativos. La actualización documental no representa una ejecución nueva de las pruebas web.
