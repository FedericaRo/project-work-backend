package com.generation.progetto_finale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.progetto_finale.modelEntity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> 
{
    public Supplier findByNameAndCode(String name, String code);
}
