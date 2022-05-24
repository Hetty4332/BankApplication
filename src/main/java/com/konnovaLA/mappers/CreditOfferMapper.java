package com.konnovaLA.mappers;

import com.konnovaLA.entities.CreditOfferDto;
import com.konnovaLA.model.CreditOffer;
import org.springframework.stereotype.Component;

@Component
public class CreditOfferMapper {
    public CreditOfferDto toDto(CreditOffer creditOffer) {
      return   CreditOfferDto.builder()
                .id(creditOffer.getId())
                .creditId(creditOffer.getCredit().getId())
                .clientId(creditOffer.getClient().getId())
                .sumCredit(creditOffer.getSumCredit())
                .build();//тут кол-ва месяцев нет
    }
}
