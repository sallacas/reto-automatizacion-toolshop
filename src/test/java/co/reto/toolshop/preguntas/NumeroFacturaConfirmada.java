package co.reto.toolshop.preguntas;

import co.reto.toolshop.ui.Pago;
import co.reto.toolshop.utilidades.Esperas;
import java.util.regex.Pattern;
import net.serenitybdd.screenplay.*;

public class NumeroFacturaConfirmada implements Question<String> {
    public static NumeroFacturaConfirmada enLaCompra() { return new NumeroFacturaConfirmada(); }
    public String answeredBy(Actor actor) {
        String confirmacion = Esperas.visible(actor, Pago.CONFIRMACION).getText();
        // Angular sanitiza el HTML traducido y elimina el id del span interno.
        // El número se extrae del texto del contenedor estable de confirmación.
        var coincidencia = Pattern.compile("\\bINV-\\d+\\b").matcher(confirmacion);
        if (!coincidencia.find()) throw new IllegalStateException("Confirmación sin número de factura: " + confirmacion);
        return coincidencia.group();
    }
}
