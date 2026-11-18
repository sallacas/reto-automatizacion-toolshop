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

public class CartTotal implements Question<BigDecimal> {
    public static CartTotal value() {
        return Instrumented.instanceOf(CartTotal.class).withProperties();
    }
    @Override
    public BigDecimal answeredBy(Actor actor) {
        return Amounts.fromText(Waits.visible(actor, CartPage.LBL_TOTAL).getText());
    }
}
