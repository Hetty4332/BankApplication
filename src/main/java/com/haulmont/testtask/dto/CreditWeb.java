package com.haulmont.testtask.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreditWeb {
    private Long id;
    @Min(value = 0, message = "Не должно быть пустым")
    private Long idBank;
    @NotNull
    private Integer creditLimit;
    @NotNull
    private Double interestRate;
    private String bankName;
}
