package com.generation.progetto_finale.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.progetto_finale.dto.CommunicationDTO;
import com.generation.progetto_finale.modelEntity.Communication;

@Service
public class CommunicationService 
{   
    private CommunicationMapper mapper = CommunicationMapper.INSTANCE;

    public Communication toEntity(CommunicationDTO dto)
    {
        return mapper.toEntity(dto);
    }

    public List<Communication> toEntity(List<CommunicationDTO> dtos)
    {
        List<Communication> res = new ArrayList<>();

        for (CommunicationDTO dto : dtos)
            res.add(mapper.toEntity(dto));
            
        return res;
    }

    public CommunicationDTO toDTO(Communication c)
    {
        return mapper.toDTO(c);
    }

    public List<CommunicationDTO> toDTO(List<Communication> communications)
    {
        List<CommunicationDTO> res = new ArrayList<>();

        for (Communication  c: communications) 
            res.add(mapper.toDTO(c));

        return res;
    }

    
}
