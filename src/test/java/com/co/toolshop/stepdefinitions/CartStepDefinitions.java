package com.co.toolshop.stepdefinitions;

import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.tasks.ModificarCantidad;
import com.co.toolshop.models.*;
import com.co.toolshop.questions.*;
import com.co.toolshop.tasks.AgregarProductos;
import io.cucumber.java.es.*;
import java.math.BigDecimal;
import java.util.List;
import net.serenitybdd.core.Serenity;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class CartStepDefinitions {
    @Cuando("agrega tres productos disponibles con cantidades distintas")
    public void agregarProductos() { theActorInTheSpotlight().attemptsTo(AgregarProductos.alCarritoConCantidades(1, 2, 3)); }
    @Cuando("cambia la cantidad del primer producto a {int}")
    public void cambiarCantidad(int cantidad) { theActorInTheSpotlight().attemptsTo(ModificarCantidad.delPrimerProductoA(cantidad)); }
    @Entonces("el carrito coincide con los precios y cantidades capturados")
    public void verificarCarrito() {
        var actor = theActorInTheSpotlight();
        List<ProductDTO> esperados = actor.recall(ActorMemory.PRODUCTS);
        List<CartLineDTO> observados = actor.asksFor(CartLines.fromCart());
        assertThat(observados).as("Productos distintos en el carrito").hasSize(esperados.size());
        for (ProductDTO esperado : esperados) {
            var coincidencias = observados.stream().filter(e -> e.name().equals(esperado.name())).toList();
            assertThat(coincidencias).as("Una línea para %s", esperado.name()).hasSize(1);
            CartLineDTO observado = coincidencias.getFirst();
            assertThat(observado.quantity()).as("Cantidad de %s", esperado.name()).isEqualTo(esperado.quantity());
            assertThat(observado.price()).as("Precio unitario de %s", esperado.name()).isEqualByComparingTo(esperado.unitPrice());
            assertThat(observado.subtotal()).as("Subtotal de %s", esperado.name()).isEqualByComparingTo(esperado.subtotal());
        }
        BigDecimal totalEsperado = actor.recall(ActorMemory.EXPECTED_TOTAL);
        Serenity.recordReportData().withTitle("Precios capturados y cálculo del carrito")
                .andContents(esperados.stream().map(e -> e.name() + ": " + e.unitPrice() + " USD x "
                        + e.quantity() + " = " + e.subtotal() + " USD").collect(java.util.stream.Collectors.joining("\n"))
                        + "\nTotal esperado: " + totalEsperado + " USD\nTotal observado: " + actor.asksFor(CartTotal.value()) + " USD");
        assertThat(actor.asksFor(CartTotal.value())).as("Total calculado desde los precios capturados")
                .isEqualByComparingTo(totalEsperado);
    }
}
