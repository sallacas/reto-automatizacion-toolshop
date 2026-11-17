package co.reto.toolshop.preguntas;

import co.reto.toolshop.modelos.Memoria;
import co.reto.toolshop.modelos.DireccionFacturacion;
import co.reto.toolshop.ui.Facturas;
import co.reto.toolshop.utilidades.*;
import java.math.BigDecimal;
import net.serenitybdd.screenplay.*;

public final class FacturaActual {
    private FacturaActual() { }
    public static Question<String> numeroEnDetalle() {
        return actor -> Esperas.visible(actor, Facturas.NUMERO).getAttribute("value");
    }
    public static Question<BigDecimal> totalEnDetalle() {
        return actor -> Importes.desdeTexto(Esperas.visible(actor, Facturas.TOTAL).getAttribute("value"));
    }
    public static Question<DireccionFacturacion> direccionEnDetalle() {
        return actor -> new DireccionFacturacion(Esperas.visible(actor, Facturas.PAIS).getAttribute("value"),
                Esperas.visible(actor, Facturas.CODIGO_POSTAL).getAttribute("value"),
                Esperas.visible(actor, Facturas.CALLE).getAttribute("value"),
                Esperas.visible(actor, Facturas.CIUDAD).getAttribute("value"),
                Esperas.visible(actor, Facturas.ESTADO).getAttribute("value"));
    }
    public static Question<BigDecimal> totalEnListado() {
        return actor -> {
            String numero = actor.recall(Memoria.FACTURA);
            var fila = Esperas.todos(actor, Facturas.FILAS).stream().filter(e ->
                    e.findElements(Esperas.por(Facturas.CELDAS)).getFirst().getText().trim().equals(numero))
                    .findFirst().orElseThrow(() -> new IllegalStateException("Factura ausente: " + numero));
            return Importes.desdeTexto(fila.findElements(Esperas.por(Facturas.CELDAS)).get(3).getText());
        };
    }
}
