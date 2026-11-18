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

public class InvoiceAddress implements Question<BillingAddressDTO> {
    public static InvoiceAddress inDetails() {
        return Instrumented.instanceOf(InvoiceAddress.class).withProperties();
    }
    @Override
    public BillingAddressDTO answeredBy(Actor actor) {
        return new BillingAddressDTO(Waits.visible(actor, InvoicesPage.TXT_COUNTRY).getAttribute("value"),
                Waits.visible(actor, InvoicesPage.TXT_POSTAL_CODE).getAttribute("value"),
                Waits.visible(actor, InvoicesPage.TXT_STREET).getAttribute("value"),
                Waits.visible(actor, InvoicesPage.TXT_CITY).getAttribute("value"),
                Waits.visible(actor, InvoicesPage.TXT_STATE).getAttribute("value"));
    }
}
