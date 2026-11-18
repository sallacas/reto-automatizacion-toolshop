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

public class InvoiceNumber implements Question<String> {
    public static InvoiceNumber inDetails() {
        return Instrumented.instanceOf(InvoiceNumber.class).withProperties();
    }
    @Override
    public String answeredBy(Actor actor) {
        return Waits.visible(actor, InvoicesPage.TXT_INVOICE_NUMBER).getAttribute("value");
    }
}
