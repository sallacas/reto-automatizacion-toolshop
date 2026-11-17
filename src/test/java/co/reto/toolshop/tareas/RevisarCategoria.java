package co.reto.toolshop.tareas;

import co.reto.toolshop.interacciones.AvanzarPagina;
import co.reto.toolshop.modelos.Memoria;
import co.reto.toolshop.ui.*;
import co.reto.toolshop.utilidades.Esperas;
import java.util.*;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RevisarCategoria implements Task {
    public static RevisarCategoria deTodosLosProductos() { return instrumented(RevisarCategoria.class); }
    @Step("{0} filtra una subcategoría con resultados y consulta cada producto de todas sus páginas")
    public <T extends Actor> void performAs(T actor) {
        Esperas.visible(actor, Catalogo.SUBCATEGORIAS);
        var candidatas = Esperas.todos(actor, Catalogo.SUBCATEGORIAS).stream()
                .map(e -> Map.entry(e.getAttribute("data-test"), e.findElement(Catalogo.ETIQUETA_PADRE).getText().trim())).toList();
        String seleccionada = null;
        for (var candidata : candidatas) {
            var objetivo = Elementos.prueba("subcategoría " + candidata.getValue(), candidata.getKey());
            Esperas.clicable(actor, objetivo).click();
            Esperas.visible(actor, Catalogo.FILTRO_COMPLETADO);
            if (!Esperas.todos(actor, Catalogo.PRODUCTOS).isEmpty()) {
                seleccionada = candidata.getValue();
                break;
            }
            actor.attemptsTo(AbrirTienda.en("/"));
            Esperas.visible(actor, Catalogo.SUBCATEGORIAS);
        }
        if (seleccionada == null) throw new IllegalStateException("No se encontró una subcategoría con productos");
        actor.remember(Memoria.CATEGORIA, seleccionada);
        List<String> enlaces = new ArrayList<>();
        do {
            Esperas.visible(actor, Catalogo.PRODUCTOS);
            enlaces.addAll(Esperas.todos(actor, Catalogo.PRODUCTOS).stream().map(e -> e.getAttribute("href")).toList());
            if (!AvanzarPagina.disponible(actor)) break;
            actor.attemptsTo(AvanzarPagina.siguiente());
        } while (true);
        if (new HashSet<>(enlaces).size() != enlaces.size()) throw new IllegalStateException("El filtro devolvió productos repetidos entre páginas");
        Map<String, String> categorias = new LinkedHashMap<>();
        for (String enlace : enlaces) {
            Esperas.navegador(actor).get(enlace);
            String nombre = Esperas.visible(actor, Catalogo.NOMBRE_DETALLE).getText().trim();
            String categoria = Esperas.visible(actor, Catalogo.CATEGORIA_DETALLE).getText().trim();
            categorias.put(nombre + " (" + enlace + ")", categoria);
        }
        actor.remember(Memoria.CATEGORIAS_OBSERVADAS, Map.copyOf(categorias));
    }
}
