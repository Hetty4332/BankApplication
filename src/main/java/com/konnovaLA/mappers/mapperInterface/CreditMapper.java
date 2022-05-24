package com.konnovaLA.mappers.mapperInterface;

import com.konnovaLA.entities.CreditDto;
import com.konnovaLA.model.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    @Mapping(target = "bankName", source = "bankName")
    CreditDto creditToDto(Credit credit, String bankName);

    Credit creditFromDto(CreditDto dto);

}
