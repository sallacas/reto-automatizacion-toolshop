package co.reto.toolshop.ui;

import net.serenitybdd.screenplay.targets.Target;
import static co.reto.toolshop.ui.Elementos.*;

public final class Facturas {
    private Facturas() { }
    public static final Target TITULO = prueba("título de facturas", "page-title");
    public static final Target FILAS = css("facturas de la cuenta", "app-invoices tbody tr");
    public static final Target CELDAS = css("celdas de una factura", "td");
    public static final Target DETALLE = css("enlace al detalle de factura", "a");
    public static final Target NUMERO = prueba("número de factura", "invoice-number");
    public static final Target TOTAL = css("total de factura", "input#total[data-test='total']");
}
