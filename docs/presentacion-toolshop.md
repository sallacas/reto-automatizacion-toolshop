# Presentación del reto Toolshop

Guion editable de 15 diapositivas para explicar el ejercicio. El PowerPoint incluye estas notas del expositor.

## 1. Reto Toolshop

Cómo funciona la automatización de una compra

Una explicación para aprender el ejercicio y sustentarlo con ejemplos del código actual.

**Notas del expositor**

Presenta el objetivo en palabras de un cliente: comprar herramientas, cambiar una cantidad y comprobar que el carrito y la factura conservan los importes correctos. La tienda es una demo y el pago utiliza datos ficticios. Esta explicación sigue la estructura refactorizada, con Tasks en español y carpetas con las convenciones acordadas.
Fuente: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/README.md

## 2. La comprobación de una compra

**Resultado esperado**

El código captura precios y cantidades.
Calcula cada subtotal y los suma.

**Resultado observado**

La página muestra un total.
La factura conserva la información de compra.

La aserción compara lo observado con lo esperado y falla si no coincide.

**Notas del expositor**

Empieza con una compra manual para que el público comprenda la necesidad de comparar. La preparación es registrar la cuenta. La acción es comprar o cambiar una cantidad. El esperado se calcula con datos capturados, sin copiar el total del carrito. La aserción es la comparación. Que la página muestre una factura permite confirmar la navegación; la corrección de sus datos necesita comprobaciones adicionales.
Fuente: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/test/java/com/co/toolshop/stepdefinitions/CartStepDefinitions.java

## 3. Los cinco escenarios

| Escenario | Comprobación |
| --- | --- |
| Carrito | Líneas y total antes y después de cambiar una cantidad. |
| Compra y factura | Número, total en listado y detalle, y dirección enviada. |
| Ordenamiento | Precio ascendente en todas las páginas presentadas. |
| Categoría | Categoría real de cada producto filtrado, en su detalle. |
| Login negativo | Error de credenciales inválidas y permanencia en login. |

**Notas del expositor**

Son cinco escenarios web repartidos en tres archivos feature. Carrito y factura comparten Tasks y pasos, pero cada escenario prepara su propio usuario y carrito. La cobertura del catálogo comprende las páginas del rango de precios inicial. La prueba de categoría no acepta un resultado vacío como prueba de pertenencia.
Fuentes: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/test/resources/features/compra.feature
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/test/resources/features/catalogo.feature
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/test/resources/features/autenticacion.feature

## 4. Las herramientas y su función

| Herramienta | Responsabilidad |
| --- | --- |
| Java 21 | Escribe instrucciones, datos y cálculos. |
| Selenium | Opera Chrome y lee los controles de la página. |
| Serenity BDD | Integra Screenplay, ejecución y reporte. |
| Cucumber y Gherkin | Conectan ejemplos legibles con métodos Java. |
| JUnit y AssertJ | Participan en la ejecución y las comprobaciones. |
| Maven | Compila, ejecuta pruebas y genera el reporte. |
| Git y GitHub | Conservan cambios y permiten revisarlos mediante PR. |

**Notas del expositor**

Explica una función a la vez. Gherkin es la forma de escribir Dado, Cuando y Entonces; Cucumber hace la conexión con Java. Java permite organizar el programa en objetos. Screenplay es el patrón elegido para repartir las responsabilidades de automatización. BDD aporta el enfoque de describir comportamientos mediante ejemplos. JUnit es el motor utilizado por el ejecutor y las pruebas unitarias, y AssertJ expresa muchas de las aserciones del proyecto.
Fuente: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/pom.xml

## 5. Las piezas de Screenplay

| Pieza | En el reto |
| --- | --- |
| Actor | Cliente de Toolshop. |
| Ability | BrowseTheWeb le permite usar el navegador. |
| Task | ModificarCantidad coordina el objetivo del cliente. |
| Interaction | ChangeQuantity escribe en el control. |
| Question | CartTotal devuelve el total observado. |
| Target | CartPage.LBL_TOTAL localiza el dato en la página. |
| Memoria | remember y recall conservan datos del escenario. |

**Notas del expositor**

Usa la analogía de una persona que compra con una lista y una libreta. El actor tiene la capacidad de usar el navegador, realiza objetivos y consulta información. La memoria pertenece al escenario y evita compartir cuentas o carritos entre escenarios. Las aserciones comparan el resultado de las Questions con lo esperado.
Fuentes: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/test/java/com/co/toolshop/stepdefinitions/Hooks.java
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/tasks/ModificarCantidad.java
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/questions/CartTotal.java

## 6. Modificar una cantidad

