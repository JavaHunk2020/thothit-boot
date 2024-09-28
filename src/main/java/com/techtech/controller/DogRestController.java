package com.techtech.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techtech.dto.DogDTO;
import com.techtech.service.DogService;

@RestController
@RequestMapping("/v1")
public class DogRestController {
	
	final private DogService dogService;
	
	/**
	 * this is called constructor injection
	 * @param dogService
	 */
	public DogRestController(final DogService dogService) {
		this.dogService=dogService;
	}

	// ENDPOINT = http://localhost:444/v1/dogs
	@GetMapping("/dogs")
	public List<DogDTO> showDogs() {
		return dogService.findDogs();
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
	// 201 -CREATED
	@PostMapping("/dogs")
	public ResponseEntity<DogDTO>  createDog(@RequestBody DogDTO dogDTO) {
		dogDTO=dogService.save(dogDTO);
		return new ResponseEntity<DogDTO>(dogDTO,HttpStatus.CREATED);
	}
	
	///http://localhost:444/v1/dogs/1
	@DeleteMapping("/dogs/{did}")
	public ResponseEntity<Void> deleteDog(@PathVariable int did) {
		dogService.deleteDog(did);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
