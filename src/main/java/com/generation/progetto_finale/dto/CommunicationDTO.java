package com.generation.progetto_finale.dto;

import java.time.LocalDate;

import com.generation.progetto_finale.modelEntity.Communication.CommunicationImportance;
import com.generation.progetto_finale.modelEntity.Communication.CommunicationType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommunicationDTO 
{
    private Integer id;

    private String communicationName;
    private String fromPerson; 
    private String toPerson;
    private String description;
    private LocalDate creationDate;
    private CommunicationImportance importance;
    private CommunicationType type;
    private String pdfFilePath; // percorso del PDF

}
