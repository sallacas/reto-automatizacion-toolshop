package co.reto.toolshop.ui;

import net.serenitybdd.screenplay.targets.Target;
import static co.reto.toolshop.ui.Elementos.*;

public final class Carrito {
    private Carrito() { }
    public static final Target FILAS = css("líneas del carrito", "app-cart tbody tr:has([data-test='product-title'])");
    public static final Target NOMBRE = prueba("nombre en carrito", "product-title");
    public static final Target PRECIO = prueba("precio en carrito", "product-price");
    public static final Target CANTIDAD = prueba("cantidad en carrito", "product-quantity");
    public static final Target SUBTOTAL = prueba("subtotal de producto", "line-price");
    public static final Target TOTAL = prueba("total del carrito", "cart-total");
    public static final Target CONTINUAR = prueba("continuar desde el carrito", "proceed-1");
    public static final Target CONTINUAR_SESION = prueba("continuar con la sesión iniciada", "proceed-2");
}
