/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dao.GameCompany;
import com.project.raider.dto.GameGameDetailsDto;
import java.util.List;

/**
 *
 * @author Sava
 */
public interface GameCompanyService {
    GameGameDetailsDto save(GameGameDetailsDto gameCompany);
    GameCompany findById(long id);
    List<GameCompany> findByGameId(long gameId);
    List<GameCompany> findByCompanyId(long companyId);
    boolean existsByGameIdAndCompanyId(long gameId, long companyId);
    void delete(long id);
}
