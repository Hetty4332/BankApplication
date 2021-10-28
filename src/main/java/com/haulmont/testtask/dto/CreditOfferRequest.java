package com.haulmont.testtask.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreditOfferRequest {

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
