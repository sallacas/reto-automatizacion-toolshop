package co.reto.toolshop.pasos;

import co.reto.toolshop.modelos.Memoria;
import co.reto.toolshop.modelos.DireccionFacturacion;
import co.reto.toolshop.preguntas.FacturaActual;
import co.reto.toolshop.tareas.*;
import io.cucumber.java.es.*;
import java.math.BigDecimal;
import net.serenitybdd.core.Serenity;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class PasosCompra {
    @Cuando("completa la compra con transferencia bancaria simulada")
    public void comprar() { theActorInTheSpotlight().attemptsTo(CompletarCompra.porTransferenciaSimulada()); }
    @Entonces("la factura guardada pertenece a la compra y conserva su total")
    public void validarFactura() {
        var actor = theActorInTheSpotlight();
        String numero = actor.recall(Memoria.FACTURA);
        BigDecimal calculado = actor.recall(Memoria.TOTAL_ESPERADO);
        BigDecimal compra = actor.recall(Memoria.TOTAL_COMPRA);
        assertThat(numero).as("Número de factura confirmado").isNotBlank();
        assertThat(compra).as("Importe final de la compra").isEqualByComparingTo(calculado);
        actor.attemptsTo(ConsultarFactura.deLaCompra());
        BigDecimal listado = actor.recall(Memoria.TOTAL_FACTURA_LISTADO);
        assertThat(listado).as("Total de la factura %s en el listado", numero).isEqualByComparingTo(calculado);
        assertThat(actor.asksFor(FacturaActual.numeroEnDetalle())).as("Factura abierta").isEqualTo(numero);
        assertThat(actor.asksFor(FacturaActual.totalEnDetalle())).as("Total del detalle de factura %s", numero).isEqualByComparingTo(calculado);
        DireccionFacturacion direccion = actor.recall(Memoria.DIRECCION_FACTURACION);
        assertThat(actor.asksFor(FacturaActual.direccionEnDetalle())).as("Dirección de la factura %s", numero).isEqualTo(direccion);
        Serenity.recordReportData().withTitle("Factura verificada")
                .andContents("Factura: " + numero + "\nTotal calculado: " + calculado
                        + " USD\nTotal de compra: " + compra + " USD\nTotal en listado: " + listado
                        + " USD\nTotal en detalle: " + actor.asksFor(FacturaActual.totalEnDetalle()) + " USD");
        Serenity.takeScreenshot();
    }
}
