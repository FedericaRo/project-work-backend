package com.generation.progetto_finale.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.generation.progetto_finale.dto.TaskDTO;
import com.generation.progetto_finale.modelEntity.Task;


@Mapper
public interface TaskMapper 
{
    public static final TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);


        @Mapping(source = "frequency", target = "frequency")
        @Mapping(source = "status", target = "status")
        @Mapping(source = "creationDate", target = "creationDate", dateFormat = "yyyy-MM-dd")
        @Mapping(source = "completionDate", target = "completionDate", dateFormat = "yyyy-MM-dd")
        TaskDTO toDTO(Task t);


        @Mapping(target = "frequency")
        @Mapping(target = "status")
        @Mapping(source = "creationDate", target = "creationDate", dateFormat = "yyyy-MM-dd")
        @Mapping(source = "completionDate", target = "completionDate", dateFormat = "yyyy-MM-dd")
        Task toEntity(TaskDTO dto);

    

}
