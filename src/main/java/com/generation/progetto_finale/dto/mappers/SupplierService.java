package com.generation.progetto_finale.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.progetto_finale.dto.SupplierDTO;
import com.generation.progetto_finale.modelEntity.Supplier;


@Service
public class SupplierService 
{
private SupplierMapper mapper = SupplierMapper.INSTANCE;

    public Supplier toEntity(SupplierDTO dto)
    {
        return mapper.toEntity(dto);
    }

    public List<Supplier> toEntity(List<SupplierDTO> dtos)
    {
        List<Supplier> res = new ArrayList<>();

        for(SupplierDTO dto:dtos)
            res.add(mapper.toEntity(dto));

        return res;
    }

    public SupplierDTO toDTO(Supplier p)
    {
        return mapper.toDTO(p);
    }

    public List<SupplierDTO> toDTO(List<Supplier> products)
    {
        List<SupplierDTO> res = new ArrayList<>();

        for(Supplier p:products)
            res.add(mapper.toDTO(p));

        return res;
    }
}
