package com.example.pet_project.rest;

import com.example.pet_project.dto.ItemsDto;
import com.example.pet_project.entity.Items;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.sevice.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/v1")
public class RestConrollerItems {


	private final ItemsService itemsService;

	@Autowired
	public RestConrollerItems(ItemsService itemsService) {
		this.itemsService = itemsService;
	}

	@GetMapping
	public ResponseEntity<?> start() {
		return ResponseEntity.ok("items");
	}


	@PostMapping("/addItems")
	public ResponseEntity<?> createItems(@RequestBody @Valid Items items) {

		Items items1 = itemsService.createItems(items);
		return new ResponseEntity<>(ItemsDto.toItemsDto(items1), HttpStatus.OK);

	}

	@GetMapping("/finditems{id}")
	public ResponseEntity<?> findByIdItems(@PathVariable("id") Long id) throws ElementNotFoundException {

		Items items = itemsService.firstByIdItems(id);
		return new ResponseEntity<>(ItemsDto.toItemsDto(items), HttpStatus.OK);

	}

	@GetMapping("/findallitems")
	public ResponseEntity<?> findAllItems() {
		List<Items> list = itemsService.allItems();
		List<ItemsDto> collect = list.stream().map(ItemsDto::toItemsDto).collect(Collectors.toList());
		return new ResponseEntity<>(collect, HttpStatus.OK);
	}


	@PutMapping("/itemspatch{id}")
	public ResponseEntity<?> patchItems(@PathVariable("id") Long id, @RequestBody @Valid Items items) throws ElementNotFoundException {

		Items items1 = itemsService.patchItems(id, items);
		return new ResponseEntity<>(ItemsDto.toItemsDto(items1), HttpStatus.OK);

	}

	@DeleteMapping("/deleteitems{id}")
	public ResponseEntity<?> deleteItems(@PathVariable("id") Long id) throws ElementNotFoundException {


		return new ResponseEntity<>(itemsService.deleteItems(id), HttpStatus.OK);

	}


}
