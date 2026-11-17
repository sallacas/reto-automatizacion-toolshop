package co.reto.toolshop.pasos;

import co.reto.toolshop.modelos.Memoria;
import co.reto.toolshop.preguntas.FacturaActual;
import co.reto.toolshop.tareas.*;
import io.cucumber.java.es.*;
import java.math.BigDecimal;
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
        assertThat(actor.asksFor(FacturaActual.totalEnListado())).as("Total de la factura %s", numero).isEqualByComparingTo(calculado);
    }
}
