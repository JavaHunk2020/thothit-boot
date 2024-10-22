package com.techtech.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techtech.dto.DogDTO;
import com.techtech.dto.PatchDTO;
import com.techtech.dto.UserRequestDTO;
import com.techtech.service.DogService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//MANY TO MANY Relationship
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
	
	@Value("${jwt.secret.key:ABUE87%&$&##@)@(&@*^@^@@@}")
	private String jwtSecret;

	private int jwtExpirationMs = 1800000;
	
	@PostMapping("/cauth")
	public Map<String,Object> authUser(@RequestBody UserRequestDTO requestDTO){
		Map<String, Object> claims = new HashMap<>();
		claims.put("company", "AbcTech");
		claims.put("title", "Mr.");
        String subject=requestDTO.getEmail();
		if(requestDTO.getEmail().equalsIgnoreCase("jack@gmail.com") && "jill".equalsIgnoreCase(requestDTO.getPassword())) {
			claims.put("scopes", List.of("CUSTOMER"));
		}else if(requestDTO.getEmail().equalsIgnoreCase("admin@gmail.com") && "admin".equalsIgnoreCase(requestDTO.getPassword())) {
			claims.put("scopes", List.of("ADMIN"));
		}
		String token= Jwts.builder().
				setSubject(subject)
				.addClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
		return Map.of("Authentication",token);
	}

	// ENDPOINT = http://localhost:444/v1/dogs
	@GetMapping("/dogs")
	@PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('CUSTOMER')")
	public List<DogDTO> showDogs() {
		return dogService.findDogs();
	}

	// /dogs?did=12
	// http://localhost:444/v1/dogs/1
	@GetMapping("/dogs/{did}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public  ResponseEntity<DogDTO>  showDog(@PathVariable int did) {
		DogDTO dogDTO=dogService.findById(did);
		return new ResponseEntity<DogDTO>(dogDTO,HttpStatus.OK);
	}

	/**
	 * { "name": "Jacky", "color": "red" }
	 * 
	 * @param dogDTO
	 * @return
	 */
	// 201 -CREATED
	//http://localhost:444/v1/dogs
	@PostMapping("/dogs")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<DogDTO>  createDog(@RequestBody DogDTO dogDTO) {
		//did must be null
		dogDTO=dogService.save(dogDTO);
		return new ResponseEntity<DogDTO>(dogDTO,HttpStatus.CREATED);
	}
	
	@PutMapping("/dogs")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<DogDTO>  updateDog(@RequestBody DogDTO dogDTO) {
		///it must be not be null
		 dogService.update(dogDTO);
		 return new ResponseEntity<DogDTO>(dogDTO,HttpStatus.OK);
	}
	
	///http://localhost:444/v1/dogs/1
	@DeleteMapping("/dogs/{did}")
	public ResponseEntity<Void> deleteDog(@PathVariable int did) {
		boolean status=dogService.deleteDog(did);
		return new ResponseEntity<Void>(status ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}
	
	@PatchMapping("/dogs")
	public ResponseEntity<Map<String,Object>>  updateDog(@RequestBody PatchDTO dogDTO) {
		///it must be not be null
		 dogService.update(dogDTO);
		 Map map=new HashMap<String, Object>();
		 map.put("message", dogDTO.getAttributeName()+" is updated successfully.");
		 return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

}
