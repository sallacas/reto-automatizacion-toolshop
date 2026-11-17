package co.reto.toolshop.interacciones;

import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.support.ui.Select;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SeleccionarOpcion implements Interaction {
    private final Target lista;
    private final String valor;
    public SeleccionarOpcion(Target lista, String valor) { this.lista = lista; this.valor = valor; }
    public static SeleccionarOpcion en(Target lista, String valor) { return instrumented(SeleccionarOpcion.class, lista, valor); }
    @Step("{0} selecciona #valor en #lista")
    public <T extends Actor> void performAs(T actor) {
        new Select(Esperas.clicable(actor, lista)).selectByValue(valor);
    }
}
