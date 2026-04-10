package com.wise.consumeronboarding.intentpicker.domain.model;

public record IntentPickerUser(
    Country country
) {

   //?
   public boolean hasPendingInvite() {
     return true;
   }
}
