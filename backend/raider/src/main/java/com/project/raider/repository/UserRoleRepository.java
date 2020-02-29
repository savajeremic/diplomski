/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Sava
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    UserRole findByName(String roleName);
    boolean existsByName(String name);
}