1. **Task:** Prepara el nuevo ProductDTO y coordina la modificación.
2. **Interaction:** Escribe la cantidad del producto en su campo.
3. **Memoria y cálculo:** La Task conserva los nuevos datos y recalcula el total esperado.
4. **Question y aserción:** El paso lee el total de la web y compara ambos importes.

**Notas del expositor**

Muestra ModificarCantidad.delPrimerProductoA(4). Primero crea una nueva versión del producto para mantener la inmutabilidad. Ejecuta ChangeQuantity.ofProduct(nombre).to(4), que opera el control. Después actualiza PRODUCTS y EXPECTED_TOTAL en la memoria del actor, y espera un cambio observable del total. El paso Entonces verifica las líneas y el total.
Fuentes: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/tasks/ModificarCantidad.java
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/interactions/ChangeQuantity.java

## 7. El recorrido de un paso

1. **Gherkin:** Cuando cambia la cantidad del primer producto a 4.
2. **StepDefinition:** El método con @Cuando recibe el número mediante {int}.
3. **Task e Interaction:** El actor ejecuta el objetivo y opera la interfaz con Selenium.
4. **Lectura, aserción y reporte:** Una Question lee. La aserción compara. Serenity registra el resultado.

**Notas del expositor**

Haz el recorrido de archivos en este orden: compra.feature, CartStepDefinitions, ModificarCantidad, ChangeQuantity, CartTotal y CartPage. Vuelve luego a la aserción de CartStepDefinitions. No atribuyas a la Question la comparación que hace el paso: la Question devuelve un dato.
Fuente: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/test/java/com/co/toolshop/stepdefinitions/CartStepDefinitions.java

## 8. Las llamadas de las Tasks

```
actor.attemptsTo(RegistrarUsuario.con(usuario));
actor.attemptsTo(IniciarSesion.enLaTiendaCon(usuario));
actor.attemptsTo(AgregarProductos.alCarritoConCantidades(1, 2, 3));
actor.attemptsTo(ModificarCantidad.delPrimerProductoA(4));
```

La fábrica construye la Task con sus datos.
El actor ejecuta su método performAs.
Las Tasks y sus fábricas se mantienen en español.

**Notas del expositor**

El fragmento ilustra las llamadas dentro de un escenario preparado. En el código real se genera el usuario con UserGenerator.newUser, se registra y luego se recupera del actor para iniciar sesión. Un método como con es una fábrica estática. Tasks.instrumented crea una instancia compatible con la instrumentación de Serenity para registrar pasos. Los detalles de ejecución permanecen en performAs.
Fuentes: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/tasks/RegistrarUsuario.java
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/test/java/com/co/toolshop/stepdefinitions/AuthenticationStepDefinitions.java

## 9. El cálculo del carrito

| Producto | USD/unidad | Cantidad final | Subtotal USD |
| --- | --- | --- | --- |
| Combination Pliers | 14,15 | 4 | 56,60 |
| Pliers | 12,01 | 2 | 24,02 |
| Bolt Cutters | 48,41 | 3 | 145,23 |
| Total final |  |  | 225,85 |

Con cantidades 1, 2 y 3: USD 183,40. Al cambiar la primera a 4: USD 225,85.

Ejemplo de una ejecución anterior. Cada prueba captura los precios actuales.

**Notas del expositor**

Multiplica cada precio por su cantidad y suma. La diferencia por pasar de una a cuatro unidades del primer producto es 42,45 USD, que equivale a tres unidades adicionales de 14,15. El proyecto usa BigDecimal construido desde texto y compara importes por valor, por lo que 12.0 y 12.00 son equivalentes. También comprueba nombres, precios, cantidades y subtotales. Estos importes son evidencia histórica utilizada como ejemplo, no constantes de los escenarios.
Fuentes: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/docs/validacion.md
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/utils/Amounts.java

## 10. Datos y esperas de la prueba

1. **UUID:** Cada compra registra un correo nuevo y recuerda su cuenta.
2. **Productos actuales:** Captura IDs y precios. Elige disponibilidad y ausencia de descuentos.
3. **Esperas explícitas:** Espera cambios observables, con un tiempo máximo.
4. **Dirección y pago:** Conserva los datos postales validados y usa transferencia ficticia.

**Notas del expositor**

El correo tiene la forma toolshop.<UUID v4>@example.com. Un UUID hace extremadamente improbable una colisión, pero no se presenta como garantía matemática absoluta. La compra elige productos sin promociones y con CO2 C, D o E para aislar la fórmula precio por cantidad. Las esperas verifican una condición, como la llegada de productos de otra página o la actualización del total. No se utiliza Thread.sleep. El backend valida ciudad y estado con el servicio postal.
Fuentes: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/utils/data/UserGenerator.java
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/tasks/AgregarProductos.java
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/tasks/CompletarCompra.java

