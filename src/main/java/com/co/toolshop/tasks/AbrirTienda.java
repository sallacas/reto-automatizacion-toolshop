package com.co.toolshop.tasks;

import net.serenitybdd.screenplay.actions.*;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.Waits;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AbrirTienda implements Task {
    private final String ruta;
    public AbrirTienda(String ruta) { this.ruta = ruta; }
    public static AbrirTienda en(String ruta) { return instrumented(AbrirTienda.class, ruta); }
    @Step("{0} abre la tienda en #ruta")
    public <T extends Actor> void performAs(T actor) {
        String base = System.getProperty("webdriver.base.url", "https://practicesoftwaretesting.com").replaceAll("/+$", "");
        actor.attemptsTo(Open.url(base + ruta),
                WaitUntil.the(NavigationPage.BTN_LANGUAGE, isVisible())
                        .forNoMoreThan(Long.getLong("espera.segundos", 30L)).seconds());
        String idioma = NavigationPage.BTN_LANGUAGE.resolveFor(actor).getText().trim();
        if (!"ES".equals(idioma)) {
            actor.attemptsTo(Click.on(NavigationPage.BTN_LANGUAGE).afterWaitingUntilEnabled(), Click.on(NavigationPage.LNK_SPANISH).afterWaitingUntilEnabled());
            Waits.until(actor, "La tienda debe estar en español", d -> "ES".equals(d.findElement(Waits.by(NavigationPage.BTN_LANGUAGE)).getText().trim()));
        }
    }
}
