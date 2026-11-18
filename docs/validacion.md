# Resultado de la validación

La suite completa se ejecutó el 17 de noviembre de 2026 contra `https://practicesoftwaretesting.com`, utilizando Chrome visible y datos generados durante la ejecución. Maven terminó con `BUILD SUCCESS` a las 18:29:53, hora de Colombia.

Comando utilizado:

```powershell
mvn -B -ntp clean verify
```

| Grupo | Ejecutadas | Fallos | Errores | Omitidas |
| --- | ---: | ---: | ---: | ---: |
| Pruebas unitarias | 6 | 0 | 0 | 0 |
| Escenarios web | 5 | 0 | 0 | 0 |

Los cinco escenarios aprobados fueron login negativo, ordenamiento de todas las páginas, categoría real de todos los resultados filtrados, recálculo de carrito y compra con consulta de factura. La prueba de factura también se ejecutó de forma independiente en modo headless.

## Evidencia de la compra

La ejecución completa generó la factura `INV-2026000010`. Después del cambio de cantidad, los datos capturados produjeron este cálculo:

| Producto | Precio unitario USD | Cantidad | Subtotal USD |
| --- | ---: | ---: | ---: |
| Combination Pliers | 14,15 | 4 | 56,60 |
| Pliers | 12,01 | 2 | 24,02 |
| Bolt Cutters | 48,41 | 3 | 145,23 |
| Total | | | 225,85 |

El total calculado coincidió con el carrito, el listado de facturas y el detalle. La dirección del detalle coincidió con la enviada durante el checkout. Estos valores documentan una ejecución; no se utilizan como constantes de los escenarios y pueden cambiar cuando cambie el catálogo.

## Ambiente y reporte

- Windows 11, Java Corretto 21.0.9 y Maven 3.9.11.
- Chrome 157.0.8103.79 y Selenium 4.33.0 administrado por Serenity 4.2.34.
- Duración total de Maven: 1 minuto y 14 segundos; ejecución web: 57,73 segundos.
- Reporte generado en `target/serenity-reports/index.html`, con datos de cálculo y captura del detalle de factura.

El reporte completo y los archivos técnicos permanecen fuera de Git. Cada evaluador puede regenerarlos mediante el comando indicado. El sitio público es compartido: una ejecución posterior usa otros usuarios UUID, otra factura y el catálogo disponible en ese momento.

## Validación del ajuste de convenciones Screenplay

El 17 de noviembre de 2026 se verificó la estructura basada en `sallacas/prueba-tecnica-web`, con Tasks y fábricas en español. Las capas reutilizables están en `src/main/java/com/co/toolshop`; los pasos, Hooks, ejecutor y pruebas unitarias están en `src/test/java/com/co/toolshop`.

```powershell
mvn -B -ntp clean verify "-Dheadless.mode=true"
```

Maven terminó con `BUILD SUCCESS` a las 19:20:05, hora de Colombia. Se ejecutaron las seis pruebas unitarias y los cinco escenarios web: cero fallos, errores u omisiones. La duración total fue de 1 minuto y 14 segundos. El reporte Serenity se regeneró con los resultados de esta ejecución.

También se comprobó la compilación final con `mvn -B -ntp test-compile "-DskipTests"`. La adaptación mantiene usuarios UUID, cálculos con BigDecimal, memoria del actor y escenarios independientes. Se verificó el flujo completo de compra y la dirección e importe de la factura después del cambio de estructura y composición.
