package com.konnovaLA.mappers;

import com.konnovaLA.entities.request.CreditOfferRequest;
import com.konnovaLA.model.CreditOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditOfferMapper {

    @Mapping(target = "countMonthCredit", ignore = true)
    CreditOfferRequest creditOfferToDto(CreditOffer creditOffer);

}
