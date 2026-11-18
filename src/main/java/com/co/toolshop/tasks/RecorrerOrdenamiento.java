package com.co.toolshop.tasks;

import net.serenitybdd.screenplay.actions.*;
import com.co.toolshop.interactions.GoToNextPage;
import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.questions.CatalogPrices;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.Waits;
import java.math.BigDecimal;
import java.util.*;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RecorrerOrdenamiento implements Task {
    public static RecorrerOrdenamiento porPrecioAscendente() { return instrumented(RecorrerOrdenamiento.class); }
    @Step("{0} ordena por precio ascendente y captura todas las páginas")
    public <T extends Actor> void performAs(T actor) {
        Waits.visible(actor, CatalogPage.LST_PRODUCTS);
        actor.attemptsTo(SelectFromOptions.byValue("price,asc").from(CatalogPage.SELECT_SORT));
        Waits.visible(actor, CatalogPage.LBL_SORT_COMPLETED);
        List<BigDecimal> precios = new ArrayList<>();
        Set<String> enlacesVistos = new HashSet<>();
        do {
            for (var tarjeta : Waits.all(actor, CatalogPage.LST_PRODUCTS)) {
                if (!enlacesVistos.add(tarjeta.getAttribute("href"))) {
                    throw new IllegalStateException("Producto repetido al recorrer el catálogo: " + tarjeta.getText());
                }
            }
            precios.addAll(actor.asksFor(CatalogPrices.onCurrentPage()));
            if (!GoToNextPage.isAvailable(actor)) break;
            actor.attemptsTo(GoToNextPage.inTheCatalog());
        } while (true);
        actor.remember(ActorMemory.CATALOG_PRICES, List.copyOf(precios));
    }
}
