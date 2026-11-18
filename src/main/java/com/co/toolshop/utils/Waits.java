package com.co.toolshop.utils;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class Waits {
    private Waits() { }
    public static WebDriver driver(Actor actor) { return BrowseTheWeb.as(actor).getDriver(); }
    public static By by(Target objetivo) { return By.cssSelector(objetivo.getCssOrXPathSelector()); }
    public static <T> T until(Actor actor, String descripcion, Function<WebDriver, T> condicion) {
        return new WebDriverWait(driver(actor), Duration.ofSeconds(Long.getLong("espera.segundos", 30L)))
                .ignoring(StaleElementReferenceException.class)
                .withMessage(descripcion).until(condicion);
    }
    public static WebElement visible(Actor actor, Target objetivo) {
        return until(actor, "Debe estar visible: " + objetivo.getName(), ExpectedConditions.visibilityOfElementLocated(by(objetivo)));
    }
    public static WebElement clickable(Actor actor, Target objetivo) {
        return until(actor, "Debe estar habilitado: " + objetivo.getName(), ExpectedConditions.elementToBeClickable(by(objetivo)));
    }
    public static List<WebElement> all(Actor actor, Target objetivo) { return driver(actor).findElements(by(objetivo)); }
    public static boolean present(Actor actor, Target objetivo) {
        return all(actor, objetivo).stream().anyMatch(WebElement::isDisplayed);
    }
    public static WebElement within(WebElement contenedor, Target objetivo) { return contenedor.findElement(by(objetivo)); }
}
