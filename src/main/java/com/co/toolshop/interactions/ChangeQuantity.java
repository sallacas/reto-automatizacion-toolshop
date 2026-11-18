package com.co.toolshop.interactions;

import com.co.toolshop.userinterfaces.CartPage;
import com.co.toolshop.utils.Waits;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.Keys;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ChangeQuantity implements Interaction {
    private final String productName;
    private final int quantity;
    public ChangeQuantity(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }
    public static Product ofProduct(String productName) { return new Product(productName); }
    public record Product(String name) {
        public ChangeQuantity to(int quantity) { return instrumented(ChangeQuantity.class, name, quantity); }
    }
    @Step("{0} cambia la cantidad de #productName a #quantity")
    public <T extends Actor> void performAs(T actor) {
        var fila = Waits.all(actor, CartPage.LST_ROWS).stream()
                .filter(e -> Waits.within(e, CartPage.LBL_PRODUCT_NAME).getText().trim().equals(productName))
                .findFirst().orElseThrow(() -> new IllegalStateException("No se encontró el producto: " + productName));
        Waits.within(fila, CartPage.TXT_QUANTITY).sendKeys(Keys.chord(Keys.CONTROL, "a"),
                Integer.toString(quantity), Keys.TAB);
    }
}
