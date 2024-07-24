package com.generation.progetto_finale.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.progetto_finale.dto.ProductDTO;
import com.generation.progetto_finale.modelEntity.Product;

@Service
public class ProductService 
{
    private ProductMapper mapper = ProductMapper.INSTANCE;

    public Product toEntity(ProductDTO dto)
    {
        return mapper.toEntity(dto);
    }

    public List<Product> toEntity(List<ProductDTO> dtos)
    {
        List<Product> res = new ArrayList<>();

        for(ProductDTO dto:dtos)
            res.add(mapper.toEntity(dto));

        return res;
    }

    public ProductDTO toDTO(Product p)
    {
        return mapper.toDTO(p);
    }

    public List<ProductDTO> toDTO(List<Product> products)
    {
        List<ProductDTO> res = new ArrayList<>();

        for(Product p:products)
            res.add(mapper.toDTO(p));

        return res;
    }
}
