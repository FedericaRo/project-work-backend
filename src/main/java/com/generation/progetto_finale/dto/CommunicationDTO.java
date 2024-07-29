package com.generation.progetto_finale.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommunicationDTO 
{
    private Integer id;

    private String fromPerson; 
    private String toPerson;
    private String importance;
    private String type;
    private String description;
    private LocalDate creationDate;
}
