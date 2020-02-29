/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.Company;
import com.project.raider.dao.Genre;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sava
 */
public interface GenreRepository extends JpaRepository<Genre, Long>{
    Optional<Genre> findByName(String name);
    boolean existsByName(String name);
}
