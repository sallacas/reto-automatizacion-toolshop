package com.co.toolshop.interactions;

import net.serenitybdd.screenplay.actions.*;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.Waits;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GoToNextPage implements Interaction {
    public static GoToNextPage inTheCatalog() { return instrumented(GoToNextPage.class); }
    public static boolean isAvailable(Actor actor) {
        return Waits.present(actor, CatalogPage.BTN_NEXT_PAGE_ENABLED);
    }
    @Step("{0} recorre la siguiente página del catálogo")
    public <T extends Actor> void performAs(T actor) {
        String anterior = Waits.visible(actor, CatalogPage.LBL_ACTIVE_PAGE).getText();
        String primerEnlaceAnterior = Waits.visible(actor, CatalogPage.LST_PRODUCTS).getAttribute("href");
        actor.attemptsTo(Click.on(CatalogPage.BTN_NEXT_PAGE).afterWaitingUntilEnabled());
        Waits.until(actor, "Debe cambiar la página activa", d ->
                !d.findElement(Waits.by(CatalogPage.LBL_ACTIVE_PAGE)).getText().equals(anterior));
        Waits.visible(actor, CatalogPage.LST_PRODUCTS);
        Waits.until(actor, "Deben llegar los productos de la nueva página", d -> {
            var productos = Waits.all(actor, CatalogPage.LST_PRODUCTS);
            return !productos.isEmpty() && !productos.getFirst().getAttribute("href").equals(primerEnlaceAnterior);
        });
    }
}
