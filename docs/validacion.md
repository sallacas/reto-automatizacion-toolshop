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
