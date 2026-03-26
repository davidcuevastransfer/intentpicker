package com.wise.consumeronboarding.intentpicker.presentation;

import static com.transferwise.domainauthorization.interfaces.Permission.PROFILES_GET;

import java.util.Map;

import com.wise.common.security.Rule;
import com.wise.common.security.RulePolicy;
import com.wise.common.security.annotations.SecurityPolicy;
import com.wise.common.security.core.OrRule;
import com.wise.common.security.publicapi.PublicApiClientRule;
import com.wise.common.security.publicapi.ResourceType;
import com.wise.common.security.spiffe.ServiceRule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntentPickerController {

  @GetMapping("/api/v1/intent-picker")
  @SecurityPolicy(PublicRule.class)
  public IntentPickerResponse getIntentPicker() {
    return new IntentPickerResponse("id");
  }

  @GetMapping("/internal/v1/intent-picker")
  @SecurityPolicy(InternalRule.class)
  public IntentPickerResponse getIntentPicker(String blah) {
    return new IntentPickerResponse("id");
  }

  private static final class PublicRule implements RulePolicy {
    @Override
    public Rule getRule() {
      return PublicApiClientRule.builder()
          .allowRequestVia("hold-bff")
          .allowRequestVia("multi-currency-accounts")
          .requireDomainAuthorizationCheck(ResourceType.PROFILE, PROFILES_GET)
          .build();
    }
  }

  private static final class InternalRule implements RulePolicy {
    @Override
    public Rule getRule() {
      return OrRule.anyOf(
          ServiceRule.forService("twcard-management"),
          ServiceRule.forService("twcard-order"),
          ServiceRule.forService("launchpad")
      );
    }
  }
}

