package com.generation.progetto_finale.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.dto.OrderDTO;
import com.generation.progetto_finale.dto.mappers.OrderService;
import com.generation.progetto_finale.modelEntity.Order;
import com.generation.progetto_finale.repositories.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/orders")
public class OrderController 
{
    @Autowired
    OrderRepository orRepo;

    @Autowired 
    OrderService orServ;


    @GetMapping
    public List<OrderDTO> getAll()
    {
        return orServ.toDTO(orRepo.findAll());
    }

    @PutMapping("{orderId}/changeArrivedStatus")
    public void changeArrivedStatus(@PathVariable Integer orderId, boolean arrivedStatus) 
    {
        System.out.println(arrivedStatus);
        Optional<Order> orderToChange = orRepo.findById(orderId);
        if (orderToChange.isEmpty())
            throw new EntityNotFoundException("L'ordine non esiste");

        Order order = orderToChange.get();
        System.out.println(order);
        order.setHasArrived(!arrivedStatus);
        orRepo.save(order);
    }


    @PutMapping("{orderId}/editPackagingQuantity")
    public void editPackagingQuantity(@PathVariable Integer orderId, @RequestBody Map<String, Integer> requestBody)
    {
        Integer packagingOrderedQuantity = requestBody.get("packagingOrderedQuantity");
        System.out.println(packagingOrderedQuantity);
        Optional<Order> orderToChange = orRepo.findById(orderId);
        if (orderToChange.isEmpty())
            throw new EntityNotFoundException("L'ordine non esiste");

        Order order = orderToChange.get();
        System.out.println(order.getProduct().getProductName());
        order.setPackagingOrderedQuantity(packagingOrderedQuantity);
        orRepo.save(order);
    }

    @PutMapping("{orderId}/editUnitQuantity")
    public void editUnitQuantity(@PathVariable Integer orderId, @RequestBody Map<String, Integer> requestBody) 
    {

        Integer unitOrderedQuantity = requestBody.get("unitOrderedQuantity");
        Optional<Order> orderToChange = orRepo.findById(orderId);
        if (orderToChange.isEmpty()) 
            throw new EntityNotFoundException("L'ordine non esiste");

        Order order = orderToChange.get();
        System.out.println(order.getProduct().getProductName());
        order.setUnitOrderedQuantity(unitOrderedQuantity);
        orRepo.save(order);

    }

    @DeleteMapping("{orderId}")
    public OrderDTO deleteOrder(@PathVariable Integer orderId)
    {
        Optional<Order> orderToDelete = orRepo.findById(orderId);
        if (orderToDelete.isEmpty()) 
            throw new EntityNotFoundException("L'ordine non esiste");

        orRepo.delete(orderToDelete.get());

        return orServ.toDTO(orderToDelete.get());
    }




}
