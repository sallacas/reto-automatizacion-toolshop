package com.co.toolshop.questions;

import com.co.toolshop.userinterfaces.NavigationPage;
import com.co.toolshop.utils.Waits;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

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
