/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.repository;

import com.project.raider.dao.Company;
import com.project.raider.dao.GameCompany;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sava
 */
public interface GameCompanyRepository extends JpaRepository<GameCompany, Long> {
    List<GameCompany> findByGameId(long gameId);
    List<GameCompany> findByCompanyId(long companyId);
    boolean existsByGameIdAndCompanyId(long gameId, long companyId);
}
