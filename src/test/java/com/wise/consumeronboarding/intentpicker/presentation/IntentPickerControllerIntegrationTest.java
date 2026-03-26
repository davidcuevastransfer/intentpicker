package com.wise.consumeronboarding.intentpicker.presentation;

import com.wise.consumeronboarding.test.BaseTestEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@BaseTestEnvironment
class IntentPickerControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void pingEndpointReturnsOk() {
    webTestClient.get()
        .uri("/api/v1/ping")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.status")
        .isEqualTo("ok");
  }
}
