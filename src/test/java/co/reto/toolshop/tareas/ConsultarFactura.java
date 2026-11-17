package co.reto.toolshop.tareas;

import co.reto.toolshop.modelos.Memoria;
import co.reto.toolshop.preguntas.FacturaActual;
import co.reto.toolshop.ui.Facturas;
import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ConsultarFactura implements Task {
    public static ConsultarFactura deLaCompra() { return instrumented(ConsultarFactura.class); }
    @Step("{0} busca y abre la factura creada en su cuenta")
    public <T extends Actor> void performAs(T actor) {
        String numero = actor.recall(Memoria.FACTURA);
        actor.attemptsTo(AbrirTienda.en("/account/invoices"));
        Esperas.hasta(actor, "La cuenta debe mostrar la factura " + numero, d ->
                Esperas.todos(actor, Facturas.FILAS).stream().anyMatch(fila ->
                        fila.findElements(Esperas.por(Facturas.CELDAS)).getFirst().getText().trim().equals(numero)));
        actor.remember(Memoria.TOTAL_FACTURA_LISTADO, actor.asksFor(FacturaActual.totalEnListado()));
        var fila = Esperas.todos(actor, Facturas.FILAS).stream().filter(e ->
                e.findElements(Esperas.por(Facturas.CELDAS)).getFirst().getText().trim().equals(numero)).findFirst().orElseThrow();
        Esperas.dentro(fila, Facturas.DETALLE).click();
        Esperas.visible(actor, Facturas.NUMERO);
    }
}
