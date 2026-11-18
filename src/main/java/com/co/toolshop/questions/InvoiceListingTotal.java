package com.co.toolshop.questions;

import com.co.toolshop.models.*;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.*;
import java.math.BigDecimal;
import java.util.List;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class InvoiceListingTotal implements Question<BigDecimal> {
    private final String number;
    public InvoiceListingTotal(String number) { this.number = number; }
    public static InvoiceListingTotal forNumber(String number) {
        return Instrumented.instanceOf(InvoiceListingTotal.class).withProperties(number);
    }
    @Override
    public BigDecimal answeredBy(Actor actor) {
        var row = Waits.all(actor, InvoicesPage.LST_ROWS).stream().filter(e ->
                e.findElements(Waits.by(InvoicesPage.LST_CELLS)).getFirst().getText().trim().equals(number))
                .findFirst().orElseThrow(() -> new IllegalStateException("Factura ausente: " + number));
        return Amounts.fromText(row.findElements(Waits.by(InvoicesPage.LST_CELLS)).get(3).getText());
    }
}
