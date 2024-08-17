package com.generation.progetto_finale.modelEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generation.progetto_finale.auth.model.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_profiles")
public class Profile 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    
    private String imagePath;

    // @Lob
    // private byte[] fileContent;
    // @JsonIgnore
    /**
     * * Rimosso il cascade che stava bloccando l'eliminazione di un profilo, dato che stava
     * * tentando di eliminare anche il suo User ahahah
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;









}
