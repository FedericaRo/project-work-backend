package com.generation.progetto_finale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.progetto_finale.modelEntity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> 
{

}
