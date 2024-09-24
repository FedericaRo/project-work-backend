package com.generation.progetto_finale.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.generation.progetto_finale.dto.CommunicationDTO;
import com.generation.progetto_finale.modelEntity.Communication;

@Mapper
public interface CommunicationMapper 
{
    public static final CommunicationMapper INSTANCE = Mappers.getMapper(CommunicationMapper.class);

    @Mapping(source = "importance", target= "importance")
    @Mapping(source = "type", target = "type")
    CommunicationDTO toDTO(Communication c);

    @Mapping(target = "importance")
    @Mapping(target = "type")
    Communication toEntity(CommunicationDTO dto);
    
}
