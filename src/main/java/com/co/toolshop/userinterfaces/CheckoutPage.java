package com.co.toolshop.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import static com.co.toolshop.userinterfaces.Elements.*;

public final class CheckoutPage {
    private CheckoutPage() { }
    public static final Target SELECT_COUNTRY = test("país", "country");
    public static final Target TXT_POSTAL_CODE = test("código postal", "postal_code");
    public static final Target TXT_HOUSE_NUMBER = test("número", "house_number");
    public static final Target TXT_STREET = test("calle", "street");
    public static final Target TXT_CITY = test("ciudad", "city");
    public static final Target TXT_STATE = test("estado", "state");
    public static final Target LBL_ADDRESS_LOOKUP_LOADING = test("búsqueda de dirección pendiente", "postcode-lookup-loading");
    public static final Target BTN_CONTINUE_TO_PAYMENT = test("continuar desde facturación", "proceed-3");
    public static final Target SELECT_PAYMENT_METHOD = test("método de pago", "payment-method");
    public static final Target TXT_BANK_NAME = test("banco ficticio", "bank_name");
    public static final Target TXT_ACCOUNT_HOLDER = test("titular ficticio", "account_name");
    public static final Target TXT_ACCOUNT_NUMBER = test("cuenta ficticia", "account_number");
    public static final Target BTN_FINISH = test("validar o confirmar pago", "finish");
    public static final Target LBL_PAYMENT_SUCCESS = test("pago validado", "payment-success-message");
    public static final Target LBL_ORDER_CONFIRMATION = css("confirmación de la compra", "#order-confirmation");
}
