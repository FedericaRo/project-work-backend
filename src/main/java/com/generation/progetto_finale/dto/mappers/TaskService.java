package com.generation.progetto_finale.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.progetto_finale.dto.TaskDTO;
import com.generation.progetto_finale.modelEntity.Task;

@Service
public class TaskService 
{
    private TaskMapper mapper = TaskMapper.INSTANCE;

    public Task toEntity(TaskDTO dto)
    {
        return mapper.toEntity(dto);
    }

    public List<Task> toEntity(List<TaskDTO> dtos)
    {
        List<Task> res = new ArrayList<>();
        
        for(TaskDTO dto:dtos)
            res.add(mapper.toEntity(dto));
        
        return res;
    }

    public TaskDTO toDTO(Task t)
    {
        return mapper.toDTO(t);
    }

    public List<TaskDTO> toDTO(List<Task> tasks)
    {
        List<TaskDTO> res = new ArrayList<>();

        for(Task t:tasks)
            res.add(mapper.toDTO(t));

        return res;
    }

}
