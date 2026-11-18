# Reto de automatización Toolshop

Pruebas web de [Practice Software Testing – Toolshop](https://practicesoftwaretesting.com) con Java 21, Maven, Selenium, Serenity BDD, Screenplay y Cucumber. La suite comprueba el cálculo del carrito, la compra y factura, el ordenamiento del catálogo, el filtro por categoría y el rechazo de credenciales inválidas.

## Material para entender y explicar el reto

| Material | Para qué sirve |
| --- | --- |
| [Guía HTML para principiantes](docs/guia-principiantes.html) | Explica automatización y Screenplay desde cero, recorre el código, permite practicar el cálculo del carrito y contiene una autoevaluación con respuestas explicadas. |
| [Presentación PowerPoint](docs/presentacion-toolshop.pptx) | Quince diapositivas editables con notas del expositor para una explicación de unos diez minutos. |
| [Guion de las diapositivas](docs/presentacion-toolshop.md) | Fuente de texto editable con el contenido, las notas y las referencias de cada diapositiva. |
| [Guía de sustentación](docs/sustentacion.md) | Orden de exposición, archivos que conviene mostrar, preguntas del evaluador y demostración sugerida. |
| [Validación registrada](docs/validacion.md) | Ambiente, fecha y resultados de las ejecuciones verificadas del proyecto. |

Para leer el HTML, descarga o clona el repositorio y abre `docs/guia-principiantes.html` en Chrome o Edge. El contenido, la calculadora y la autoevaluación funcionan sin servidor ni conexión; los enlaces a GitHub requieren Internet. GitHub puede mostrar el código HTML en lugar de su vista de lectura. El botón de impresión permite usar la opción del navegador para guardar una copia en PDF.

Los importes del material de estudio son ejemplos de una ejecución anterior. Los escenarios Java capturan los precios de la tienda en cada ejecución y calculan sus propios valores esperados.

## Requisitos previos

- JDK 21 y `JAVA_HOME` configurado.
- Maven 3.9 o superior y Google Chrome instalado.
- Git y acceso a Internet para descargar dependencias, el controlador de Chrome y consultar el sitio público.

Comprobar la instalación:

```powershell
java -version
mvn -version
git --version
```

Las versiones están fijadas en el POM: Serenity 4.2.34, Cucumber 7.22.2 y JUnit 4.13.2. Selenium 4.33.0 procede de Serenity; no se mezclan versiones de sus módulos. Selenium Manager administra el controlador del navegador. El ejecutor de Cucumber utiliza JUnit 4; las pruebas unitarias usan el mismo motor.

## Ejecución

Ejecutar los comandos desde la raíz del repositorio. Las propiedades Maven se escriben entre comillas para que PowerShell no divida los argumentos que contienen puntos.

```powershell
# Suite completa con Chrome visible y reporte nuevo
mvn clean verify

# Suite completa sin ventana de Chrome
mvn clean verify "-Dheadless.mode=true"

# Un escenario independiente
mvn clean verify "-Dcucumber.filter.tags=@factura" "-Dheadless.mode=true"

# Dos escenarios seleccionados
mvn clean verify "-Dcucumber.filter.tags=@carrito or @login_negativo"

# Solo pruebas unitarias; no abre el navegador
mvn test
```

Etiquetas disponibles: `@carrito`, `@factura`, `@ordenamiento`, `@categoria` y `@login_negativo`. La ejecución es secuencial. Cada escenario tiene su propio actor, navegador y datos; `@factura` prepara y valida su propio carrito, por lo que no necesita ejecutar antes `@carrito`.

Opciones adicionales:

```powershell
# Otra instalación del mismo Toolshop
mvn clean verify "-Dwebdriver.base.url=http://localhost:4200"

# Esperas explícitas de hasta 45 segundos
mvn clean verify "-Despera.segundos=45"
```

El código de salida de Maven será distinto de cero si falla una prueba. Failsafe ejecuta las pruebas web durante `integration-test`; Serenity genera el informe antes de que `verify` propague los fallos.

## Reporte y evidencias

Después de `mvn clean verify`, abrir `target/serenity-reports/index.html`. Incluye resultados por escenario, pasos de Screenplay, duración, errores y capturas en caso de fallo. Los informes técnicos adicionales quedan en `target/surefire-reports` y `target/failsafe-reports`.

Usar `clean` antes de una ejecución que se vaya a entregar: así el reporte no mezcla resultados anteriores con una selección actual por etiquetas. Los reportes se generan localmente y no se versionan.

## Cobertura

| Etiqueta | Comportamiento y validación |
| --- | --- |
| `@carrito` | Registro y login; tres productos distintos con cantidades 1, 2 y 3; comparación de nombres, precios, cantidades, subtotales y total. Cambio de la primera cantidad a 4 y nueva validación. |
| `@factura` | Usuario y carrito propios; validación antes y después del cambio; transferencia bancaria simulada; número y total de factura en listado y detalle contra el cálculo original; dirección facturada contra la enviada. |
| `@ordenamiento` | Precio ascendente en todas las páginas del catálogo presentado, incluyendo los límites entre páginas. Comparación numérica y aceptación de empates. |
| `@categoria` | Subcategoría elegida en ejecución; recorrido de todas sus páginas y consulta de la categoría real de cada producto. Un filtro sin resultados no cuenta como prueba satisfactoria. |
| `@login_negativo` | Correo UUID no registrado y contraseña válida para el formulario; mensaje exacto `Invalid email or password` y permanencia en login sin acceso a la cuenta. |

## Estructura y Screenplay

```text
src/main/java/com/co/toolshop/
  tasks/            Objetivos del cliente con clases y fábricas en español
  interactions/     Operaciones específicas sobre controles
  questions/        Clases que implementan Question<T> y leen el estado
  userinterfaces/   Pantallas Page y Targets TXT_, BTN_, LBL_, SELECT_ y LST_
  models/           DTOs inmutables de usuario, producto, carrito y dirección
  utils/            Memoria del actor, importes y esperas explícitas
    data/           Generador de usuarios sintéticos con UUID v4
src/test/java/com/co/toolshop/
  runners/          ToolshopRunnerIT integrado con Serenity y Cucumber
  stepdefinitions/  Pasos Gherkin y Hooks de preparación y cierre
  unit/             Pruebas de cálculos, formato, orden y unicidad
src/test/resources/
  features/         Escenarios Gherkin en español
  serenity.conf     Configuración de Chrome y reportes
docs/
  sustentacion.md   Guía de decisiones y demostración
  validacion.md     Resultados de ejecución
  guia-principiantes.html    Documento autónomo con prácticas interactivas
  presentacion-toolshop.pptx Presentación editable con notas del expositor
  presentacion-toolshop.md   Contenido y notas de las diapositivas
```

La organización sigue las convenciones de [prueba-tecnica-web](https://github.com/sallacas/prueba-tecnica-web), manteniendo las Tasks y sus fábricas en español. Los pasos delegan en Tasks/Interactions y consultan clases `Question<T>` para realizar las aserciones. La habilidad `BrowseTheWeb` pertenece al actor.

Ejemplos de composición:

```java
actor.attemptsTo(RegistrarUsuario.con(UserGenerator.newUser()));
actor.attemptsTo(IniciarSesion.enLaTiendaCon(actor.recall(ActorMemory.USER)));
actor.attemptsTo(AgregarProductos.alCarritoConCantidades(1, 2, 3));
actor.attemptsTo(ModificarCantidad.delPrimerProductoA(4));
actor.attemptsTo(CompletarCompra.porTransferenciaBancaria());
actor.attemptsTo(ConsultarFactura.porNumero(actor.recall(ActorMemory.INVOICE_NUMBER)));
BigDecimal total = actor.asksFor(CartTotal.value());
```

Las Tasks componen acciones nativas como `Open.url`, `Click.on`, `Enter.theValue`, `SelectFromOptions` y `WaitUntil`. Las Interactions propias cubren la paginación y la escritura que necesita Toolshop: `Write.theValue(...).into(...)` reemplaza la selección sin vaciar primero el campo y evita incluir contraseñas en la descripción del reporte; `ChangeQuantity.ofProduct(...).to(...)` solo opera el control. La Task `ModificarCantidad` actualiza los datos recordados y recalcula el importe.

Las pantallas se llaman `LoginPage`, `RegisterPage`, `CatalogPage`, `CartPage`, `CheckoutPage`, `InvoicesPage` y `NavigationPage`. Por ejemplo, `LoginPage.TXT_EMAIL` y `LoginPage.BTN_LOGIN`. Los DTOs mantienen la inmutabilidad de Java 21 mediante records y la lógica monetaria usa `BigDecimal`.

Las dependencias utilizadas por las capas reutilizables tienen alcance de compilación en Maven; Cucumber, el ejecutor y las aserciones de prueba permanecen en el alcance de pruebas.

## Datos y cálculos

- Cada escenario de compra registra un usuario nuevo con correo `toolshop.<UUID v4>@example.com`. La contraseña también se genera por ejecución y cumple las reglas del registro y el límite del login. La dirección, teléfono y datos bancarios son ficticios.
- Se utiliza la memoria del actor mediante `remember` / `recall` para usuario, productos, cantidades, total esperado, total de compra, factura y resultados del catálogo. No hay usuarios compartidos, archivos de credenciales ni estado mutable global.
- Los identificadores y precios de los productos se descubren en el catálogo actual. Los nombres y precios se capturan en el detalle antes de agregarlos. El catálogo es un dato de entrada del sistema; no se requieren productos con IDs o precios predefinidos.
- Toolshop aplica descuentos ecológicos y puede ofrecer promociones. Para aislar la fórmula exigida por el reto, los escenarios de compra eligen tres productos disponibles, sin promociones y con calificación CO₂ C, D o E. Si no existen suficientes, el escenario falla con una explicación. No se omiten validaciones ni se toma el total del sitio como resultado esperado.
- Todos los cálculos usan `BigDecimal` construido desde texto: suma de `precioUnitario × cantidad`. Las comparaciones monetarias son numéricas, de modo que `12.0` y `12.00` son equivalentes. El formato leído es USD con punto decimal, tal como lo presenta la aplicación en español.
- El ordenamiento y el filtro recorren todas las páginas que presenta el catálogo con su rango de precios inicial. Se detectan productos repetidos entre páginas. No se automatizan los otros filtros que el reto no exige.

## Estabilidad y ambiente público

Los Targets priorizan `data-test`; los IDs dinámicos se obtienen durante la ejecución. Se esperan condiciones observables: controles habilitados, respuesta de autocompletado postal, cambio de página y productos, actualización del total, validación del pago y confirmación de factura. No se usa `Thread.sleep`, manipulación del DOM con JavaScript ni preparación de datos mediante API.

El registro y la dirección de checkout esperan la respuesta del servicio postal. En facturación se conservan la ciudad y el estado autocompletados: el backend comprueba que correspondan al país y código postal. Se captura la dirección enviada y se compara con la factura. Un fallo de ese servicio, un cambio de contrato de la web o un reinicio de los datos públicos puede afectar la ejecución. Los fallos se conservan en el informe; no se aplican reintentos automáticos para convertirlos en aprobados.

Si el sitio público está indisponible, consultar las instrucciones Docker del [repositorio oficial](https://github.com/testsmith-io/practice-software-testing), levantar la variante estable `sprint5` y pasar su URL con `webdriver.base.url`. Esta entrega no incluye ni modifica la aplicación Toolshop. No se garantiza una versión con bugs intencionales.

## Versionamiento y entrega

El commit inicial se creó en `preparacion/base`. La implementación original se desarrolló en `funcionalidad/reto-automatizacion` y el ajuste de convenciones en `refactorizacion/convenciones-screenplay`; ambos se integraron mediante PR. Los materiales de aprendizaje se preparan en `documentacion/guia-y-presentacion`. Los commits, ramas y descripciones se mantienen en español; cada cambio se publica al terminar las verificaciones y se integra mediante commit de merge para conservar el historial.

No se incluye GitHub Actions. La [guía de sustentación](docs/sustentacion.md) explica el diseño y la demostración. Los reportes, credenciales y archivos temporales están excluidos por `.gitignore`.

La [validación de entrega](docs/validacion.md) registra la ejecución completa aprobada: seis pruebas unitarias y cinco escenarios web, sin fallos ni omisiones.

Referencias: [documentación oficial de Serenity](https://serenity-bdd.github.io/docs/guide/maven) y [código fuente de Toolshop](https://github.com/testsmith-io/practice-software-testing).
