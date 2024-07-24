package com.generation.progetto_finale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.progetto_finale.modelEntity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> 
{
    
}
