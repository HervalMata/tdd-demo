package com.garciahurtado.pillardemo.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.garciahurtado.pillardemo.model.AdModel;
import com.garciahurtado.pillardemo.model.NewspaperModel;
import com.garciahurtado.pillardemo.repository.AdRepository;
import com.garciahurtado.pillardemo.repository.NewspaperRepository;

@Service
public class NewspaperServiceImpl implements NewspaperService {

	@Resource
    private NewspaperRepository newspaperRepository;

	@Override
	public NewspaperModel create(NewspaperModel newspaper) {
		return newspaperRepository.save(newspaper);
	}

	@Override
	public NewspaperModel findById(Long id) {
		return newspaperRepository.findOne(id);
	}

	@Override
	public NewspaperModel delete(Long id) throws Exception { // TODO: custom not found exception
		NewspaperModel deletedModel = newspaperRepository.findOne(id);
		newspaperRepository.delete(deletedModel);
		return deletedModel;
	}

	@Override
	public List<NewspaperModel> findAll() {
		return newspaperRepository.findAll();
	}

	@Override
	public NewspaperModel update(NewspaperModel newspaper) throws Exception { // TODO: custom not found exception
		NewspaperModel updated = newspaperRepository.findOne(newspaper.getId());
		return updated;
	}

}
