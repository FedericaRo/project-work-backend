package com.generation.progetto_finale.modelEntity;

import com.generation.progetto_finale.modelEntity.Task.Frequency;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StoredTask 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;

    private String description;

    /**
     * variabili di stato della frequenza, settimanale,bisettimanale,mensile
     */
    @Enumerated(EnumType.STRING)
    private Frequency frequency;


    public enum Frequency 
    {
        SETTIMANALE,
        BISETTIMANALE,
        MENSILE
    }

}


