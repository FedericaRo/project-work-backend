package com.generation.progetto_finale.dto;

import com.generation.progetto_finale.modelEntity.Frequency;
import com.generation.progetto_finale.modelEntity.Task.TaskStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskDTO 
{
    private Integer id;
    private String name;
    private String description;
    private Frequency frequency;
    private String creationDate;
    private String completionDate;
    private TaskStatus status;
    private String signature;
}
