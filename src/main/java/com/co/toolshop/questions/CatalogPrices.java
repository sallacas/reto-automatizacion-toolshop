package com.co.toolshop.questions;

import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.*;
import java.math.BigDecimal;
import java.util.List;
import net.serenitybdd.screenplay.*;

public class CatalogPrices implements Question<List<BigDecimal>> {
    public static CatalogPrices onCurrentPage() { return new CatalogPrices(); }
    public List<BigDecimal> answeredBy(Actor actor) {
        Waits.visible(actor, CatalogPage.LST_PRODUCTS);
        return Waits.all(actor, CatalogPage.LST_PRODUCTS).stream()
                .map(tarjeta -> Amounts.fromText(Waits.within(tarjeta, CatalogPage.LBL_PRODUCT_PRICE).getText())).toList();
    }
}
