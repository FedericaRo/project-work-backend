package com.generation.progetto_finale.modelEntity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product 
{   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*Nome prodotto
     */
    private String productName;

    /**
     * Prezzo unitario del prodotto
     */
    private Double unitPrice;

    /**
     * Tipologia unità (PZ,KG)
     */
    private String unitType;

    /**
     * Numero di pezzi SINGOLI in magazzino
     */
    private Integer unitTypeQuantity;

    /**
     * Identificativo di comunicazione tra azienda e e fornitore
     */
    private String code;

    /**
     * Tipologia packaging (CT,CON,MACCHINA,CAR)
     */
    private String packagingType;

    /**
     * Numero di packaging in magazzino
     */
    private Integer packagingTypeQuantity;

    /**
     * Numero di unità contenute in un packaging
     */
    private Integer unitsPerPackaging;

    /**
     * Livello minimo sotto cui le scorte devo essere riordinate
     */
    private Integer reorderPoint;

    // /**
    //  * Disponibilità in magazzino
    //  */
    // private Integer availability;

    @ManyToOne(fetch = FetchType.EAGER) //eager=appena carica categoria, carica anche i figli
    @JoinColumn(name = "category_id", nullable = true) //name= nome della colonna del db che fungerà da chiave esterna--> product_id
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = true)
    private Supplier supplier;

    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL)
    private List<Order> orders;
}
