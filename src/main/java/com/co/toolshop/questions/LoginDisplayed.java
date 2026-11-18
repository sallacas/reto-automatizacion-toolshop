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

public class LoginDisplayed implements Question<Boolean> {
    public static LoginDisplayed withoutSession() {
        return Instrumented.instanceOf(LoginDisplayed.class).withProperties();
    }
    @Override
    public Boolean answeredBy(Actor actor) {
        return Waits.driver(actor).getCurrentUrl().contains("/auth/login")
                && Waits.present(actor, LoginPage.BTN_LOGIN)
                && !Waits.present(actor, NavigationPage.LNK_ACCOUNT_INVOICES);
    }
}
