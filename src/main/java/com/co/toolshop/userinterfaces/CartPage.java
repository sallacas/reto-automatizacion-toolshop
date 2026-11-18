package com.co.toolshop.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import static com.co.toolshop.userinterfaces.Elements.*;

public final class CartPage {
    private CartPage() { }
    public static final Target LST_ROWS = css("líneas del carrito", "app-cart tbody tr:has([data-test='product-title'])");
    public static final Target LBL_PRODUCT_NAME = test("nombre en carrito", "product-title");
    public static final Target LBL_UNIT_PRICE = test("precio en carrito", "product-price");
    public static final Target TXT_QUANTITY = test("cantidad en carrito", "product-quantity");
    public static final Target LBL_LINE_TOTAL = test("subtotal de producto", "line-price");
    public static final Target LBL_TOTAL = test("total del carrito", "cart-total");
    public static final Target BTN_CONTINUE = test("continuar desde el carrito", "proceed-1");
    public static final Target BTN_CONTINUE_AS_CUSTOMER = test("continuar con la sesión iniciada", "proceed-2");
}
