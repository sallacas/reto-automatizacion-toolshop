package com.co.toolshop.interactions;

import com.co.toolshop.utils.Waits;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Write implements Interaction {
    private final Target campo;
    private final String contenido;
    public Write(Target campo, String contenido) { this.campo = campo; this.contenido = contenido; }
    public static Value theValue(String contenido) { return new Value(contenido); }
    public record Value(String contenido) {
        public Write into(Target campo) { return instrumented(Write.class, campo, contenido); }
    }
    // Reemplaza la selección sin vaciar primero: Toolshop repone el mínimo
    // al vaciar un control numérico. La contraseña no se expone en el reporte.
    @Step("{0} completa #campo")
    public <T extends Actor> void performAs(T actor) {
        WebElement elemento = Waits.clickable(actor, campo);
        elemento.sendKeys(Keys.chord(Keys.CONTROL, "a"), contenido, Keys.TAB);
    }
}
