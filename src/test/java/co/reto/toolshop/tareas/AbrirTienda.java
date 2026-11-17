package co.reto.toolshop.tareas;

import co.reto.toolshop.ui.Acceso;
import co.reto.toolshop.interacciones.Pulsar;
import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AbrirTienda implements Task {
    private final String ruta;
    public AbrirTienda(String ruta) { this.ruta = ruta; }
    public static AbrirTienda en(String ruta) { return instrumented(AbrirTienda.class, ruta); }
    @Step("{0} abre la tienda en #ruta")
    public <T extends Actor> void performAs(T actor) {
        String base = System.getProperty("webdriver.base.url", "https://practicesoftwaretesting.com").replaceAll("/+$", "");
        Esperas.navegador(actor).get(base + ruta);
        String idioma = Esperas.visible(actor, Acceso.IDIOMA).getText().trim();
        if (!"ES".equals(idioma)) {
            actor.attemptsTo(Pulsar.sobre(Acceso.IDIOMA), Pulsar.sobre(Acceso.ESPANOL));
            Esperas.hasta(actor, "La tienda debe estar en español", d -> "ES".equals(d.findElement(Esperas.por(Acceso.IDIOMA)).getText().trim()));
        }
    }
}
