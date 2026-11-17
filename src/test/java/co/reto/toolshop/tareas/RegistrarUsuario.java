package co.reto.toolshop.tareas;

import co.reto.toolshop.datos.GeneradorUsuarios;
import co.reto.toolshop.interacciones.*;
import co.reto.toolshop.modelos.*;
import co.reto.toolshop.ui.Acceso;
import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.support.ui.Select;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegistrarUsuario implements Task {
    public static RegistrarUsuario nuevo() { return instrumented(RegistrarUsuario.class); }
    @Step("{0} registra un usuario nuevo con correo UUID")
    public <T extends Actor> void performAs(T actor) {
        UsuarioPrueba usuario = GeneradorUsuarios.nuevo();
        actor.remember(Memoria.USUARIO, usuario);
        actor.attemptsTo(AbrirTienda.en("/auth/register"),
                Escribir.en(Acceso.NOMBRE, usuario.nombre()), Escribir.en(Acceso.APELLIDO, usuario.apellido()),
                Escribir.en(Acceso.NACIMIENTO, usuario.nacimiento()));
        new Select(Esperas.clicable(actor, Acceso.PAIS)).selectByValue(usuario.pais());
        actor.attemptsTo(Escribir.en(Acceso.CODIGO_POSTAL, usuario.codigoPostal()), Escribir.en(Acceso.NUMERO, usuario.numero()),
                Escribir.en(Acceso.TELEFONO, usuario.telefono()), Escribir.en(Acceso.CORREO, usuario.correo()),
                Escribir.en(Acceso.CLAVE, usuario.clave()));
        Esperas.hasta(actor, "Debe finalizar el autocompletado postal", d ->
                !d.findElement(Esperas.por(Acceso.CALLE)).getAttribute("value").isBlank()
                        && !Esperas.presente(actor, Acceso.BUSQUEDA_POSTAL));
        actor.attemptsTo(Escribir.en(Acceso.CALLE, usuario.calle()), Escribir.en(Acceso.CIUDAD, usuario.ciudad()),
                Escribir.en(Acceso.ESTADO, usuario.estado()), Pulsar.sobre(Acceso.REGISTRAR));
        Esperas.hasta(actor, "El registro debe redirigir al login; revisar posibles errores de formulario", d -> {
            if (Esperas.presente(actor, Acceso.ERROR_REGISTRO)) {
                throw new IllegalStateException("Registro rechazado: " + d.findElement(Esperas.por(Acceso.ERROR_REGISTRO)).getText());
            }
            return d.getCurrentUrl().contains("/auth/login");
        });
    }
}
