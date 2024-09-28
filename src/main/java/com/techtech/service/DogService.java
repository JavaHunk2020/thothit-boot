package com.techtech.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techtech.dao.DogRepository;
import com.techtech.dto.DogDTO;
import com.techtech.entity.DogEntity;

@Service
public class DogService {

	@Autowired
	private DogRepository dogRepository;
	
	public DogDTO save(DogDTO dogDTO) {
		DogEntity dogEntity=new DogEntity();
		BeanUtils.copyProperties(dogDTO, dogEntity);
		DogEntity entity=dogRepository.save(dogEntity);
		dogDTO.setDid(entity.getDid());
		return dogDTO;
	}
	
	public List<DogDTO> findDogs() {
		List<DogDTO> results =new ArrayList<DogDTO>();
		List<DogEntity> dogEntityList=dogRepository.findAll();
		for(DogEntity entity : dogEntityList) {
			DogDTO dogDTO=new DogDTO();
			BeanUtils.copyProperties(entity, dogDTO);
			results.add(dogDTO);
		}
		return results;
	}
	
	public void deleteDog(int did) {
		dogRepository.deleteById(did);
	}
}
