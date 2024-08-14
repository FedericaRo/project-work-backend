package com.generation.progetto_finale.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.generation.progetto_finale.modelEntity.Profile;

public interface ProfileRepository extends JpaRepository<Profile,Integer>
{
    @Query("SELECT p FROM Profile p WHERE p.user.username = :username")
    List<Profile> findProfilesByUsername(@Param("username") String username);
}
