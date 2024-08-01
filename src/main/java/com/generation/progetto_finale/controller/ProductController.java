package com.generation.progetto_finale.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.dto.ProductDTO;
import com.generation.progetto_finale.dto.mappers.ProductService;
import com.generation.progetto_finale.modelEntity.Category;
import com.generation.progetto_finale.modelEntity.Product;
import com.generation.progetto_finale.modelEntity.Supplier;
import com.generation.progetto_finale.repositories.CategoryRepository;
import com.generation.progetto_finale.repositories.ProductRepository;
import com.generation.progetto_finale.repositories.SupplierRepository;

@RestController
@RequestMapping("/products")
public class ProductController 
{
    @Autowired
    ProductRepository pRepo;
    @Autowired
    ProductService pServ;
    @Autowired
    SupplierRepository sRepo;
    @Autowired
    CategoryRepository cRepo;

    @GetMapping
    public List<ProductDTO> getAll()
    {
        return pServ.toDTO(pRepo.findAll());
    }

    
    @PostMapping("/newProduct")
    public ProductDTO addNewProduct(@RequestBody ProductDTO dto) {

        System.out.println(dto);
        Product product = pServ.toEntity(dto);

        Supplier supplier = sRepo.findByNameAndCode(dto.getSupplierName(), dto.getSupplierCode());
        Category category = cRepo.findByName(dto.getCategoryName());
        double roundedValue = Math.round(dto.getUnitPrice() * 100.0) / 100.0;

        product.setUnitPrice(roundedValue);
        product.setCategory(category);
        product.setSupplier(supplier);

        product = pRepo.save(product);

        return pServ.toDTO(product);
    }
}
