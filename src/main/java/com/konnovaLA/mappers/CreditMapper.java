package com.konnovaLA.mappers;

import com.konnovaLA.dto.CreditDtoRequest;
import com.konnovaLA.dto.CreditOfferDtoRequest;
import com.konnovaLA.model.Credit;
import org.springframework.stereotype.Component;

@Component
public class CreditMapper {
    public CreditDtoRequest toDto(Credit credit) {
        return   CreditDtoRequest.builder()
                .id(credit.getId())

                .build();//тут кол-ва месяцев нет
    }
    }
