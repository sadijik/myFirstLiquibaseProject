package com.example.pet_project.rest;

import com.example.pet_project.dto.TowensDto;
import com.example.pet_project.entity.Towens;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.sevice.TowensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v2")
public class RestControllerTowens {
	@Autowired
	private TowensService towensService;

	@GetMapping
	public ResponseEntity<?> start() {
		return ResponseEntity.ok("Towens");
	}


	@PostMapping("/addTowens")
	public ResponseEntity<?> createItems(@Valid @RequestBody Towens towens) {
		Towens towens1 = towensService.createTowens(towens);
		return new ResponseEntity<>(TowensDto.toModelTowens(towens1), HttpStatus.OK);

	}

	@GetMapping("/findtowens{id}")
	public ResponseEntity<?> findByIdTowens(@PathVariable("id") Long id) throws ElementNotFoundException {

		return new ResponseEntity<>(TowensDto.toModelTowens(towensService.firstByIdTowens(id)), HttpStatus.OK);

	}

	@GetMapping("/findalltowens")
	public ResponseEntity<?> findAllTowens() {
		List<TowensDto> towens = towensService.allTowens().stream().map(TowensDto::toModelTowens).collect(Collectors.toList());

		return new ResponseEntity<>(towens, HttpStatus.OK);
	}


	@PutMapping("/towenspatch{id}")
	public ResponseEntity<?> patchTowens(@PathVariable("id") Long id, @RequestBody @Valid Towens towens) throws ElementNotFoundException {

		Towens towens1 = towensService.patchTowens(id, towens);
		return new ResponseEntity<>(TowensDto.toModelTowens(towens1), HttpStatus.OK);


	}

	@DeleteMapping("/deletetowens{id}")
	public ResponseEntity<?> deleteTowens(@PathVariable("id") Long id) throws ElementNotFoundException {

		return new ResponseEntity<>(towensService.deleteTowens(id), HttpStatus.OK);

	}
}
