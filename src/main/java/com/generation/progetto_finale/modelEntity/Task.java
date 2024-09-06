package com.generation.progetto_finale.modelEntity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task 
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

    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;

    private LocalDate completionDate;

    /**
     * variabili di stato checked/unchecked
     */
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    /**
     * firma di chi ha eseguito la task
     */
    private String signature = "nessuna";

    /**
     * metodo chiamato al momento del salvataggio di una entità nel database
     */
    @PrePersist
    public void onCreate()
    {
        creationDate = LocalDate.now();    
    }

    /**
     * Metodo chiamato prima di aggiornare una entità nel database
     */
    
    public void onUpdate() 
    {
        if (getStatus().equals(TaskStatus.DAFARSI))
        {
            setCompletionDate(null);
        }
        /**
         * controllo se lo stato cambia da pending a completed
         */
        if (status == TaskStatus.COMPLETATO && completionDate == null) 
        {
            completionDate = LocalDate.now();
        }
    }

    /**
     * enum è per valori costanti, come variabili di stato
     */
    public enum TaskStatus 
    {
        DAFARSI,
        COMPLETATO,
        INCOMPIUTO
    }
    
    













}
