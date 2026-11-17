package co.reto.toolshop.interacciones;

import co.reto.toolshop.ui.Catalogo;
import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AvanzarPagina implements Interaction {
    public static AvanzarPagina siguiente() { return instrumented(AvanzarPagina.class); }
    public static boolean disponible(Actor actor) {
        return Esperas.presente(actor, Catalogo.SIGUIENTE_HABILITADA);
    }
    @Step("{0} recorre la siguiente página del catálogo")
    public <T extends Actor> void performAs(T actor) {
        String anterior = Esperas.visible(actor, Catalogo.PAGINA_ACTUAL).getText();
        String primerEnlaceAnterior = Esperas.visible(actor, Catalogo.PRODUCTOS).getAttribute("href");
        actor.attemptsTo(Pulsar.sobre(Catalogo.SIGUIENTE));
        Esperas.hasta(actor, "Debe cambiar la página activa", d ->
                !d.findElement(Esperas.por(Catalogo.PAGINA_ACTUAL)).getText().equals(anterior));
        Esperas.visible(actor, Catalogo.PRODUCTOS);
        Esperas.hasta(actor, "Deben llegar los productos de la nueva página", d -> {
            var productos = Esperas.todos(actor, Catalogo.PRODUCTOS);
            return !productos.isEmpty() && !productos.getFirst().getAttribute("href").equals(primerEnlaceAnterior);
        });
    }
}
