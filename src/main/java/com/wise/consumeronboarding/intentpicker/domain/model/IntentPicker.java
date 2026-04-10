package com.wise.consumeronboarding.intentpicker.domain.model;

import java.util.List;

public record IntentPicker(
    List<Intent> intents,
    AssetInterestRate interestRate,
    IntentPickerUser intentPickerUser
) {
}
