package com.wise.consumeronboarding.test;

import com.wise.consumeronboarding.Application;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@AutoConfigureObservability
@ContextConfiguration(initializers = TestInitializer.class)
@SpringBootTest(classes = {Application.class}, webEnvironment =
    SpringBootTest.WebEnvironment.RANDOM_PORT)
public @interface BaseTestEnvironment {
}
