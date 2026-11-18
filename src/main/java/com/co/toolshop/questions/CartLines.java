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

public class CartLines implements Question<List<CartLineDTO>> {
    public static CartLines fromCart() {
        return Instrumented.instanceOf(CartLines.class).withProperties();
    }
    @Override
    public List<CartLineDTO> answeredBy(Actor actor) {
        Waits.visible(actor, CartPage.LBL_TOTAL);
        return Waits.all(actor, CartPage.LST_ROWS).stream().map(row -> new CartLineDTO(
                Waits.within(row, CartPage.LBL_PRODUCT_NAME).getText().trim(),
                Amounts.fromText(Waits.within(row, CartPage.LBL_UNIT_PRICE).getText()),
                Integer.parseInt(Waits.within(row, CartPage.TXT_QUANTITY).getAttribute("value")),
                Amounts.fromText(Waits.within(row, CartPage.LBL_LINE_TOTAL).getText()))).toList();
    }
}
