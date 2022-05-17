package com.konnovaLA.mappers;

import com.konnovaLA.dto.CreditOfferDtoRequest;
import com.konnovaLA.model.CreditOffer;
import org.springframework.stereotype.Component;

@Component
public class CreditOfferMapper {
    public CreditOfferDtoRequest toDto(CreditOffer creditOffer) {
      return   CreditOfferDtoRequest.builder()
                .id(creditOffer.getId())
                .creditId(creditOffer.getCredit().getId())
                .clientId(creditOffer.getClient().getId())
                .sumCredit(creditOffer.getSumCredit())
                .build();//тут кол-ва месяцев нет
    }
}
