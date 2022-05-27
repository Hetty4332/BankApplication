package com.konnovaLA.mappers;

import com.konnovaLA.entities.request.CreditRequest;
import com.konnovaLA.model.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    @Mapping(target = "bankName", source = "bankName")
    CreditRequest creditToDto(Credit credit, String bankName);

    Credit creditFromDto(CreditRequest dto);

}
