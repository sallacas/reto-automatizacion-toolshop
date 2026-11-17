package co.reto.toolshop.ui;

import net.serenitybdd.screenplay.targets.Target;
import static co.reto.toolshop.ui.Elementos.*;

public final class Catalogo {
    private Catalogo() { }
    public static final Target PRODUCTOS = css("tarjetas de productos", "a.card[data-test^='product-']");
    public static final Target NOMBRE_TARJETA = prueba("nombre de tarjeta", "product-name");
    public static final Target PRECIO_TARJETA = prueba("precio de tarjeta", "product-price");
    public static final Target AGOTADO = prueba("producto agotado", "out-of-stock");
    public static final Target PROMOCION = prueba("precio promocional", "product-discount-price");
    public static final Target CO2_ACTIVO = css("calificación CO2 activa", "[data-test='co2-rating-badge'] .active");
    public static final Target ORDEN = prueba("orden del catálogo", "sort");
    public static final Target SUBCATEGORIAS = css("subcategorías", "input[name='category_id'][data-test^='category-']");
    public static final Target SIGUIENTE = prueba("página siguiente", "pagination-next");
    public static final Target PAGINA_ACTUAL = css("página actual", "app-pagination li.active button");
    public static final Target SIN_RESULTADOS = prueba("sin resultados", "no-results");
    public static final Target NOMBRE_DETALLE = css("nombre del producto", "h1[data-test='product-name']");
    public static final Target PRECIO_DETALLE = prueba("precio unitario", "unit-price");
    public static final Target CATEGORIA_DETALLE = css("categoría real del producto", "[aria-label='category']");
    public static final Target CANTIDAD = prueba("cantidad a agregar", "quantity");
    public static final Target AGREGAR = prueba("añadir al carrito", "add-to-cart");
    public static final Target CONTADOR_CARRITO = prueba("contador del carrito", "cart-quantity");
    public static final Target NOTIFICACION = css("notificación de éxito", ".toast-success");
}
