package io.webdevice.wiring;

public interface DeviceDefinitionTest {

    // No specified capabilities
    void shouldBuildDefinitionWithoutCapabilitiesAndWithoutConfidential();
    void shouldBuildDefinitionWithoutCapabilitiesAndWithConfidential();

    // Capabilities originating from options
    void shouldBuildDefinitionWithOptionsOnly();
    void shouldBuildDefinitionWithOptionsMergingCapabilities();
    void shouldBuildDefinitionWithOptionsMergingExtraCapabilities();
    void shouldBuildDefinitionWithOptionsMergingCapabilitiesAndExtraCapabilities();

    // Capabilities originating from DesiredCapabilities.xxx()
    void shouldBuildDefinitionWithDesiredOnly();
    void shouldBuildDefinitionWithDesiredMergingCapabilities();
    void shouldBuildDefinitionWithDesiredMergingExtraCapabilities();
    void shouldBuildDefinitionWithDesiredMergingCapabilitiesAndExtraCapabilities();

    // Capabilities originating from Map
    void shouldBuildDefinitionWithMapOnly();
    void shouldBuildDefinitionWithMapMergingExtraCapabilities();
}