package com.generation.progetto_finale.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import jakarta.persistence.EntityNotFoundException;


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
        
        Supplier supplier = sRepo.findByName(dto.getSupplierName());
        Category category = cRepo.findByName(dto.getCategoryName());
        double roundedValue = Math.round(dto.getUnitPrice() * 100.0) / 100.0;

        product.setUnitPrice(roundedValue);
        product.setCategory(category);
        product.setSupplier(supplier);

        product = pRepo.save(product);

        return pServ.toDTO(product);
    }

    @DeleteMapping("{productId}")
    public ProductDTO deleteProduct(@PathVariable Integer productId)
    {
        Optional<Product> productToDelete = pRepo.findById(productId);
        if (productToDelete.isEmpty()) 
            throw new EntityNotFoundException("Il prodotto non esiste");

        pRepo.delete(productToDelete.get());

        return pServ.toDTO(productToDelete.get());
    }
    
}
