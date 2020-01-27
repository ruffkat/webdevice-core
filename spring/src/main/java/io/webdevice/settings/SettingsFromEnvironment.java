package io.webdevice.settings;

import io.bestquality.lang.CheckedSupplier;
import io.webdevice.support.AnnotationAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.springframework.util.ClassUtils.forName;
import static org.springframework.util.ClassUtils.isPresent;

/**
 * The {@link SettingsFromEnvironment} class uses an annotation-configurable
 * {@link SettingsBinder} to bind properties from the execution environment
 * onto the {@link Settings} used to configure the {@link io.webdevice.wiring.WebDeviceRuntime}
 * <p>
 * Applying the Strategy Design Pattern allows the {@link io.webdevice.wiring.WebDeviceRuntime}
 * to be used in both Spring Boot and traditional Spring contexts.
 */
public class SettingsFromEnvironment
        implements CheckedSupplier<Settings> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ConfigurableEnvironment environment;
    private final AnnotationAttributes attributes;

    public SettingsFromEnvironment(ConfigurableEnvironment environment,
                                   AnnotationAttributes attributes) {
        this.environment = environment;
        this.attributes = attributes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Settings get()
            throws Exception {
        SettingsBinder binder = attributes.valueOf("binder", Class.class,
                (impl) -> {
                    // Explicitly specified binders take precedence
                    return (impl != SettingsBinder.class)
                            ? (SettingsBinder) impl.getDeclaredConstructor().newInstance()
                            : null;
                },
                () -> {
                    // Attempt to load the preferred binder using Spring Boot.
                    if (isPresent("org.springframework.boot.context.properties.bind.Binder", null)) {
                        // Spring Boot is available, now check if the webdevice-spring-boot module is present
                        if (isPresent("io.webdevice.settings.ConfigurationPropertiesBinder", null)) {
                            return (SettingsBinder) forName("io.webdevice.settings.ConfigurationPropertiesBinder", null)
                                    .getDeclaredConstructor()
                                    .newInstance();
                        } else {
                            log.warn("Did you know that there is a Spring Boot module available? See https://webdevice.io for details!");
                        }
                    }
                    // Spring Boot is not available, load from Environment or use default
                    Class<?> binderClass = environment.getProperty("webdevice.binder", Class.class,
                            DefaultSettingsBinder.class);
                    return (SettingsBinder) binderClass
                            .getDeclaredConstructor()
                            .newInstance();
                });
        return binder.from(environment);
    }
}