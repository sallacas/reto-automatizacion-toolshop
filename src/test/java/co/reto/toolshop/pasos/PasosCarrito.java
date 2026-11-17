package co.reto.toolshop.pasos;

import co.reto.toolshop.interacciones.CambiarCantidad;
import co.reto.toolshop.modelos.*;
import co.reto.toolshop.preguntas.CarritoActual;
import co.reto.toolshop.tareas.AgregarProductos;
import io.cucumber.java.es.*;
import java.math.BigDecimal;
import java.util.List;
import net.serenitybdd.core.Serenity;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class PasosCarrito {
    @Cuando("agrega tres productos disponibles con cantidades distintas")
    public void agregarProductos() { theActorInTheSpotlight().attemptsTo(AgregarProductos.distintos()); }
    @Cuando("cambia la cantidad del primer producto a {int}")
    public void cambiarCantidad(int cantidad) { theActorInTheSpotlight().attemptsTo(CambiarCantidad.delPrimeroA(cantidad)); }
    @Entonces("el carrito coincide con los precios y cantidades capturados")
    public void verificarCarrito() {
        var actor = theActorInTheSpotlight();
        List<ProductoCapturado> esperados = actor.recall(Memoria.PRODUCTOS);
        List<LineaCarrito> observados = actor.asksFor(CarritoActual.lineas());
        assertThat(observados).as("Productos distintos en el carrito").hasSize(esperados.size());
        for (ProductoCapturado esperado : esperados) {
            var coincidencias = observados.stream().filter(e -> e.nombre().equals(esperado.nombre())).toList();
            assertThat(coincidencias).as("Una línea para %s", esperado.nombre()).hasSize(1);
            LineaCarrito observado = coincidencias.getFirst();
            assertThat(observado.cantidad()).as("Cantidad de %s", esperado.nombre()).isEqualTo(esperado.cantidad());
            assertThat(observado.precio()).as("Precio unitario de %s", esperado.nombre()).isEqualByComparingTo(esperado.precioUnitario());
            assertThat(observado.subtotal()).as("Subtotal de %s", esperado.nombre()).isEqualByComparingTo(esperado.subtotal());
        }
        BigDecimal totalEsperado = actor.recall(Memoria.TOTAL_ESPERADO);
        Serenity.recordReportData().withTitle("Precios capturados y cálculo del carrito")
                .andContents(esperados.stream().map(e -> e.nombre() + ": " + e.precioUnitario() + " USD x "
                        + e.cantidad() + " = " + e.subtotal() + " USD").collect(java.util.stream.Collectors.joining("\n"))
                        + "\nTotal esperado: " + totalEsperado + " USD\nTotal observado: " + actor.asksFor(CarritoActual.total()) + " USD");
        assertThat(actor.asksFor(CarritoActual.total())).as("Total calculado desde los precios capturados")
                .isEqualByComparingTo(totalEsperado);
    }
}
