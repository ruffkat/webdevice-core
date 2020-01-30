package io.webdevice.cucumber.provided.configured;

import io.cucumber.java8.En;
import io.webdevice.wiring.EnableWebDevice;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class ConfigurationStep
        implements En {

    @Configuration
    @EnableWebDevice(settings = "io/webdevice/cucumber/provided/configured/webdevice.yaml")
    public static class TestConfiguration {
    }
}
