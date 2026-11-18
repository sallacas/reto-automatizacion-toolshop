package com.co.toolshop.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

public final class Elements {
    private Elements() { }
    public static Target test(String name, String attribute) {
        return css(name, "[data-test='" + attribute + "']");
    }
    public static Target css(String name, String selector) { return Target.the(name).locatedBy(selector); }
}