## 11. La organización del repositorio

**Código reutilizable**

src/main/java/com/co/toolshop

tasks e interactions
questions y userinterfaces
models y utils

**Ejecución de pruebas**

src/test/java/com/co/toolshop

runners y stepdefinitions
Hooks y unit

Features en src/test/resources

Las pantallas usan Page y los Targets prefijos como TXT_, BTN_ y LBL_.

**Notas del expositor**

Relaciona cada carpeta con una responsabilidad. Models contiene los DTOs como ProductDTO y BillingAddressDTO. Son records inmutables. Utils contiene importes, esperas y claves de memoria. StepDefinitions conecta el texto con Java; los Hooks preparan y cierran la actuación. Se mantuvo Maven y se ajustó el alcance de las dependencias para compilar las capas reutilizables.
Fuente: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/README.md

## 12. La evidencia registrada

Última verificación del código refactorizado
17 de noviembre de 2026, 19:20:05, hora de Colombia

5 escenarios web y 6 pruebas unitarias aprobados. Cero fallos, errores u omisiones.

**Notas del expositor**

La ejecución registrada utilizó mvn -B -ntp clean verify con headless.mode=true. Pasaron los cinco escenarios web y las seis pruebas unitarias sin fallos, errores ni omisiones. Es un resultado histórico del ambiente público y no prueba que la aplicación completa esté libre de defectos. La actualización de documentos y diapositivas no constituye una nueva ejecución de la suite. Abre Serenity para consultar pasos, datos de cálculo y la captura del detalle de factura.
Fuente: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/docs/validacion.md

## 13. Una demostración del ejercicio

```
mvn test

mvn clean verify "-Dcucumber.filter.tags=@factura"

mvn clean verify "-Dheadless.mode=true"
```

1. Pruebas unitarias sin navegador.
2. Factura independiente con Chrome visible.
3. Suite completa sin mostrar la ventana de Chrome.

**Notas del expositor**

Antes de ejecutar, comprueba JDK 21, Maven 3.9 o superior, Chrome e Internet. Abre la terminal en la carpeta de pom.xml. Explica que clean elimina resultados anteriores de target, por lo que debe guardarse un reporte que se quiera conservar. El informe queda en target/serenity-reports/index.html. Si usas un reporte ya guardado, di su fecha y ambiente. No atribuyas los resultados de otro momento a una ejecución actual.
Fuente: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/README.md

## 14. Una ruta de aprendizaje

1. **Comprensión funcional:** Explica la compra y calcula el ejemplo a mano.
2. **Lectura de código:** Sigue Gherkin, StepDefinition, Task, Interaction y Question.
3. **Práctica:** Ejecuta un escenario y revisa su reporte. Cambia una cantidad en una rama.
4. **Explicación propia:** Cuenta dónde sale el esperado y qué demuestra la aserción.

**Notas del expositor**

Si no conoces Java, añade primero clases, objetos, métodos, parámetros, listas y records a tu estudio. La guía HTML amplía esta ruta en siete sesiones y añade una calculadora y una autoevaluación. Para practicar, cambia en una rama la cantidad final de cuatro a cinco, calcula a mano con los precios observados y explica por qué el esperado se recalcula. Mantén el reporte y el historial de tus cambios.
Fuentes: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/docs/guia-principiantes.html
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/src/main/java/com/co/toolshop/models/ProductDTO.java

## 15. La sustentación en diez minutos

1. **Objetivo y cobertura:** Un minuto para presentar la compra y los otros escenarios.
2. **Patrón y código:** Cuatro minutos para repartir responsabilidades y seguir un paso.
3. **Cálculo y demostración:** Cuatro minutos para explicar el esperado y mostrar evidencia.
4. **Límites:** Un minuto para delimitar la cobertura y las dependencias del ambiente.

**Notas del expositor**

Responde con precisión: la factura prepara su carrito para ser independiente; BigDecimal maneja los importes decimales; Question lee y la aserción compara; el orden y la categoría incluyen las páginas presentadas; el catálogo y el servicio postal son dependencias externas. La cobertura no incluye análisis del PDF, todos los pagos o descuentos. Si el evaluador pide versionamiento, muestra las ramas, los commits en español y los PR integrados. La meta es explicar decisiones con ejemplos reales del código, no memorizar nombres de herramientas.
Fuentes: https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/docs/sustentacion.md
https://github.com/sallacas/reto-automatizacion-toolshop/blob/main/docs/validacion.md
