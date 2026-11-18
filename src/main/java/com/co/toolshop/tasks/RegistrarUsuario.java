package com.co.toolshop.tasks;

import net.serenitybdd.screenplay.actions.*;
import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.interactions.*;
import com.co.toolshop.models.*;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.Waits;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.Keys;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegistrarUsuario implements Task {
    private final NewUserDTO usuario;
    public RegistrarUsuario(NewUserDTO usuario) { this.usuario = usuario; }
    public static RegistrarUsuario con(NewUserDTO usuario) { return instrumented(RegistrarUsuario.class, usuario); }
    @Step("{0} registra un usuario nuevo con correo UUID")
    public <T extends Actor> void performAs(T actor) {
        actor.remember(ActorMemory.USER, usuario);
        actor.attemptsTo(AbrirTienda.en("/auth/register"),
                Enter.theValue(usuario.name()).into(RegisterPage.TXT_FIRST_NAME).thenHit(Keys.TAB),
                Enter.theValue(usuario.lastName()).into(RegisterPage.TXT_LAST_NAME).thenHit(Keys.TAB),
                Enter.theValue(usuario.dateOfBirth()).into(RegisterPage.TXT_DATE_OF_BIRTH).thenHit(Keys.TAB));
        actor.attemptsTo(SelectFromOptions.byValue(usuario.country()).from(RegisterPage.SELECT_COUNTRY));
        actor.attemptsTo(Write.theValue(usuario.postalCode()).into(RegisterPage.TXT_POSTAL_CODE), Write.theValue(usuario.houseNumber()).into(RegisterPage.TXT_HOUSE_NUMBER),
                Write.theValue(usuario.phone()).into(RegisterPage.TXT_PHONE), Write.theValue(usuario.email()).into(RegisterPage.TXT_EMAIL),
                Write.theValue(usuario.password()).into(RegisterPage.TXT_PASSWORD));
        Waits.until(actor, "Debe finalizar el autocompletado postal", d ->
                !d.findElement(Waits.by(RegisterPage.TXT_STREET)).getAttribute("value").isBlank()
                        && !Waits.present(actor, RegisterPage.LBL_ADDRESS_LOOKUP_LOADING));
        actor.attemptsTo(Write.theValue(usuario.street()).into(RegisterPage.TXT_STREET), Write.theValue(usuario.city()).into(RegisterPage.TXT_CITY),
                Write.theValue(usuario.state()).into(RegisterPage.TXT_STATE), Click.on(RegisterPage.BTN_REGISTER).afterWaitingUntilEnabled());
        Waits.until(actor, "El registro debe redirigir al login; revisar posibles errores de formulario", d -> {
            if (Waits.present(actor, RegisterPage.LBL_REGISTER_ERROR)) {
                throw new IllegalStateException("Registro rechazado: " + d.findElement(Waits.by(RegisterPage.LBL_REGISTER_ERROR)).getText());
            }
            return d.getCurrentUrl().contains("/auth/login");
        });
    }
}
