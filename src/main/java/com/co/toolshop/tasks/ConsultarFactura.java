package com.co.toolshop.tasks;

import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.questions.*;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.Waits;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ConsultarFactura implements Task {
    private final String numero;
    public ConsultarFactura(String numero) { this.numero = numero; }
    public static ConsultarFactura porNumero(String numero) { return instrumented(ConsultarFactura.class, numero); }
    @Step("{0} busca y abre la factura creada en su cuenta")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AbrirTienda.en("/account/invoices"));
        Waits.until(actor, "La cuenta debe mostrar la factura " + numero, d ->
                Waits.all(actor, InvoicesPage.LST_ROWS).stream().anyMatch(fila ->
                        fila.findElements(Waits.by(InvoicesPage.LST_CELLS)).getFirst().getText().trim().equals(numero)));
        actor.remember(ActorMemory.LISTED_INVOICE_TOTAL, actor.asksFor(InvoiceListingTotal.forNumber(numero)));
        var fila = Waits.all(actor, InvoicesPage.LST_ROWS).stream().filter(e ->
                e.findElements(Waits.by(InvoicesPage.LST_CELLS)).getFirst().getText().trim().equals(numero)).findFirst().orElseThrow();
        Waits.within(fila, InvoicesPage.LNK_DETAILS).click();
        Waits.visible(actor, InvoicesPage.TXT_INVOICE_NUMBER);
    }
}
