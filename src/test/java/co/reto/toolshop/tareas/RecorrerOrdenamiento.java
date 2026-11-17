package co.reto.toolshop.tareas;

import co.reto.toolshop.interacciones.AvanzarPagina;
import co.reto.toolshop.modelos.Memoria;
import co.reto.toolshop.preguntas.PreciosCatalogo;
import co.reto.toolshop.ui.Catalogo;
import co.reto.toolshop.utilidades.Esperas;
import java.math.BigDecimal;
import java.util.*;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.support.ui.Select;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RecorrerOrdenamiento implements Task {
    public static RecorrerOrdenamiento ascendente() { return instrumented(RecorrerOrdenamiento.class); }
    @Step("{0} ordena por precio ascendente y captura todas las páginas")
    public <T extends Actor> void performAs(T actor) {
        Esperas.visible(actor, Catalogo.PRODUCTOS);
        new Select(Esperas.clicable(actor, Catalogo.ORDEN)).selectByValue("price,asc");
        Esperas.visible(actor, Catalogo.ORDEN_COMPLETADO);
        List<BigDecimal> precios = new ArrayList<>();
        Set<String> enlacesVistos = new HashSet<>();
        do {
            for (var tarjeta : Esperas.todos(actor, Catalogo.PRODUCTOS)) {
                if (!enlacesVistos.add(tarjeta.getAttribute("href"))) {
                    throw new IllegalStateException("Producto repetido al recorrer el catálogo: " + tarjeta.getText());
                }
            }
            precios.addAll(actor.asksFor(PreciosCatalogo.deLaPagina()));
            if (!AvanzarPagina.disponible(actor)) break;
            actor.attemptsTo(AvanzarPagina.siguiente());
        } while (true);
        actor.remember(Memoria.PRECIOS_CATALOGO, List.copyOf(precios));
    }
}
