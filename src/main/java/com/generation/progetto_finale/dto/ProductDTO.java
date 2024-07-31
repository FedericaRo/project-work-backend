package com.generation.progetto_finale.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO 
{
    private Integer id, category_id, supplier_id;
    private String productName;
    private Double unitPrice;
    private String unitType;
    private Integer unitTypeQuantity;
    private String packagingType;
    private Integer packagingTypeQuantity;
    private Integer unitsPerPackaging;
    private String categoryName;
    private String supplierName, supplierCode;


}
