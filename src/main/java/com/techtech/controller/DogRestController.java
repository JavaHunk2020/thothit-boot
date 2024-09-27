package com.techtech.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techtech.dto.DogDTO;

@RestController
@RequestMapping("/v1")
public class DogRestController {

	// ENDPOINT = http://localhost:444/v1/dogs
	@GetMapping("/dogs")
	public List<DogDTO> showDogs() {
		List<DogDTO> dtos = new ArrayList<DogDTO>();
		dtos.add(new DogDTO(1, "Jacky", "red"));
		dtos.add(new DogDTO(2, "Macky", "white"));
		dtos.add(new DogDTO(3, "Gogar", "blue"));
		return dtos;
	}

	// /dogs?did=12
	// http://localhost:444/v1/dogs/1
	@GetMapping("/dogs/{did}")
	public DogDTO showDog(@PathVariable int did) {
		return new DogDTO(did, "Macky", "white");
	}

	/**
	 * { "name": "Jacky", "color": "red" }
	 * 
	 * @param dogDTO
	 * @return
	 */
	@PostMapping("/dogs")
	public DogDTO createDog(@RequestBody DogDTO dogDTO) {
		System.out.println("request payload = " + dogDTO);
		dogDTO.setDid(new Random().nextInt());
		return dogDTO;
	}

}
