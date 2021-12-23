package com.example.pet_project.rest;

import com.example.pet_project.PetProjectApplicationTests;
import com.example.pet_project.entity.Shippings;
import com.example.pet_project.exeprion.DataStartAfterDataEnsException;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.sevice.ShippingsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestControllerShippingsTest extends PetProjectApplicationTests {

	@Autowired
	private ShippingsService shippingsService;

	@Test
	void createItems() throws DataStartAfterDataEnsException, ElementNotFoundException {
		Shippings shippings = Shippings.builder()
				.end_data(LocalDate.of(2200, 3, 23))
				.start_data(LocalDate.of(2130, 12, 30))
				.build();
		System.out.println(shippings.getEnd_data());
		System.out.println(shippings.getStart_data());
		System.out.println(shippings.getId());
		shippingsService.createShippings(shippings);
		System.out.println("12 " + shippings.getEnd_data());
		assertNotNull(shippingsService.firstByIdShippings(shippings.getId()));
		System.out.println("22 " + shippingsService.firstByIdShippings(shippings.getId()));
		shippingsService.deleteShippings(shippings.getId());
	}

	@Test
	void createItemsException() {
		Shippings shippings = Shippings.builder()
				.end_data(LocalDate.of(2000, 3, 23))
				.start_data(LocalDate.of(2230, 12, 30))
				.build();
		assertThrows(DataStartAfterDataEnsException.class, () -> shippingsService.createShippings(shippings));
	}

	@Test
	void findByIdshppings() {

		assertThrows(ElementNotFoundException.class, () -> shippingsService.firstByIdShippings(2123L));

	}

	@Test
	void findAllShippings() throws DataStartAfterDataEnsException, ElementNotFoundException {
		Shippings shippings1 = Shippings.builder()
				.end_data(LocalDate.of(2200, 3, 23))
				.start_data(LocalDate.of(2130, 12, 30))
				.build();
		Shippings shippings2 = Shippings.builder()
				.end_data(LocalDate.of(2300, 4, 23))
				.start_data(LocalDate.of(2230, 12, 30))
				.build();
		shippingsService.createShippings(shippings1);
		shippingsService.createShippings(shippings2);
		assertNotNull(shippingsService.allShippings());
		shippingsService.deleteShippings(shippings1.getId());
		shippingsService.deleteShippings(shippings2.getId());
	}


	@Test
	void deleteShuppings() throws DataStartAfterDataEnsException, ElementNotFoundException {
		Shippings shippings = Shippings.builder()
				.end_data(LocalDate.of(2200, 3, 23))
				.start_data(LocalDate.of(2130, 12, 30))
				.build();
		shippingsService.createShippings(shippings);
		assertNotNull(shippingsService.firstByIdShippings(shippings.getId()));
		shippingsService.deleteShippings(shippings.getId());
		assertThrows(ElementNotFoundException.class, () -> shippingsService.firstByIdShippings(shippings.getId()));
	}


}