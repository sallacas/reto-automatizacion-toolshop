package co.reto.toolshop.utilidades;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class Esperas {
    private Esperas() { }
    public static WebDriver navegador(Actor actor) { return BrowseTheWeb.as(actor).getDriver(); }
    public static By por(Target objetivo) { return By.cssSelector(objetivo.getCssOrXPathSelector()); }
    public static <T> T hasta(Actor actor, String descripcion, Function<WebDriver, T> condicion) {
        return new WebDriverWait(navegador(actor), Duration.ofSeconds(Long.getLong("espera.segundos", 30L)))
                .ignoring(StaleElementReferenceException.class)
                .withMessage(descripcion).until(condicion);
    }
    public static WebElement visible(Actor actor, Target objetivo) {
        return hasta(actor, "Debe estar visible: " + objetivo.getName(), ExpectedConditions.visibilityOfElementLocated(por(objetivo)));
    }
    public static WebElement clicable(Actor actor, Target objetivo) {
        return hasta(actor, "Debe estar habilitado: " + objetivo.getName(), ExpectedConditions.elementToBeClickable(por(objetivo)));
    }
    public static List<WebElement> todos(Actor actor, Target objetivo) { return navegador(actor).findElements(por(objetivo)); }
    public static boolean presente(Actor actor, Target objetivo) {
        return todos(actor, objetivo).stream().anyMatch(WebElement::isDisplayed);
    }
    public static WebElement dentro(WebElement contenedor, Target objetivo) { return contenedor.findElement(por(objetivo)); }
}
