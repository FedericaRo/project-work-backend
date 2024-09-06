package com.generation.progetto_finale.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.progetto_finale.dto.OrderDTO;
import com.generation.progetto_finale.modelEntity.Order;

@Service
public class OrderService 
{

    private OrderMapper mapper = OrderMapper.INSTANCE;

    public Order toEntity(OrderDTO dto)
    {
        return mapper.toEntity(dto);
    }

    public List<Order> toEntity(List<OrderDTO> dtos)
    {
        List<Order> res = new ArrayList<>();

        for(OrderDTO dto:dtos)
            res.add(mapper.toEntity(dto));

        return res;
    }

    public OrderDTO toDTO(Order o)
    {
        return mapper.toDTO(o);
    }

    public List<OrderDTO> toDTO(List<Order> orders)
    {
        List<OrderDTO> res = new ArrayList<>();

        for(Order o:orders)
            res.add(mapper.toDTO(o));

        return res;
    }

}
