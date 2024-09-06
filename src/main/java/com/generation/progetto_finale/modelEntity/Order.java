package com.generation.progetto_finale.modelEntity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="orders")
public class Order 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer unitOrderedQuantity;

    private Integer packagingOrderedQuantity;

    private boolean arrived;

    @Column(name = "order_date", nullable = false, updatable = false)
    private LocalDate orderDate;
    private LocalDate deliverDate;

    @PrePersist   //questo metodo verrà richiamato in automatico quando salvo per la prima volta una entità
    public void onCreate()
    {
        orderDate         = LocalDate.now();
        arrived           = false;

    }
   
}
