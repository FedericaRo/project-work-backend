package com.generation.progetto_finale.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.generation.progetto_finale.dto.ProfileDTO;
import com.generation.progetto_finale.modelEntity.Profile;

@Mapper
public interface ProfileMapper 
{

    public static final ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(source = "user.username",target = "user")
    ProfileDTO toDTO(Profile c);

    @Mapping(target = "user"  , ignore = true)
    Profile toEntity(ProfileDTO dto);

}
