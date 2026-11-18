package com.co.toolshop.questions;

import com.co.toolshop.userinterfaces.CartPage;
import com.co.toolshop.utils.Waits;
import com.co.toolshop.utils.Amounts;
import java.math.BigDecimal;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CartTotal implements Question<BigDecimal> {
    public static CartTotal value() {
        return Instrumented.instanceOf(CartTotal.class).withProperties();
    }
    @Override
    public BigDecimal answeredBy(Actor actor) {
        return Amounts.fromText(Waits.visible(actor, CartPage.LBL_TOTAL).getText());
    }
}
