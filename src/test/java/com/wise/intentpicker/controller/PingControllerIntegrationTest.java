package com.wise.intentpicker.controller;

import com.wise.intentpicker.test.BaseTestEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@BaseTestEnvironment
class PingControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void pingEndpointReturnsOk() {
    webTestClient.get()
        .uri("/api/v1/ping")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.status").isEqualTo("ok");
  }
}
