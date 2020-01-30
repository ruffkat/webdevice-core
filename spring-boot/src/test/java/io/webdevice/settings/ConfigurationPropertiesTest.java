package io.webdevice.settings;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.ConfigurableConversionService;

public abstract class ConfigurationPropertiesTest
        extends BoundSettingsTest {

    @Override
    protected SettingsBinder makeSettingsBinder() {
        return new ConfigurationPropertiesBinder();
    }

    @Override
    protected ConfigurableConversionService makeConversionService() {
        return new ApplicationConversionService();
    }
}
