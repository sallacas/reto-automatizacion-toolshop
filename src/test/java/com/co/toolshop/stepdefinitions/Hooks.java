package com.co.toolshop.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.*;
import java.time.Duration;

public class Hooks {
    @Before
    public void prepararActor() {
        OnStage.setTheStage(new OnlineCast());
        var navegador = Serenity.getDriver();
        navegador.manage().timeouts().implicitlyWait(Duration.ZERO);
        OnStage.theActorCalled("Cliente de Toolshop").can(BrowseTheWeb.with(navegador));
    }
    @After
    public void cerrarEscenario() { OnStage.drawTheCurtain(); }
}
