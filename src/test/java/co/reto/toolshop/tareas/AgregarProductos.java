package co.reto.toolshop.tareas;

import co.reto.toolshop.interacciones.*;
import co.reto.toolshop.modelos.*;
import co.reto.toolshop.ui.Catalogo;
import co.reto.toolshop.utilidades.*;
import java.util.*;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.WebElement;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AgregarProductos implements Task {
    public static AgregarProductos distintos() { return instrumented(AgregarProductos.class); }
    @Step("{0} captura y agrega tres productos sin descuentos con cantidades 1, 2 y 3")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AbrirTienda.en("/"));
        List<String> enlaces = new ArrayList<>();
        do {
            Esperas.visible(actor, Catalogo.PRODUCTOS);
            for (WebElement tarjeta : Esperas.todos(actor, Catalogo.PRODUCTOS)) {
                boolean agotado = !tarjeta.findElements(Esperas.por(Catalogo.AGOTADO)).isEmpty();
                boolean promocion = !tarjeta.findElements(Esperas.por(Catalogo.PROMOCION)).isEmpty();
                var calificaciones = tarjeta.findElements(Esperas.por(Catalogo.CO2_ACTIVO));
                boolean sinDescuentoEcologico = !calificaciones.isEmpty()
                        && Set.of("C", "D", "E").contains(calificaciones.getFirst().getText().trim());
                if (!agotado && !promocion && sinDescuentoEcologico) {
                    enlaces.add(tarjeta.getAttribute("href"));
                    if (enlaces.size() == 3) break;
                }
            }
            if (enlaces.size() == 3 || !AvanzarPagina.disponible(actor)) break;
            actor.attemptsTo(AvanzarPagina.siguiente());
        } while (true);
        if (enlaces.size() < 3) throw new IllegalStateException("No hay tres productos disponibles sin promociones y con calificación C, D o E");

        List<ProductoCapturado> productos = new ArrayList<>();
        int cantidad = 1;
        for (String enlace : enlaces) {
            Esperas.navegador(actor).get(enlace);
            String nombre = Esperas.visible(actor, Catalogo.NOMBRE_DETALLE).getText().trim();
            var precio = Importes.desdeTexto(Esperas.visible(actor, Catalogo.PRECIO_DETALLE).getText());
            String identificador = enlace.substring(enlace.lastIndexOf('/') + 1);
            String contadorAnterior = Esperas.presente(actor, Catalogo.CONTADOR_CARRITO)
                    ? Esperas.visible(actor, Catalogo.CONTADOR_CARRITO).getText() : "";
            actor.attemptsTo(Escribir.en(Catalogo.CANTIDAD, Integer.toString(cantidad)), Pulsar.sobre(Catalogo.AGREGAR));
            Esperas.hasta(actor, "El contador debe confirmar que el producto se agregó", d ->
                    Esperas.presente(actor, Catalogo.CONTADOR_CARRITO)
                            && !d.findElement(Esperas.por(Catalogo.CONTADOR_CARRITO)).getText().equals(contadorAnterior));
            productos.add(new ProductoCapturado(identificador, nombre, precio, cantidad++));
        }
        actor.remember(Memoria.PRODUCTOS, List.copyOf(productos));
        actor.remember(Memoria.TOTAL_ESPERADO, Importes.total(productos));
        actor.attemptsTo(AbrirTienda.en("/checkout"));
        Esperas.visible(actor, co.reto.toolshop.ui.Carrito.TOTAL);
    }
}
