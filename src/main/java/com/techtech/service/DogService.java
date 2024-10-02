package com.techtech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techtech.dao.DogRepository;
import com.techtech.dto.DogDTO;
import com.techtech.dto.PatchDTO;
import com.techtech.entity.DogEntity;
import com.techtech.exception.DogNotFoundException;

@Service
public class DogService {

	@Autowired
	private DogRepository dogRepository;

	public DogDTO save(DogDTO dogDTO) {
		DogEntity dogEntity = new DogEntity();
		BeanUtils.copyProperties(dogDTO, dogEntity);
		DogEntity entity = dogRepository.save(dogEntity);
		dogDTO.setDid(entity.getDid());
		return dogDTO;
	}
	
	public DogDTO findById(int did) {
		Optional<DogEntity> optional = dogRepository.findById(did);
		DogDTO dogDTO = new DogDTO();
		if(optional.isPresent()) {
			BeanUtils.copyProperties(optional.get(), dogDTO);
		}else {
			throw new DogNotFoundException("Hey! it seems like this dog does not exist!");
		}
		return dogDTO;
	}

	public List<DogDTO> findDogs() {
		List<DogDTO> results = new ArrayList<DogDTO>();
		List<DogEntity> dogEntityList = dogRepository.findAll();
		for (DogEntity entity : dogEntityList) {
			DogDTO dogDTO = new DogDTO();
			BeanUtils.copyProperties(entity, dogDTO);
			results.add(dogDTO);
		}
		return results;
	}

	public boolean deleteDog(int did) {
		boolean status = false;
		if (dogRepository.existsById(did)) {
			dogRepository.deleteById(did);
			status = true;
		}
		return status;
	}
	
	/**
	 * This is logic for partial update
	 * @param patchDTO
	 */
	
	public void update(PatchDTO patchDTO) {
		Optional<DogEntity> optional = dogRepository.findById(Integer.parseInt(patchDTO.getId()));
		if (optional.isPresent()) {
			DogEntity dbdogEntity = optional.get();
			if ("name".equalsIgnoreCase(patchDTO.getAttributeName())) {
				dbdogEntity.setName(patchDTO.getAttributeValue());
			}
			else if ("color".equalsIgnoreCase(patchDTO.getAttributeName())) {
				dbdogEntity.setColor(patchDTO.getAttributeValue());
			}
			dogRepository.save(dbdogEntity);
		}else {
			throw new DogNotFoundException("Hey! it seems like this dog does not exist!");
		}
	}

	//** logic for updating existing entity
	
	public void update(DogDTO dogDTO) {
		Optional<DogEntity> optional = dogRepository.findById(dogDTO.getDid());
		if (optional.isPresent()) {
			DogEntity dbdogEntity = optional.get();
			if (dogDTO.getColor() != null) {
				dbdogEntity.setColor(dogDTO.getColor());
			}
			if (dogDTO.getName() != null) {
				dbdogEntity.setName(dogDTO.getName());
			}
			dogRepository.save(dbdogEntity);
		}else {
			throw new DogNotFoundException("Hey! it seems like this dog does not exist!");
		}
	}
}
