package com.generation.progetto_finale.dto.mappers;


import com.generation.progetto_finale.modelEntity.Task.TaskStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompletionTaskDTO 
{

    private TaskStatus status;
    private String signature;
    

}
