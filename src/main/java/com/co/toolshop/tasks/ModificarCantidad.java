package com.co.toolshop.tasks;

import com.co.toolshop.interactions.ChangeQuantity;
import com.co.toolshop.models.ProductDTO;
import com.co.toolshop.userinterfaces.CartPage;
import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.utils.Amounts;
import com.co.toolshop.utils.Waits;
import java.util.ArrayList;
import java.util.List;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ModificarCantidad implements Task {
    private final int cantidad;
    public ModificarCantidad(int cantidad) { this.cantidad = cantidad; }
    public static ModificarCantidad delPrimerProductoA(int cantidad) {
        return instrumented(ModificarCantidad.class, cantidad);
    }
    @Override
    @Step("{0} modifica la cantidad del primer producto a #cantidad y recalcula la compra")
    public <T extends Actor> void performAs(T actor) {
        List<ProductDTO> productos = new ArrayList<>(actor.<List<ProductDTO>>recall(ActorMemory.PRODUCTS));
        ProductDTO primero = productos.getFirst();
        ProductDTO actualizado = primero.withQuantity(cantidad);
        String totalAnterior = Waits.visible(actor, CartPage.LBL_TOTAL).getText();
        actor.attemptsTo(ChangeQuantity.ofProduct(primero.name()).to(cantidad));
        productos.set(0, actualizado);
        actor.remember(ActorMemory.PRODUCTS, List.copyOf(productos));
        actor.remember(ActorMemory.EXPECTED_TOTAL, Amounts.total(productos));
        Waits.until(actor, "Debe actualizarse el total tras cambiar la cantidad", d ->
                !d.findElement(Waits.by(CartPage.LBL_TOTAL)).getText().equals(totalAnterior));
    }
}
