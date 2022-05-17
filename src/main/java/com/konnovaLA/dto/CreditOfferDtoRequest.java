package com.konnovaLA.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreditOfferDtoRequest {
    private Long id;
    @Min(value = 0, message = "Не должно быть пустым")
    private Long clientId;
    @Min(value=0, message = "Не должно быть пустым")
    private Long creditId;
    @NotNull
    @Min(value=0, message = "Значение не должно быть отрицательным")
    private Integer sumCredit;
    @NotNull
    @Min(value=0, message = "Значение не должно быть отрицательным")
    private Integer countMonthCredit;
}