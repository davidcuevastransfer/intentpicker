package com.wise.consumeronboarding.test;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    // Reset OpenTelemetry state between tests if available
    try {
      Class<?> clazz = Class.forName("io.opentelemetry.api.GlobalOpenTelemetry");
      clazz.getMethod("resetForTest").invoke(null);
    } catch (Exception e) {
      // OpenTelemetry not on classpath, skip
    }
  }
}
