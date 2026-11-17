package co.reto.toolshop.tareas;

import co.reto.toolshop.interacciones.*;
import co.reto.toolshop.modelos.*;
import co.reto.toolshop.ui.Acceso;
import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class IniciarSesion implements Task {
    private final UsuarioPrueba usuario;
    public IniciarSesion(UsuarioPrueba usuario) { this.usuario = usuario; }
    public static IniciarSesion con(UsuarioPrueba usuario) { return instrumented(IniciarSesion.class, usuario); }
    @Step("{0} ingresa con las credenciales del usuario de prueba")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AbrirTienda.en("/auth/login"), Escribir.en(Acceso.CORREO, usuario.correo()),
                Escribir.en(Acceso.CLAVE, usuario.clave()), Pulsar.sobre(Acceso.INGRESAR));
    }
}
