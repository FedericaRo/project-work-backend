package com.generation.progetto_finale.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO
{
    private Integer id;
    // private Integer supplier_id;
    private String supplierName;
    private String supplierCode;
    
    // private Integer product_id;
    private String productName;

    private LocalDate orderDate;
    private LocalDate deliverDate;

    private Integer unitOrderedQuantity;
    private String unitType;
    
    private Integer packagingOrderedQuantity;
    private String packagingType;
    private boolean arrived;


    // private Integer unitDeliveredQuantity;
    // private Integer packagingDeliveredQuantity;




    // private LocalDate deliverDate;


    // private Integer category_id, supplier_id;

    // private String categoryName;
    // private String supplierName, supplierCode;

}
