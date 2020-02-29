/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.service.implementation;

import com.project.raider.dao.Company;
import com.project.raider.dto.GameDetailsDto;
import com.project.raider.exception.ApiException;
import com.project.raider.repository.CompanyRepository;
import com.project.raider.service.CompanyService;
import com.project.raider.service.GameCompanyService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sava
 */
@Service
public class CompanyServiceImp implements CompanyService{
    
    @Autowired
    private final CompanyRepository companyRepository;
    
    @Autowired
    private final GameCompanyService gameCompanyService;
    
    public CompanyServiceImp(CompanyRepository companyRepository, 
            GameCompanyService gameCompanyService) {
        this.companyRepository = companyRepository;
        this.gameCompanyService = gameCompanyService;
    }

    @Override
    public List<GameDetailsDto> getAllCompanies() {
        List<Company> languageList = companyRepository.findAll();
        List<GameDetailsDto> gameDetailsDtoList = new ArrayList<>();
        languageList.stream().forEach(l -> gameDetailsDtoList.add(new GameDetailsDto(l.getId(), l.getName())));
        return gameDetailsDtoList;
    }

    @Override
    public GameDetailsDto getById(long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ApiException("Company not found.", HttpStatus.NOT_FOUND));
        return new GameDetailsDto(company.getId(), company.getName());
    }

    @Override
    public GameDetailsDto save(GameDetailsDto companyDto) {
        long id = companyDto.getId();
        String name = companyDto.getName();
        Company company = new Company(name);
        if(companyRepository.existsById(id)) {
            company.setId(id);
        } 
        else if(companyRepository.existsByName(name)) {
            throw new ApiException("Company with name already exists.", HttpStatus.BAD_REQUEST);
        }
        Company newCompany = companyRepository.save(company);
        companyDto.setId(newCompany.getId());
        return companyDto;
    }

    @Override
    public boolean existsByName(String name) {
        return companyRepository.existsByName(name);
    }

    @Override
    public void delete(long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ApiException("Company not found.", HttpStatus.BAD_REQUEST));
        gameCompanyService.findByCompanyId(companyId).stream()
                .forEach((gc) -> gameCompanyService.delete(gc.getId()));
        companyRepository.delete(company);
    }
    
}
