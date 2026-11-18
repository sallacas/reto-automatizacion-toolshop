package com.co.toolshop.questions;

import com.co.toolshop.userinterfaces.InvoicesPage;
import com.co.toolshop.utils.Waits;
import com.co.toolshop.utils.Amounts;
import java.math.BigDecimal;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class InvoiceTotal implements Question<BigDecimal> {
    public static InvoiceTotal inDetails() {
        return Instrumented.instanceOf(InvoiceTotal.class).withProperties();
    }
    @Override
    public BigDecimal answeredBy(Actor actor) {
        return Amounts.fromText(Waits.visible(actor, InvoicesPage.TXT_TOTAL).getAttribute("value"));
    }
}
