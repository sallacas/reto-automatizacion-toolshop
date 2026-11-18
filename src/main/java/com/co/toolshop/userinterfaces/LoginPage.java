package com.co.toolshop.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import static com.co.toolshop.userinterfaces.Elements.*;

public final class LoginPage {
    private LoginPage() { }
    public static final Target TXT_EMAIL = test("correo", "email");
    public static final Target TXT_PASSWORD = test("contraseña", "password");
    public static final Target BTN_LOGIN = test("iniciar sesión", "login-submit");
    public static final Target LBL_LOGIN_ERROR = test("error de autenticación", "login-error");
}
