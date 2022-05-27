package com.konnovaLA.mappers;

import com.konnovaLA.entities.request.ClientRequest;
import com.konnovaLA.model.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {

    Client dtoToClient (ClientRequest client);

}
