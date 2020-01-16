package io.webdevice.support;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Set;

public class ProtectedWebDriver
        extends RemoteWebDriver {
    private final Set<String> confidential;

    public ProtectedWebDriver(URL remoteAddress, Capabilities capabilities, Set<String> confidential) {
        super(remoteAddress, capabilities);
        this.confidential = confidential;
    }

    public ProtectedWebDriver(CommandExecutor executor, Capabilities capabilities, Set<String> confidential) {
        super(executor, capabilities);
        this.confidential = confidential;
    }

    @Override
    protected void startSession(Capabilities capabilities) {
        super.startSession(capabilities);
        try {
            Field field = RemoteWebDriver.class.getDeclaredField("capabilities");
            field.setAccessible(true);
            field.set(this, new ProtectedCapabilities(getCapabilities(), confidential));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}