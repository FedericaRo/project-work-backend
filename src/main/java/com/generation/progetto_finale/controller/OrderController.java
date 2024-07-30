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
    public ResponseEntity<Map<String, Object>> changeArrivedStatus(@PathVariable Integer orderId, @RequestBody Map<String, Boolean> requestBody) 
    {
        boolean arrivedStatus = requestBody.get("arrivedStatus");
        System.out.println(arrivedStatus);
        Optional<Order> orderToChange = orRepo.findById(orderId);
        if (orderToChange.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "L'ordine non esiste");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Order order = orderToChange.get();
        System.out.println(order);
        order.setHasArrived(!arrivedStatus);
        orRepo.save(order);

        Map<String, Object> response = new HashMap<>();
        response.put("success", "Order status updated successfully");
        response.put("arrivedStatus", !arrivedStatus);

        return ResponseEntity.ok(response);
        // return new ResponseEntity<>("{\"success\": \"Order status updated successfully\"}", HttpStatus.OK);
    }


    @PutMapping("{orderId}/editPackagingQuantity")
    public ResponseEntity<String> editPackagingQuantity(@PathVariable Integer orderId, @RequestBody Map<String, Integer> requestBody)
    {
        Integer packagingOrderedQuantity = requestBody.get("packagingOrderedQuantity");
        System.out.println(packagingOrderedQuantity);
        Optional<Order> orderToChange = orRepo.findById(orderId);
        if (orderToChange.isEmpty())
            return new ResponseEntity<>("{\"error\": \"L'ordine non esiste\"}", HttpStatus.BAD_REQUEST);

            Order order = orderToChange.get();
            System.out.println(order.getProduct().getProductName());
        order.setPackagingOrderedQuantity(packagingOrderedQuantity);
        orRepo.save(order);

        return new ResponseEntity<>("{\"success\": \"Order packaging quantity updated successfully\"}", HttpStatus.OK);
    }

    @PutMapping("{orderId}/editUnitQuantity")
    public ResponseEntity<String> editUnitQuantity(@PathVariable Integer orderId, @RequestBody Map<String, Integer> requestBody) 
    {
        Integer unitOrderedQuantity = requestBody.get("unitOrderedQuantity");
        System.out.println(unitOrderedQuantity);
        Optional<Order> orderToChange = orRepo.findById(orderId);
        if (orderToChange.isEmpty()) 
            return new ResponseEntity<>("{\"error\": \"L'ordine non esiste\"}", HttpStatus.BAD_REQUEST);

        Order order = orderToChange.get();
        System.out.println(order.getProduct().getProductName());
        order.setUnitOrderedQuantity(unitOrderedQuantity);
        orRepo.save(order);

        return new ResponseEntity<>("{\"success\": \"Order unit quantity updated successfully\"}", HttpStatus.OK);
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Integer orderId)
    {
        Optional<Order> orderToDelete = orRepo.findById(orderId);
        if (orderToDelete.isEmpty()) 
        {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "L'ordine non esiste");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        orRepo.delete(orderToDelete.get());

        Map<String, Object> response = new HashMap<>();
        response.put("success", "Order status updated successfully");
        response.put("order", orServ.toDTO(orderToDelete.get()));

        return ResponseEntity.ok(response);

    }




}
