package co.reto.toolshop.tareas;

import co.reto.toolshop.interacciones.*;
import co.reto.toolshop.modelos.*;
import co.reto.toolshop.preguntas.CarritoActual;
import co.reto.toolshop.ui.*;
import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.support.ui.Select;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CompletarCompra implements Task {
    public static CompletarCompra porTransferenciaSimulada() { return instrumented(CompletarCompra.class); }
    @Step("{0} completa facturación y transferencia bancaria ficticia")
    public <T extends Actor> void performAs(T actor) {
        UsuarioPrueba usuario = actor.recall(Memoria.USUARIO);
        actor.remember(Memoria.TOTAL_COMPRA, actor.asksFor(CarritoActual.total()));
        actor.attemptsTo(Pulsar.sobre(Carrito.CONTINUAR), Pulsar.sobre(Carrito.CONTINUAR_SESION));
        // La cuenta aporta la dirección; el número no se guarda en el perfil y se completa aquí.
        Esperas.hasta(actor, "Debe cargarse la dirección de la cuenta", d ->
                !Esperas.visible(actor, Acceso.CALLE).getAttribute("value").isBlank());
        actor.attemptsTo(Escribir.en(Acceso.NUMERO, usuario.numero()));
        // Esperar una respuesta postal observable antes de continuar, sin pausas fijas.
        Esperas.hasta(actor, "Debe finalizar la búsqueda postal de facturación", d ->
                !Esperas.presente(actor, Acceso.BUSQUEDA_POSTAL)
                        && Esperas.visible(actor, Acceso.CALLE).getAttribute("value").contains(usuario.numero()));
        actor.attemptsTo(Escribir.en(Acceso.CALLE, usuario.calle()), Escribir.en(Acceso.CIUDAD, usuario.ciudad()),
                Escribir.en(Acceso.ESTADO, usuario.estado()), Pulsar.sobre(Pago.CONTINUAR_DIRECCION));
        new Select(Esperas.clicable(actor, Pago.METODO)).selectByValue("bank-transfer");
        actor.attemptsTo(Escribir.en(Pago.BANCO, "Banco de Pruebas"),
                Escribir.en(Pago.TITULAR, usuario.nombre() + " " + usuario.apellido()),
                Escribir.en(Pago.CUENTA, usuario.telefono()), Pulsar.sobre(Pago.FINALIZAR));
        Esperas.visible(actor, Pago.PAGO_VALIDADO);
        actor.attemptsTo(Pulsar.sobre(Pago.FINALIZAR));
        String numero = Esperas.visible(actor, Pago.NUMERO_FACTURA_CONFIRMADA).getText().trim();
        if (numero.isBlank()) throw new IllegalStateException("La compra no devolvió número de factura");
        actor.remember(Memoria.FACTURA, numero);
    }
}
