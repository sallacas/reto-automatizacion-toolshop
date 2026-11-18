package com.co.toolshop.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import static com.co.toolshop.userinterfaces.Elements.*;

public final class InvoicesPage {
    private InvoicesPage() { }
    public static final Target LBL_PAGE_TITLE = test("título de facturas", "page-title");
    public static final Target LST_ROWS = css("facturas de la cuenta", "app-invoices tbody tr");
    public static final Target LST_CELLS = css("celdas de una factura", "td");
    public static final Target LNK_DETAILS = css("enlace al detalle de factura", "a");
    public static final Target TXT_INVOICE_NUMBER = test("número de factura", "invoice-number");
    public static final Target TXT_TOTAL = css("total de factura", "input#total[data-test='total']");
    public static final Target TXT_COUNTRY = test("país de facturación", "country");
    public static final Target TXT_POSTAL_CODE = test("código postal de facturación", "postal_code");
    public static final Target TXT_STREET = test("calle de facturación", "street");
    public static final Target TXT_CITY = test("ciudad de facturación", "city");
    public static final Target TXT_STATE = test("estado de facturación", "state");
}
