package co.reto.toolshop.pasos;

import co.reto.toolshop.modelos.Memoria;
import co.reto.toolshop.tareas.*;
import io.cucumber.java.es.*;
import java.math.BigDecimal;
import java.util.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class PasosCatalogo {
    @Dado("que un visitante abre el catálogo")
    public void abrirCatalogo() { theActorInTheSpotlight().attemptsTo(AbrirTienda.en("/")); }
    @Cuando("ordena por precio ascendente y recorre todas las páginas")
    public void ordenar() { theActorInTheSpotlight().attemptsTo(RecorrerOrdenamiento.ascendente()); }
    @Entonces("los precios están ordenados de menor a mayor entre todas las páginas")
    public void comprobarOrden() {
        List<BigDecimal> precios = theActorInTheSpotlight().recall(Memoria.PRECIOS_CATALOGO);
        assertThat(precios).as("Precios de todas las páginas del catálogo").isNotEmpty().isSortedAccordingTo(BigDecimal::compareTo);
    }
    @Cuando("filtra una subcategoría disponible y revisa todos sus productos")
    public void filtrar() { theActorInTheSpotlight().attemptsTo(RevisarCategoria.deTodosLosProductos()); }
    @Entonces("todos los productos pertenecen a la categoría seleccionada")
    public void comprobarCategorias() {
        String categoria = theActorInTheSpotlight().recall(Memoria.CATEGORIA);
        Map<String, String> categorias = theActorInTheSpotlight().recall(Memoria.CATEGORIAS_OBSERVADAS);
        assertThat(categorias).as("Productos inspeccionados de %s", categoria).isNotEmpty();
        categorias.forEach((producto, observada) -> assertThat(observada).as("Categoría real de %s", producto).isEqualTo(categoria));
    }
}
