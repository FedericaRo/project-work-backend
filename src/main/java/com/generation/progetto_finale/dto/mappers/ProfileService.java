package com.generation.progetto_finale.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.progetto_finale.dto.ProfileDTO;
import com.generation.progetto_finale.modelEntity.Profile;

@Service
public class ProfileService 
{
    private ProfileMapper mapper = ProfileMapper.INSTANCE;

    public Profile toEntity(ProfileDTO dto)
    {
        return mapper.toEntity(dto);
    }

    public List<Profile> toEntity(List<ProfileDTO> dtos)
    {
        List<Profile> res = new ArrayList<>();

        for(ProfileDTO dto:dtos)
            res.add(mapper.toEntity(dto));

        return res;
    }

    public ProfileDTO toDTO(Profile p)
    {
        return mapper.toDTO(p);
    }

    public List<ProfileDTO> toDTO(List<Profile> profiles)
    {
        List<ProfileDTO> res = new ArrayList<>();

        for(Profile p:profiles)
            res.add(mapper.toDTO(p));

        return res;
    }
}
