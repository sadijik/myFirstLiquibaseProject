package com.example.pet_project.rest;

import com.example.pet_project.PetProjectApplicationTests;
import com.example.pet_project.entity.Towens;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.sevice.TowensService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


class RestControllerTowensTest extends PetProjectApplicationTests {
	@Autowired
	private TowensService towensService;

	@Test
	void createItems() throws ElementNotFoundException {
		Towens towens= Towens.builder()
				.town_name("test_towen")
				.distance(231312)
				.build();
		towensService.createTowens(towens);
		assertNotNull(towensService.firstByIdTowens(towens.getId()));
		towensService.deleteTowens(towens.getId());

	}

	@Test
	void findByIdTowens() {
		assertThrows(ElementNotFoundException.class,()->towensService.firstByIdTowens(2000000L));
	}

	@Test
	void findAllTowens() throws ElementNotFoundException {
		Towens towens1= Towens.builder()
				.town_name("test_towen1")
				.distance(231312)
				.build();
		Towens towens2= Towens.builder()
				.town_name("test_towen2")
				.distance(231312)
				.build();
		towensService.createTowens(towens1);
		towensService.createTowens(towens2);
		assertNotNull(towensService.allTowens());
		towensService.deleteTowens(towens1.getId());
		towensService.deleteTowens(towens2.getId());
	}

	@Test
	void deleteTowens()  {
		assertThrows(ElementNotFoundException.class,()->towensService.deleteTowens(202002020020L));
	}
}