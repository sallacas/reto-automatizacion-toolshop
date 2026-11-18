package com.co.toolshop.stepdefinitions;

import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.models.BillingAddressDTO;
import com.co.toolshop.questions.*;
import com.co.toolshop.tasks.*;
import io.cucumber.java.es.*;
import java.math.BigDecimal;
import net.serenitybdd.core.Serenity;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseStepDefinitions {
    @Cuando("completa la compra con transferencia bancaria simulada")
    public void comprar() { theActorInTheSpotlight().attemptsTo(CompletarCompra.porTransferenciaBancaria()); }
    @Entonces("la factura guardada pertenece a la compra y conserva su total")
    public void validarFactura() {
        var actor = theActorInTheSpotlight();
        String numero = actor.recall(ActorMemory.INVOICE_NUMBER);
        BigDecimal calculado = actor.recall(ActorMemory.EXPECTED_TOTAL);
        BigDecimal compra = actor.recall(ActorMemory.PURCHASE_TOTAL);
        assertThat(numero).as("Número de factura confirmado").isNotBlank();
        assertThat(compra).as("Importe final de la compra").isEqualByComparingTo(calculado);
        actor.attemptsTo(ConsultarFactura.porNumero(numero));
        BigDecimal listado = actor.recall(ActorMemory.LISTED_INVOICE_TOTAL);
        assertThat(listado).as("Total de la factura %s en el listado", numero).isEqualByComparingTo(calculado);
        assertThat(actor.asksFor(InvoiceNumber.inDetails())).as("Factura abierta").isEqualTo(numero);
        assertThat(actor.asksFor(InvoiceTotal.inDetails())).as("Total del detalle de factura %s", numero).isEqualByComparingTo(calculado);
        BillingAddressDTO direccion = actor.recall(ActorMemory.BILLING_ADDRESS);
        assertThat(actor.asksFor(InvoiceAddress.inDetails())).as("Dirección de la factura %s", numero).isEqualTo(direccion);
        Serenity.recordReportData().withTitle("Factura verificada")
                .andContents("Factura: " + numero + "\nTotal calculado: " + calculado
                        + " USD\nTotal de compra: " + compra + " USD\nTotal en listado: " + listado
                        + " USD\nTotal en detalle: " + actor.asksFor(InvoiceTotal.inDetails()) + " USD");
        Serenity.takeScreenshot();
    }
}
