/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Company;
import com.project.raider.dao.Game;
import com.project.raider.dao.GameCompany;
import com.project.raider.dto.GameGameDetailsDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.CompanyRepository;
import com.project.raider.repository.GameCompanyRepository;
import com.project.raider.repository.GameRepository;
import com.project.raider.service.GameCompanyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sava
 */
@Service
public class GameCompanyServiceImp implements GameCompanyService {

    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    private final CompanyRepository companyRepository;

    @Autowired
    private final GameCompanyRepository gameCompanyRepository;

    public GameCompanyServiceImp(GameRepository gameRepository,
            CompanyRepository companyRepository,
            GameCompanyRepository gameCompanyRepository) {
        this.gameRepository = gameRepository;
        this.companyRepository = companyRepository;
        this.gameCompanyRepository = gameCompanyRepository;
    }

    @Override
    public GameGameDetailsDto save(GameGameDetailsDto gameCompany) {
        Game game = gameRepository.findById(gameCompany.getGameId())
                .orElseThrow(() -> new ApiException("Game not found.", HttpStatus.NOT_FOUND));
        Company company = companyRepository.findById(gameCompany.getGameDetailsId())
                .orElseThrow(() -> new ApiException("Company not found."));
        gameCompanyRepository.save(new GameCompany(game, company));
        return gameCompany;
    }

    @Override
    public void delete(long id) {
        GameCompany gameCompany = gameCompanyRepository.findById(id)
                .orElseThrow(() -> new ApiException("Game Company not found.", HttpStatus.NOT_FOUND));
        gameCompanyRepository.delete(gameCompany);
    }

    @Override
    public GameCompany findById(long id) {
        GameCompany gameCompany = gameCompanyRepository.findById(id)
                .orElseThrow(() -> new ApiException("Game Company not found.", HttpStatus.NOT_FOUND));
        return gameCompany;
    }

    @Override
    public List<GameCompany> findByGameId(long gameId) {
        return gameCompanyRepository.findByGameId(gameId);
    }

    @Override
    public List<GameCompany> findByCompanyId(long companyId) {
        return gameCompanyRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean existsByGameIdAndCompanyId(long gameId, long companyId) {
        return gameCompanyRepository.existsByGameIdAndCompanyId(gameId, companyId);
    }

}
