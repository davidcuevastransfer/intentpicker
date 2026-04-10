package com.wise.consumeronboarding.intentpicker.domain.model;


import java.math.BigDecimal;
import java.time.LocalDate;

public record AssetInterestRate(
    BigDecimal interestRate,
    LocalDate yieldDate) { }
