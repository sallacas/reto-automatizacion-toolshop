package com.co.toolshop.tasks;

import net.serenitybdd.screenplay.actions.*;
import com.co.toolshop.interactions.*;
import com.co.toolshop.models.*;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.*;
import java.util.*;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.WebElement;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AgregarProductos implements Task {
    private final List<Integer> cantidades;
    public AgregarProductos(List<Integer> cantidades) { this.cantidades = List.copyOf(cantidades); }
    public static AgregarProductos alCarritoConCantidades(int... cantidades) {
        if (cantidades.length < 3 || java.util.Arrays.stream(cantidades).anyMatch(c -> c < 1)
                || java.util.Arrays.stream(cantidades).distinct().count() != cantidades.length) {
            throw new IllegalArgumentException("Se requieren al menos tres cantidades positivas y distintas");
        }
        return instrumented(AgregarProductos.class, java.util.Arrays.stream(cantidades).boxed().toList());
    }
    @Step("{0} captura y agrega productos sin descuentos con cantidades #cantidades")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AbrirTienda.en("/"));
        List<String> enlaces = new ArrayList<>();
        do {
            Waits.visible(actor, CatalogPage.LST_PRODUCTS);
            for (WebElement tarjeta : Waits.all(actor, CatalogPage.LST_PRODUCTS)) {
                boolean agotado = !tarjeta.findElements(Waits.by(CatalogPage.LBL_OUT_OF_STOCK)).isEmpty();
                boolean promocion = !tarjeta.findElements(Waits.by(CatalogPage.LBL_PROMOTIONAL_PRICE)).isEmpty();
                var calificaciones = tarjeta.findElements(Waits.by(CatalogPage.LBL_ACTIVE_CO2_RATING));
                boolean sinDescuentoEcologico = !calificaciones.isEmpty()
                        && Set.of("C", "D", "E").contains(calificaciones.getFirst().getText().trim());
                if (!agotado && !promocion && sinDescuentoEcologico) {
                    enlaces.add(tarjeta.getAttribute("href"));
                    if (enlaces.size() == cantidades.size()) break;
                }
            }
            if (enlaces.size() == cantidades.size() || !GoToNextPage.isAvailable(actor)) break;
            actor.attemptsTo(GoToNextPage.inTheCatalog());
        } while (true);
        if (enlaces.size() < cantidades.size()) throw new IllegalStateException("No hay suficientes productos disponibles sin promociones y con calificación C, D o E");

        List<ProductDTO> productos = new ArrayList<>();
        int posicion = 0;
        for (String enlace : enlaces) {
            int cantidad = cantidades.get(posicion++);
            actor.attemptsTo(Open.url(enlace));
            String nombre = Waits.visible(actor, CatalogPage.LBL_DETAIL_NAME).getText().trim();
            var precio = Amounts.fromText(Waits.visible(actor, CatalogPage.LBL_UNIT_PRICE).getText());
            String identificador = enlace.substring(enlace.lastIndexOf('/') + 1);
            String contadorAnterior = Waits.present(actor, NavigationPage.LBL_CART_QUANTITY)
                    ? Waits.visible(actor, NavigationPage.LBL_CART_QUANTITY).getText() : "";
            actor.attemptsTo(Write.theValue(Integer.toString(cantidad)).into(CatalogPage.TXT_QUANTITY), Click.on(CatalogPage.BTN_ADD_TO_CART).afterWaitingUntilEnabled());
            Waits.until(actor, "El contador debe confirmar que el producto se agregó", d ->
                    Waits.present(actor, NavigationPage.LBL_CART_QUANTITY)
                            && !d.findElement(Waits.by(NavigationPage.LBL_CART_QUANTITY)).getText().equals(contadorAnterior));
            productos.add(new ProductDTO(identificador, nombre, precio, cantidad));
        }
        actor.remember(ActorMemory.PRODUCTS, List.copyOf(productos));
        actor.remember(ActorMemory.EXPECTED_TOTAL, Amounts.total(productos));
        actor.attemptsTo(AbrirTienda.en("/checkout"));
        Waits.visible(actor, CartPage.LBL_TOTAL);
    }
}
