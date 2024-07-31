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
    private LocalDate creationDate;

     /**
     * metodo chiamato al momento del salvataggio di una entità nel database
     */
    @PrePersist
    public void onCreate()
    {
        creationDate = LocalDate.now();    
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
}
