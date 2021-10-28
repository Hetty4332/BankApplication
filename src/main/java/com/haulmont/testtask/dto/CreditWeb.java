package com.haulmont.testtask.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreditWeb {
    private Long id;
    @Min(value = 0, message = "Не должно быть пустым")
    private Long idBank;
    @NotNull
    @Min(value = 0, message = "Значение не должно быть отрицательным")
    private Integer creditLimit;
    @NotNull
    @Min(value = 0, message = "Значение не должно быть отрицательным")
    private Double interestRate;
    @NotBlank
    private String bankName;
}
