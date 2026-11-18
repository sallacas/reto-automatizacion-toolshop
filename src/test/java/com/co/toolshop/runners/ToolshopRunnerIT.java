package com.co.toolshop.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.co.toolshop.stepdefinitions",
        plugin = {"pretty"}, snippets = io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE)
public class ToolshopRunnerIT { }
