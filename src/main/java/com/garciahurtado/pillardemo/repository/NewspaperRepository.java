package com.garciahurtado.pillardemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.model.NewspaperModel;

@Repository
public interface NewspaperRepository extends JpaRepository<NewspaperModel, Long> {


}
