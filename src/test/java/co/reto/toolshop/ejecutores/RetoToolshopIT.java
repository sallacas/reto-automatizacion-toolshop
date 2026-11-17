package co.reto.toolshop.ejecutores;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/caracteristicas", glue = "co.reto.toolshop.pasos",
        plugin = {"pretty"}, snippets = io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE)
public class RetoToolshopIT { }
