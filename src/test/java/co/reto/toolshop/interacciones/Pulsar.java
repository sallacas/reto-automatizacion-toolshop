package co.reto.toolshop.interacciones;

import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import net.serenitybdd.screenplay.targets.Target;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Pulsar implements Interaction {
    private final Target objetivo;
    public Pulsar(Target objetivo) { this.objetivo = objetivo; }
    public static Pulsar sobre(Target objetivo) { return instrumented(Pulsar.class, objetivo); }
    @Step("{0} pulsa #objetivo")
    public <T extends Actor> void performAs(T actor) { Esperas.clicable(actor, objetivo).click(); }
}
