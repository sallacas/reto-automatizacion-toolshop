package com.co.toolshop.questions;

import com.co.toolshop.userinterfaces.InvoicesPage;
import com.co.toolshop.utils.Waits;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class InvoiceNumber implements Question<String> {
    public static InvoiceNumber inDetails() {
        return Instrumented.instanceOf(InvoiceNumber.class).withProperties();
    }
    @Override
    public String answeredBy(Actor actor) {
        return Waits.visible(actor, InvoicesPage.TXT_INVOICE_NUMBER).getAttribute("value");
    }
}
