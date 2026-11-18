package com.co.toolshop.tasks;

import net.serenitybdd.screenplay.actions.*;
import com.co.toolshop.utils.ActorMemory;
import com.co.toolshop.interactions.*;
import com.co.toolshop.models.*;
import com.co.toolshop.questions.*;
import com.co.toolshop.questions.ConfirmedInvoiceNumber;
import com.co.toolshop.userinterfaces.*;
import com.co.toolshop.utils.Waits;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.*;
import org.openqa.selenium.Keys;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CompletarCompra implements Task {
    public static CompletarCompra porTransferenciaBancaria() { return instrumented(CompletarCompra.class); }
    @Step("{0} completa facturación y transferencia bancaria ficticia")
    public <T extends Actor> void performAs(T actor) {
        NewUserDTO usuario = actor.recall(ActorMemory.USER);
        actor.remember(ActorMemory.PURCHASE_TOTAL, actor.asksFor(CartTotal.value()));
        actor.attemptsTo(Click.on(CartPage.BTN_CONTINUE).afterWaitingUntilEnabled(), Click.on(CartPage.BTN_CONTINUE_AS_CUSTOMER).afterWaitingUntilEnabled());
        // La cuenta aporta la dirección; el número no se guarda en el perfil y se completa aquí.
        Waits.until(actor, "Debe cargarse la dirección de la cuenta", d ->
                !Waits.visible(actor, CheckoutPage.TXT_STREET).getAttribute("value").isBlank());
        // El servicio puede devolver la misma calle del perfil. Un valor sintético único
        // permite observar la respuesta, incluso si el domicilio autocompletado se repite.
        actor.attemptsTo(Write.theValue(usuario.street()).into(CheckoutPage.TXT_STREET), Write.theValue(usuario.houseNumber()).into(CheckoutPage.TXT_HOUSE_NUMBER));
        // Esperar una respuesta postal observable antes de continuar, sin pausas fijas.
        Waits.until(actor, "Debe finalizar la búsqueda postal de facturación", d ->
                !Waits.present(actor, CheckoutPage.LBL_ADDRESS_LOOKUP_LOADING)
                        && !Waits.visible(actor, CheckoutPage.TXT_STREET).getAttribute("value").equals(usuario.street()));
        // La API valida ciudad y estado contra el servicio postal. Se conservan los
        // valores autocompletados y se captura la dirección realmente enviada.
        actor.attemptsTo(Write.theValue(usuario.street()).into(CheckoutPage.TXT_STREET));
        actor.remember(ActorMemory.BILLING_ADDRESS, new BillingAddressDTO(usuario.country(), usuario.postalCode(),
                usuario.street(), Waits.visible(actor, CheckoutPage.TXT_CITY).getAttribute("value"),
                Waits.visible(actor, CheckoutPage.TXT_STATE).getAttribute("value")));
        actor.attemptsTo(Click.on(CheckoutPage.BTN_CONTINUE_TO_PAYMENT).afterWaitingUntilEnabled());
        actor.attemptsTo(SelectFromOptions.byValue("bank-transfer").from(CheckoutPage.SELECT_PAYMENT_METHOD));
        actor.attemptsTo(Enter.theValue("Banco de Pruebas").into(CheckoutPage.TXT_BANK_NAME).thenHit(Keys.TAB),
                Enter.theValue(usuario.name() + " " + usuario.lastName()).into(CheckoutPage.TXT_ACCOUNT_HOLDER).thenHit(Keys.TAB),
                Enter.theValue(usuario.phone()).into(CheckoutPage.TXT_ACCOUNT_NUMBER).thenHit(Keys.TAB),
                Click.on(CheckoutPage.BTN_FINISH).afterWaitingUntilEnabled());
        Waits.visible(actor, CheckoutPage.LBL_PAYMENT_SUCCESS);
        actor.attemptsTo(Click.on(CheckoutPage.BTN_FINISH).afterWaitingUntilEnabled());
        actor.remember(ActorMemory.INVOICE_NUMBER, actor.asksFor(ConfirmedInvoiceNumber.fromPurchase()));
    }
}
