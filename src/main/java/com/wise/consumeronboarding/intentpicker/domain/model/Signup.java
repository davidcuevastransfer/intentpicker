package com.wise.consumeronboarding.intentpicker.domain.model;

import java.time.Instant;

public record Signup (
    long userId,
    AccessInviteId accessInviteId,
    Instant createdAt
){
}