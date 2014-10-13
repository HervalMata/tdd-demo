package com.garciahurtado.pillardemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garciahurtado.pillardemo.model.AdModel;

@Repository
public interface AdRepository extends JpaRepository<AdModel, Long> {


}
