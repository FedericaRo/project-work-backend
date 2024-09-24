package com.generation.progetto_finale.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.progetto_finale.modelEntity.Frequency;
import com.generation.progetto_finale.modelEntity.StoredTask;

public interface StoredTaskRepository extends JpaRepository<StoredTask, Integer> 
{

    public List<StoredTask> findAllByFrequency(Frequency frequency);

}
