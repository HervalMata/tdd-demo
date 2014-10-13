package com.garciahurtado.pillardemo.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.garciahurtado.pillardemo.model.AdModel;

public interface AdService {
    public AdModel create(AdModel shop);
    public AdModel delete(Long id) throws Exception; // TODO: add Custom EntityNotFoundException
    public List<AdModel> findAll();
    public AdModel update(AdModel shop) throws Exception;
    public AdModel findById(Long id);
}
