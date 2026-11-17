package co.reto.toolshop.ui;

import net.serenitybdd.screenplay.targets.Target;
import static co.reto.toolshop.ui.Elementos.*;

public final class Pago {
    private Pago() { }
    public static final Target CONTINUAR_DIRECCION = prueba("continuar desde facturación", "proceed-3");
    public static final Target METODO = prueba("método de pago", "payment-method");
    public static final Target BANCO = prueba("banco ficticio", "bank_name");
    public static final Target TITULAR = prueba("titular ficticio", "account_name");
    public static final Target CUENTA = prueba("cuenta ficticia", "account_number");
    public static final Target FINALIZAR = prueba("validar o confirmar pago", "finish");
    public static final Target PAGO_VALIDADO = prueba("pago validado", "payment-success-message");
    public static final Target CONFIRMACION = css("confirmación de la compra", "#order-confirmation");
}
