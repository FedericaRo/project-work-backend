package com.generation.progetto_finale.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO
{

    protected Integer id;
    protected String productName;
    protected Double unitPrice;
    protected String unitType;
    protected Integer unitTypeQuantity;
    protected String packagingType;
    protected Integer packagingTypeQuantity;
    protected Integer unitsPerPackaging;
    protected String categoryName;
    protected String supplierName;
    protected Integer reorderPoint;
    protected String code;

}


