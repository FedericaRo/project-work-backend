package com.generation.progetto_finale.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.dto.ProductDTO;
import com.generation.progetto_finale.dto.mappers.ProductService;
import com.generation.progetto_finale.repositories.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController 
{
    @Autowired
    ProductRepository pRepo;
    @Autowired
    ProductService pServ;

    @GetMapping
    public List<ProductDTO> getAll()
    {
        return pServ.toDTO(pRepo.findAll());
    }
}
