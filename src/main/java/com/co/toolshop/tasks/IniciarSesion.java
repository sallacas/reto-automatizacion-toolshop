package com.co.toolshop.tasks;

import net.serenitybdd.screenplay.actions.*;
import com.co.toolshop.interactions.*;
import com.co.toolshop.models.*;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.Waits;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class IniciarSesion implements Task {
    private final NewUserDTO usuario;
    public IniciarSesion(NewUserDTO usuario) { this.usuario = usuario; }
    public static IniciarSesion enLaTiendaCon(NewUserDTO usuario) { return instrumented(IniciarSesion.class, usuario); }
    @Step("{0} ingresa con las credenciales del usuario de prueba")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(AbrirTienda.en("/auth/login"), Write.theValue(usuario.email()).into(LoginPage.TXT_EMAIL),
                Write.theValue(usuario.password()).into(LoginPage.TXT_PASSWORD), Click.on(LoginPage.BTN_LOGIN).afterWaitingUntilEnabled());
    }
}
