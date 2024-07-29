package com.generation.progetto_finale.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.generation.progetto_finale.dto.CommunicationDTO;
import com.generation.progetto_finale.modelEntity.Communication;

@Mapper
public interface CommunicationMapper 
{
    public static final CommunicationMapper INSTANCE = Mappers.getMapper(CommunicationMapper.class);

    
    CommunicationDTO toDTO(Communication c);

    Communication toEntity(CommunicationDTO dto);
    
}
