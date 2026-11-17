package co.reto.toolshop.ui;

import net.serenitybdd.screenplay.targets.Target;
import static co.reto.toolshop.ui.Elementos.*;

public final class Acceso {
    private Acceso() { }
    public static final Target IDIOMA = prueba("selector de idioma", "language-select");
    public static final Target ESPANOL = prueba("idioma español", "lang-es");
    public static final Target NOMBRE = prueba("nombre", "first-name");
    public static final Target APELLIDO = prueba("apellido", "last-name");
    public static final Target NACIMIENTO = prueba("fecha de nacimiento", "dob");
    public static final Target PAIS = prueba("país", "country");
    public static final Target CODIGO_POSTAL = prueba("código postal", "postal_code");
    public static final Target NUMERO = prueba("número", "house_number");
    public static final Target CALLE = prueba("calle", "street");
    public static final Target CIUDAD = prueba("ciudad", "city");
    public static final Target ESTADO = prueba("estado", "state");
    public static final Target TELEFONO = prueba("teléfono", "phone");
    public static final Target CORREO = prueba("correo", "email");
    public static final Target CLAVE = prueba("contraseña", "password");
    public static final Target REGISTRAR = prueba("registrar usuario", "register-submit");
    public static final Target INGRESAR = prueba("iniciar sesión", "login-submit");
    public static final Target ERROR_LOGIN = prueba("error de autenticación", "login-error");
    public static final Target ERROR_REGISTRO = prueba("error de registro", "register-error");
    public static final Target FACTURAS_CUENTA = prueba("facturas de la cuenta autenticada", "nav-invoices");
    public static final Target BUSQUEDA_POSTAL = prueba("búsqueda de dirección pendiente", "postcode-lookup-loading");
}
