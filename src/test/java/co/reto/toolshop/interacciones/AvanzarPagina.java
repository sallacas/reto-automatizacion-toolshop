package co.reto.toolshop.interacciones;

import co.reto.toolshop.ui.Catalogo;
import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.By;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AvanzarPagina implements Interaction {
    public static AvanzarPagina siguiente() { return instrumented(AvanzarPagina.class); }
    public static boolean disponible(Actor actor) {
        var botones = Esperas.todos(actor, Catalogo.SIGUIENTE);
        return !botones.isEmpty() && !botones.getFirst().findElement(By.xpath("..")).getAttribute("class").contains("disabled");
    }
    @Step("{0} recorre la siguiente página del catálogo")
    public <T extends Actor> void performAs(T actor) {
        String anterior = Esperas.visible(actor, Catalogo.PAGINA_ACTUAL).getText();
        actor.attemptsTo(Pulsar.sobre(Catalogo.SIGUIENTE));
        Esperas.hasta(actor, "Debe cambiar la página activa", d ->
                !d.findElement(Esperas.por(Catalogo.PAGINA_ACTUAL)).getText().equals(anterior));
        Esperas.visible(actor, Catalogo.PRODUCTOS);
    }
}
