package co.reto.toolshop.preguntas;

import co.reto.toolshop.ui.Acceso;
import co.reto.toolshop.utilidades.Esperas;
import net.serenitybdd.screenplay.*;

public final class EstadoSesion {
    private EstadoSesion() { }
    public static Question<String> error() {
        return actor -> Esperas.visible(actor, Acceso.ERROR_LOGIN).getText().trim();
    }
    public static Question<Boolean> autenticada() {
        return actor -> Esperas.visible(actor, Acceso.FACTURAS_CUENTA).isDisplayed()
                && Esperas.navegador(actor).getCurrentUrl().contains("/account");
    }
    public static Question<Boolean> permaneceEnLogin() {
        return actor -> Esperas.navegador(actor).getCurrentUrl().contains("/auth/login")
                && Esperas.presente(actor, Acceso.INGRESAR) && !Esperas.presente(actor, Acceso.FACTURAS_CUENTA);
    }
}
