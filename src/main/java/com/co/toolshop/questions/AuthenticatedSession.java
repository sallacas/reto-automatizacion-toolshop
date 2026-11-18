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

public class AuthenticatedSession implements Question<Boolean> {
    public static AuthenticatedSession inTheAccount() {
        return Instrumented.instanceOf(AuthenticatedSession.class).withProperties();
    }
    @Override
    public Boolean answeredBy(Actor actor) {
        return Waits.visible(actor, NavigationPage.LNK_ACCOUNT_INVOICES).isDisplayed()
                && Waits.driver(actor).getCurrentUrl().contains("/account");
    }
}
