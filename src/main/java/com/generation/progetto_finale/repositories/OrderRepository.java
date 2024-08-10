package com.generation.progetto_finale.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.progetto_finale.modelEntity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> 
{
    public List<Order> findAllByOrderDateBetween(LocalDate start, LocalDate end);

    public List<Order> findAllByProductId(Integer productId);
}
