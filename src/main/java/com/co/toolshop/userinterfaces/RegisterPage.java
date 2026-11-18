package com.co.toolshop.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import static com.co.toolshop.userinterfaces.Elements.*;

public final class RegisterPage {
    private RegisterPage() { }
    public static final Target TXT_FIRST_NAME = test("nombre", "first-name");
    public static final Target TXT_LAST_NAME = test("apellido", "last-name");
    public static final Target TXT_DATE_OF_BIRTH = test("fecha de nacimiento", "dob");
    public static final Target SELECT_COUNTRY = test("país", "country");
    public static final Target TXT_POSTAL_CODE = test("código postal", "postal_code");
    public static final Target TXT_HOUSE_NUMBER = test("número", "house_number");
    public static final Target TXT_STREET = test("calle", "street");
    public static final Target TXT_CITY = test("ciudad", "city");
    public static final Target TXT_STATE = test("estado", "state");
    public static final Target TXT_PHONE = test("teléfono", "phone");
    public static final Target TXT_EMAIL = test("correo", "email");
    public static final Target TXT_PASSWORD = test("contraseña", "password");
    public static final Target BTN_REGISTER = test("registrar usuario", "register-submit");
    public static final Target LBL_REGISTER_ERROR = test("error de registro", "register-error");
    public static final Target LBL_ADDRESS_LOOKUP_LOADING = test("búsqueda de dirección pendiente", "postcode-lookup-loading");
}
