package com.generation.progetto_finale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.progetto_finale.modelEntity.StoredTask;

public interface StoredTaskRepository extends JpaRepository<StoredTask, Integer> {

}
