package com.konnovaLA.mappers.mapperInterface;

import com.konnovaLA.entities.CreditOfferDto;
import com.konnovaLA.model.CreditOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditOfferMapper {

    @Mapping(target = "countMonthCredit", ignore = true)
    CreditOfferDto creditOfferToDto(CreditOffer creditOffer);

    }
