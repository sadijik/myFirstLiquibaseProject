package com.example.pet_project.rest;


import com.example.pet_project.PetProjectApplicationTests;
import com.example.pet_project.entity.Items;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.sevice.ItemsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RestConrollerItemsTest extends PetProjectApplicationTests {

	@Autowired
	private ItemsService itemsService;


	@Test
	void createItems() throws ElementNotFoundException {
		Items items = new Items();
		items.setItem_name("test");
		items.setQuantity(2000008);
		itemsService.createItems(items);
		assertNotNull(itemsService.firstByIdItems(items.getId()));
		itemsService.deleteItems(items.getId());

	}


	@Test
	void findByIdItemsException() {
		assertThrows(ElementNotFoundException.class, () -> itemsService.firstByIdItems(22200L));
	}

	@Test
	void findAllItems() throws ElementNotFoundException {
		Items items1 = Items.builder()
				.item_name("Test2")
				.quantity(776)
				.build();
		Items items2 = Items.builder()
				.item_name("Test2")
				.quantity(776)
				.build();
		itemsService.createItems(items1);
		itemsService.createItems(items2);
		assertNotNull(itemsService.allItems());
		itemsService.deleteItems(items1.getId());
		itemsService.deleteItems(items2.getId());

	}


	@Test
	void deleteItems() {

		assertThrows(ElementNotFoundException.class, () -> itemsService.deleteItems(21313131212L));

	}

}