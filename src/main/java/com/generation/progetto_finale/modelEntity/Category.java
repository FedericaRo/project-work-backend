package com.generation.progetto_finale.modelEntity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy= "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
    //todo NON SAPPIAMO SE METTERE CascadeType.ALL, eager vediamo se tenerlo
    private List<Product> products;
}
