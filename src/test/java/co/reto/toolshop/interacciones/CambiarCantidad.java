package co.reto.toolshop.interacciones;

import co.reto.toolshop.modelos.*;
import co.reto.toolshop.ui.Carrito;
import co.reto.toolshop.utilidades.*;
import java.util.*;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.Keys;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CambiarCantidad implements Interaction {
    private final int cantidad;
    public CambiarCantidad(int cantidad) { this.cantidad = cantidad; }
    public static CambiarCantidad delPrimeroA(int cantidad) { return instrumented(CambiarCantidad.class, cantidad); }
    @Step("{0} cambia la cantidad del primer producto a #cantidad")
    public <T extends Actor> void performAs(T actor) {
        List<ProductoCapturado> productos = new ArrayList<>(actor.<List<ProductoCapturado>>recall(Memoria.PRODUCTOS));
        ProductoCapturado primero = productos.getFirst();
        var fila = Esperas.todos(actor, Carrito.FILAS).stream()
                .filter(e -> Esperas.dentro(e, Carrito.NOMBRE).getText().trim().equals(primero.nombre()))
                .findFirst().orElseThrow(() -> new IllegalStateException("No se encontró el producto: " + primero.nombre()));
        String totalAnterior = Esperas.visible(actor, Carrito.TOTAL).getText();
        Esperas.dentro(fila, Carrito.CANTIDAD).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE,
                Integer.toString(cantidad), Keys.TAB);
        productos.set(0, primero.conCantidad(cantidad));
        actor.remember(Memoria.PRODUCTOS, List.copyOf(productos));
        actor.remember(Memoria.TOTAL_ESPERADO, Importes.total(productos));
        Esperas.hasta(actor, "Debe actualizarse el total tras cambiar la cantidad", d ->
                !d.findElement(Esperas.por(Carrito.TOTAL)).getText().equals(totalAnterior));
    }
}
