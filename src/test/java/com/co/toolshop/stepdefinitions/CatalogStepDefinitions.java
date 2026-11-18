package com.co.toolshop.stepdefinitions;

import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.tasks.*;
import io.cucumber.java.es.*;
import java.math.BigDecimal;
import java.util.*;
import net.serenitybdd.core.Serenity;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class CatalogStepDefinitions {
    @Dado("que un visitante abre el catálogo")
    public void abrirCatalogo() { theActorInTheSpotlight().attemptsTo(AbrirTienda.en("/")); }
    @Cuando("ordena por precio ascendente y recorre todas las páginas")
    public void ordenar() { theActorInTheSpotlight().attemptsTo(RecorrerOrdenamiento.porPrecioAscendente()); }
    @Entonces("los precios están ordenados de menor a mayor entre todas las páginas")
    public void comprobarOrden() {
        List<BigDecimal> precios = theActorInTheSpotlight().recall(ActorMemory.CATALOG_PRICES);
        assertThat(precios).as("Precios de todas las páginas del catálogo").isNotEmpty().isSortedAccordingTo(BigDecimal::compareTo);
        Serenity.recordReportData().withTitle("Precios de todas las páginas").andContents(precios.toString());
    }
    @Cuando("filtra una subcategoría disponible y revisa todos sus productos")
    public void filtrar() { theActorInTheSpotlight().attemptsTo(RevisarCategoria.deLosProductosFiltrados()); }
    @Entonces("todos los productos pertenecen a la categoría seleccionada")
    public void comprobarCategorias() {
        String categoria = theActorInTheSpotlight().recall(ActorMemory.CATEGORY);
        Map<String, String> categorias = theActorInTheSpotlight().recall(ActorMemory.OBSERVED_CATEGORIES);
        assertThat(categorias).as("Productos inspeccionados de %s", categoria).isNotEmpty();
        categorias.forEach((producto, observada) -> assertThat(observada).as("Categoría real de %s", producto).isEqualTo(categoria));
        Serenity.recordReportData().withTitle("Categoría y productos inspeccionados")
                .andContents("Categoría seleccionada: " + categoria + "\n" + categorias.entrySet().stream()
                        .map(e -> e.getKey() + " → " + e.getValue()).collect(java.util.stream.Collectors.joining("\n")));
    }
}
