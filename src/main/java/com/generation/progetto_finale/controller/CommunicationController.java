package com.generation.progetto_finale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.progetto_finale.dto.CommunicationDTO;
import com.generation.progetto_finale.dto.mappers.CommunicationService;
import com.generation.progetto_finale.repositories.CommunicationRepository;

@RestController
@RequestMapping("/communications")
public class CommunicationController 
{
    @Autowired
    CommunicationRepository cRepo;
    @Autowired
    CommunicationService cServ;

    @GetMapping
    public List<CommunicationDTO> getAll()
    {
        return cServ.toDTO(cRepo.findAll());
    }
}
