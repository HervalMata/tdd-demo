package com.garciahurtado.pillardemo.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.repository.AdRepository;

@Service
public class AdServiceImpl implements AdService {

	@Resource
    private AdRepository adRepository;

	@Override
	public AdModel create(AdModel ad) {
		return adRepository.save(ad);
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
