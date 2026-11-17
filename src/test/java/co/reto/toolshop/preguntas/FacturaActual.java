package co.reto.toolshop.preguntas;

import co.reto.toolshop.modelos.Memoria;
import co.reto.toolshop.ui.Facturas;
import co.reto.toolshop.utilidades.*;
import java.math.BigDecimal;
import net.serenitybdd.screenplay.*;

public final class FacturaActual {
    private FacturaActual() { }
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
