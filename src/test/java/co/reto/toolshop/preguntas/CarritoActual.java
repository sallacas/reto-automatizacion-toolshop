package co.reto.toolshop.preguntas;

import co.reto.toolshop.modelos.LineaCarrito;
import co.reto.toolshop.ui.Carrito;
import co.reto.toolshop.utilidades.*;
import java.math.BigDecimal;
import java.util.List;
import net.serenitybdd.screenplay.*;

public final class CarritoActual {
    private CarritoActual() { }
    public static Question<List<LineaCarrito>> lineas() {
        return actor -> {
            Esperas.visible(actor, Carrito.TOTAL);
            return Esperas.todos(actor, Carrito.FILAS).stream().map(fila -> new LineaCarrito(
                    Esperas.dentro(fila, Carrito.NOMBRE).getText().trim(),
                    Importes.desdeTexto(Esperas.dentro(fila, Carrito.PRECIO).getText()),
                    Integer.parseInt(Esperas.dentro(fila, Carrito.CANTIDAD).getAttribute("value")),
                    Importes.desdeTexto(Esperas.dentro(fila, Carrito.SUBTOTAL).getText()))).toList();
        };
    }
    public static Question<BigDecimal> total() {
        return actor -> Importes.desdeTexto(Esperas.visible(actor, Carrito.TOTAL).getText());
    }
}
