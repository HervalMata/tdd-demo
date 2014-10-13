package com.garciahurtado.pillardemo.service;

import java.util.List;

import com.garciahurtado.pillardemo.model.NewspaperModel;

public interface NewspaperService {
    public NewspaperModel create(NewspaperModel shop);
    public NewspaperModel delete(Long id) throws Exception; // TODO: add Custom EntityNotFoundException
    public List<NewspaperModel> findAll();
    public NewspaperModel update(NewspaperModel shop) throws Exception;
    public NewspaperModel findById(Long id);
}
