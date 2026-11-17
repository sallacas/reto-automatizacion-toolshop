package co.reto.toolshop.pasos;

import co.reto.toolshop.datos.GeneradorUsuarios;
import co.reto.toolshop.modelos.*;
import co.reto.toolshop.preguntas.EstadoSesion;
import co.reto.toolshop.tareas.*;
import co.reto.toolshop.ui.Acceso;
import co.reto.toolshop.utilidades.Esperas;
import io.cucumber.java.es.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class PasosAutenticacion {
    @Dado("que un cliente nuevo se registra e inicia sesión")
    public void registrarEIngresar() {
        var actor = theActorInTheSpotlight();
        actor.attemptsTo(RegistrarUsuario.nuevo());
        actor.attemptsTo(IniciarSesion.con(actor.recall(Memoria.USUARIO)));
        Esperas.hasta(actor, "Debe ingresar a su cuenta", d -> d.getCurrentUrl().contains("/account"));
        actor.should(seeThat("la sesión del nuevo usuario", EstadoSesion.autenticada(), is(true)));
    }
    @Dado("que un visitante dispone de credenciales de un usuario no registrado")
    public void usuarioNoRegistrado() { theActorInTheSpotlight().remember(Memoria.USUARIO, GeneradorUsuarios.nuevo()); }
    @Cuando("intenta iniciar sesión con esas credenciales")
    public void intentarIngreso() {
        var actor = theActorInTheSpotlight();
        actor.attemptsTo(IniciarSesion.con(actor.recall(Memoria.USUARIO)));
    }
    @Entonces("la tienda muestra el error de credenciales inválidas y mantiene el login")
    public void comprobarRechazo() {
        theActorInTheSpotlight().should(
                seeThat("el mensaje de autenticación", EstadoSesion.error(), equalTo("Invalid email or password")),
                seeThat("la ausencia de acceso autenticado", EstadoSesion.permaneceEnLogin(), is(true)));
    }
}
