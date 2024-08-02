package com.generation.progetto_finale.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO
{

    // , category_id, supplier_id; //! Non siamo sicuri se abbia senso averceli sia qui che nel front
    protected Integer id;
    protected String productName;
    protected Double unitPrice;
    protected String unitType;
    protected Integer unitTypeQuantity;
    protected String packagingType;
    protected Integer packagingTypeQuantity;
    protected Integer unitsPerPackaging;
    protected String categoryName;
    protected String supplierName, supplierCode;
    protected Integer reorderPoint;
    protected String code;
}
