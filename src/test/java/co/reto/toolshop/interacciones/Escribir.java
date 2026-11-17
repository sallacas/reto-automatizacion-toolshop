package co.reto.toolshop.interacciones;

import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Escribir implements Interaction {
    private final Target campo;
    private final String contenido;
    public Escribir(Target campo, String contenido) { this.campo = campo; this.contenido = contenido; }
    public static Escribir en(Target campo, String contenido) { return instrumented(Escribir.class, campo, contenido); }
    @Step("{0} completa #campo")
    public <T extends Actor> void performAs(T actor) {
        WebElement elemento = Esperas.clicable(actor, campo);
        elemento.sendKeys(Keys.chord(Keys.CONTROL, "a"), contenido, Keys.TAB);
    }
}
