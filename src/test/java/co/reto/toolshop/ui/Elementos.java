package co.reto.toolshop.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class Elementos {
    private Elementos() { }
    public static Target prueba(String nombre, String atributo) {
        return css(nombre, "[data-test='" + atributo + "']");
    }
    public static Target css(String nombre, String selector) { return Target.the(nombre).locatedBy(selector); }
}
