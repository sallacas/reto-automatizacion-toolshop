package com.co.toolshop.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import static com.co.toolshop.userinterfaces.Elements.*;

public final class NavigationPage {
    private NavigationPage() { }
    public static final Target BTN_LANGUAGE = test("selector de idioma", "language-select");
    public static final Target LNK_SPANISH = test("idioma español", "lang-es");
    public static final Target LNK_ACCOUNT_INVOICES = test("facturas de la cuenta autenticada", "nav-invoices");
    public static final Target LBL_CART_QUANTITY = test("contador del carrito", "cart-quantity");
}
