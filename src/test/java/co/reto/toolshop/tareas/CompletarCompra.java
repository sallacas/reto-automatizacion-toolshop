package co.reto.toolshop.tareas;

import co.reto.toolshop.interacciones.*;
import co.reto.toolshop.modelos.*;
import co.reto.toolshop.preguntas.CarritoActual;
import co.reto.toolshop.preguntas.NumeroFacturaConfirmada;
import co.reto.toolshop.ui.*;
import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
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
        // El servicio puede devolver la misma calle del perfil. Un valor sintético único
        // permite observar la respuesta, incluso si el domicilio autocompletado se repite.
        actor.attemptsTo(Escribir.en(Acceso.CALLE, usuario.calle()), Escribir.en(Acceso.NUMERO, usuario.numero()));
        // Esperar una respuesta postal observable antes de continuar, sin pausas fijas.
        Esperas.hasta(actor, "Debe finalizar la búsqueda postal de facturación", d ->
                !Esperas.presente(actor, Acceso.BUSQUEDA_POSTAL)
                        && !Esperas.visible(actor, Acceso.CALLE).getAttribute("value").equals(usuario.calle()));
        // La API valida ciudad y estado contra el servicio postal. Se conservan los
        // valores autocompletados y se captura la dirección realmente enviada.
        actor.attemptsTo(Escribir.en(Acceso.CALLE, usuario.calle()));
        actor.remember(Memoria.DIRECCION_FACTURACION, new DireccionFacturacion(usuario.pais(), usuario.codigoPostal(),
                usuario.calle(), Esperas.visible(actor, Acceso.CIUDAD).getAttribute("value"),
                Esperas.visible(actor, Acceso.ESTADO).getAttribute("value")));
        actor.attemptsTo(Pulsar.sobre(Pago.CONTINUAR_DIRECCION));
        actor.attemptsTo(SeleccionarOpcion.en(Pago.METODO, "bank-transfer"));
        actor.attemptsTo(Escribir.en(Pago.BANCO, "Banco de Pruebas"),
                Escribir.en(Pago.TITULAR, usuario.nombre() + " " + usuario.apellido()),
                Escribir.en(Pago.CUENTA, usuario.telefono()), Pulsar.sobre(Pago.FINALIZAR));
        Esperas.visible(actor, Pago.PAGO_VALIDADO);
        actor.attemptsTo(Pulsar.sobre(Pago.FINALIZAR));
        actor.remember(Memoria.FACTURA, actor.asksFor(NumeroFacturaConfirmada.enLaCompra()));
    }
}
