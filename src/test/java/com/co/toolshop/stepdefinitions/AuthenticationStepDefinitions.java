package com.co.toolshop.stepdefinitions;

import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.utils.data.UserGenerator;
import com.co.toolshop.models.*;
import com.co.toolshop.questions.*;
import com.co.toolshop.tasks.*;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.Waits;
import io.cucumber.java.es.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class AuthenticationStepDefinitions {
    @Dado("que un cliente nuevo se registra e inicia sesión")
    public void registrarEIngresar() {
        var actor = theActorInTheSpotlight();
        actor.attemptsTo(RegistrarUsuario.con(UserGenerator.newUser()));
        actor.attemptsTo(IniciarSesion.enLaTiendaCon(actor.recall(ActorMemory.USER)));
        Waits.until(actor, "Debe ingresar a su cuenta", d -> d.getCurrentUrl().contains("/account"));
        actor.should(seeThat("la sesión del nuevo usuario", AuthenticatedSession.inTheAccount(), is(true)));
    }
    @Dado("que un visitante dispone de credenciales de un usuario no registrado")
    public void usuarioNoRegistrado() { theActorInTheSpotlight().remember(ActorMemory.USER, UserGenerator.newUser()); }
    @Cuando("intenta iniciar sesión con esas credenciales")
    public void intentarIngreso() {
        var actor = theActorInTheSpotlight();
        actor.attemptsTo(IniciarSesion.enLaTiendaCon(actor.recall(ActorMemory.USER)));
    }
    @Entonces("la tienda muestra el error de credenciales inválidas y mantiene el login")
    public void comprobarRechazo() {
        theActorInTheSpotlight().should(
                seeThat("el mensaje de autenticación", ValidateText.of(LoginPage.LBL_LOGIN_ERROR), equalTo("Invalid email or password")),
                seeThat("la ausencia de acceso autenticado", LoginDisplayed.withoutSession(), is(true)));
    }
}
