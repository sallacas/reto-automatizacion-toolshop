package com.co.toolshop.tasks;

import net.serenitybdd.screenplay.actions.*;
import com.co.toolshop.interactions.GoToNextPage;
import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.Waits;
import java.util.*;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RevisarCategoria implements Task {
    public static RevisarCategoria deLosProductosFiltrados() { return instrumented(RevisarCategoria.class); }
    @Step("{0} filtra una subcategoría con resultados y consulta cada producto de todas sus páginas")
    public <T extends Actor> void performAs(T actor) {
        Waits.visible(actor, CatalogPage.LST_SUBCATEGORIES);
        var candidatas = Waits.all(actor, CatalogPage.LST_SUBCATEGORIES).stream()
                .map(e -> Map.entry(e.getAttribute("data-test"), e.findElement(CatalogPage.BY_PARENT_LABEL).getText().trim())).toList();
        String seleccionada = null;
        for (var candidata : candidatas) {
            var objetivo = Elements.test("subcategoría " + candidata.getValue(), candidata.getKey());
            actor.attemptsTo(Click.on(objetivo).afterWaitingUntilEnabled());
            Waits.visible(actor, CatalogPage.LBL_FILTER_COMPLETED);
            if (!Waits.all(actor, CatalogPage.LST_PRODUCTS).isEmpty()) {
                seleccionada = candidata.getValue();
                break;
            }
            actor.attemptsTo(AbrirTienda.en("/"));
            Waits.visible(actor, CatalogPage.LST_SUBCATEGORIES);
        }
        if (seleccionada == null) throw new IllegalStateException("No se encontró una subcategoría con productos");
        actor.remember(ActorMemory.CATEGORY, seleccionada);
        List<String> enlaces = new ArrayList<>();
        do {
            Waits.visible(actor, CatalogPage.LST_PRODUCTS);
            enlaces.addAll(Waits.all(actor, CatalogPage.LST_PRODUCTS).stream().map(e -> e.getAttribute("href")).toList());
            if (!GoToNextPage.isAvailable(actor)) break;
            actor.attemptsTo(GoToNextPage.inTheCatalog());
        } while (true);
        if (new HashSet<>(enlaces).size() != enlaces.size()) throw new IllegalStateException("El filtro devolvió productos repetidos entre páginas");
        Map<String, String> categorias = new LinkedHashMap<>();
        for (String enlace : enlaces) {
            actor.attemptsTo(Open.url(enlace));
            String nombre = Waits.visible(actor, CatalogPage.LBL_DETAIL_NAME).getText().trim();
            String categoria = Waits.visible(actor, CatalogPage.LBL_DETAIL_CATEGORY).getText().trim();
            categorias.put(nombre + " (" + enlace + ")", categoria);
        }
        actor.remember(ActorMemory.OBSERVED_CATEGORIES, Map.copyOf(categorias));
    }
}
