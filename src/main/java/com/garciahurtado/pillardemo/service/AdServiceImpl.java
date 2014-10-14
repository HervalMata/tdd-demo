package com.garciahurtado.pillardemo.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.model.NewspaperModel;
import com.garciahurtado.pillardemo.repository.AdRepository;
import com.garciahurtado.pillardemo.repository.NewspaperRepository;

@Service
public class AdServiceImpl implements AdService {

	@Resource
    private AdRepository adRepository;
	
	@Resource
	private NewspaperRepository newsRepository;

	@Override
	public AdModel create(AdModel ad) {
		// Save the newspapers first
		Collection<NewspaperModel> newspapers = ad.getNewspapers();
		for (NewspaperModel news : newspapers) {
			newsRepository.save(news);
		}
		
		AdModel model = adRepository.save(ad);
		return model;
	}

	@Override
	public AdModel findById(Long id) {
		return adRepository.findOne(id);
	}

	@Override
	public AdModel delete(Long id) throws Exception { // TODO: custom not found exception
		AdModel deletedAdModel = adRepository.findOne(id);

		if (deletedAdModel == null)
			throw new Exception("Ad Model not found");

		adRepository.delete(deletedAdModel);
		return deletedAdModel;
	}

	@Override
	public List<AdModel> findAll() {
		return adRepository.findAll();
	}

	@Override
	public AdModel update(AdModel ad) throws Exception { // TODO: custom not found exception
		AdModel updatedAd = adRepository.findOne(ad.getId());

		if (updatedAd == null)
			throw new Exception();

		return updatedAd;
	}

}
