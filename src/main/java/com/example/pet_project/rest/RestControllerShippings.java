package com.example.pet_project.rest;


import com.example.pet_project.dto.ShippingsDto;
import com.example.pet_project.entity.Shippings;
import com.example.pet_project.exeprion.DataStartAfterDataEnsException;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.sevice.ShippingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/v3")
public class RestControllerShippings {

	private final ShippingsService shippingsService;

	@Autowired
	public RestControllerShippings(ShippingsService shippingsService) {
		this.shippingsService = shippingsService;
	}

	@GetMapping
	public ResponseEntity<?> start() {
		return ResponseEntity.ok("Shippings");
	}


	@PostMapping("/addshippings")
	public ResponseEntity<?> createShippings(@Valid @RequestBody Shippings shippings) throws DataStartAfterDataEnsException {

		Shippings shippings1 = shippingsService.createShippings(shippings);
		//return new ResponseEntity<>(ShippingsDto.toSippingslDto(shippings1), HttpStatus.OK);
		return new ResponseEntity<>(shippings1, HttpStatus.OK);

	}

	@GetMapping("/findshippings{id}")
	public ResponseEntity<?> findByIdshppings(@PathVariable("id") Long id) throws ElementNotFoundException {

		Shippings shippings = shippingsService.firstByIdShippings(id);

		return new ResponseEntity<>(ShippingsDto.toSippingslDto(shippings), HttpStatus.OK);

	}


	@GetMapping("/findallshippings")
	public ResponseEntity<?> findAllShippings() {
		List<ShippingsDto> list = shippingsService.allShippings().stream().map(ShippingsDto::toSippingslDto).collect(Collectors.toList());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PutMapping("/patchshippings{id}")
	public ResponseEntity<?> patchShippings(@PathVariable("id") Long id, @RequestBody Shippings shippings) throws ElementNotFoundException {
		Shippings shippings1 = shippingsService.patchShippings(id, shippings);
		return new ResponseEntity<>(ShippingsDto.toSippingslDto(shippings1), HttpStatus.OK);
	}

	@DeleteMapping("/deleteshippings{id}")
	public ResponseEntity<?> deleteShuppings(@PathVariable("id") Long id) throws ElementNotFoundException {
		return new ResponseEntity<>(shippingsService.deleteShippings(id), HttpStatus.OK);

	}


	@PostMapping("{shippingsId}/items/{itemId}/add")
	public ResponseEntity<?> addItemToShippings(@PathVariable("shippingsId") final Long shippingsId,
	                                            @PathVariable("itemId") final Long itemId) throws ElementNotFoundException {
		Shippings shippings = shippingsService.addItemToShippings(shippingsId, itemId);
		return new ResponseEntity<>(ShippingsDto.toSippingslDto(shippings), HttpStatus.OK);
	}

	@PostMapping("{shippingsId}/towens/{towenId}/add")
	public ResponseEntity<?> addTowenToShippings(@PathVariable("shippingsId") final Long shippingsId,
	                                             @PathVariable("towenId") final Long towenId) throws ElementNotFoundException {
		Shippings shippings = shippingsService.addTowensToShippings(shippingsId, towenId);
		return new ResponseEntity<>(ShippingsDto.toSippingslDto(shippings), HttpStatus.OK);
	}

}