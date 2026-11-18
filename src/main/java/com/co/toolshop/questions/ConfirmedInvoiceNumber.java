package com.co.toolshop.questions;

import com.co.toolshop.userinterfaces.CheckoutPage;
import com.co.toolshop.utils.Waits;
import java.util.regex.Pattern;
import net.serenitybdd.screenplay.*;

public class ConfirmedInvoiceNumber implements Question<String> {
    public static ConfirmedInvoiceNumber fromPurchase() { return new ConfirmedInvoiceNumber(); }
    public String answeredBy(Actor actor) {
        String confirmacion = Waits.visible(actor, CheckoutPage.LBL_ORDER_CONFIRMATION).getText();
        // Angular sanitiza el HTML traducido y elimina el id del span interno.
        // El número se extrae del texto del contenedor estable de confirmación.
        var coincidencia = Pattern.compile("\\bINV-\\d+\\b").matcher(confirmacion);
        if (!coincidencia.find()) throw new IllegalStateException("Confirmación sin número de factura: " + confirmacion);
        return coincidencia.group();
    }
}
