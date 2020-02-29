/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.GameFlag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sava
 */
public interface GameFlagRepository extends JpaRepository<GameFlag, Short>{
    GameFlag findByName(String flagName);
    boolean existsByName(String name);
}
