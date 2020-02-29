/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service;

import com.project.raider.dto.GameDetailsDto;
import java.util.List;

/**
 *
 * @author Sava
 */
public interface CompanyService {
    List<GameDetailsDto> getAllCompanies();
    GameDetailsDto getById(long id);
    GameDetailsDto save(GameDetailsDto company);
    boolean existsByName(String name);
    void delete(long companyId);
}
