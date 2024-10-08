package com.generation.progetto_finale.modelEntity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class Communication 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String communicationName;
    private String fromPerson; 
    private String toPerson;
    private String description;


    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationDate;

     /**
     * metodo chiamato al momento del salvataggio di una entità nel database
     */
    @PrePersist
    public void onCreate()
    {
        creationDate = LocalDateTime.now();    
    }


    /**
     * variabili di importanza
     */
    @Enumerated(EnumType.STRING)
    private CommunicationImportance importance;

    public enum CommunicationImportance
    {
        ALTA,     // Alta importanza
        MEDIA,   // Media importanza
        BASSA       // Bassa importanza
    }

    @Enumerated(EnumType.STRING)
    private CommunicationType type;

    public enum CommunicationType
    {
        AMMINISTRATIVA, //circolari, comunicazioni aziendali
        ORGANIZZATIVA,  //riunioni, organizzazione interna
        INFORMATIVA,    //informazione di carattere generico (compleanni, mezzi pubblici, tutto ciò che non riguarda il resto)
        CAMBIOTURNO
    }


    // percorso del file pdf
    private String pdfFilePath;
}

