package com.example.pet_project.rest;


import com.example.pet_project.PetProjectApplicationTests;
import com.example.pet_project.entity.Items;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.repo.ItemsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class RestConrollerItemsTest extends PetProjectApplicationTests {

	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mvc;


	@AfterEach
	public void resetDb() {
		itemsRepository.deleteAll();
	}


	private Items createItem(String name, Integer quantity1) {
		Items emp = Items.builder()
				.item_name(name)
				.quantity(quantity1)
				.build();
		return itemsRepository.save(emp);

	}

	@Test
	public void startTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v1"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("items"));

	}

	@Test
	public void createItemTest() throws Exception {
		Items items = createItem("bolt", 10);
		assertNotNull(items.getItem_name());
		assertNotNull(items.getQuantity());

		mvc.perform(MockMvcRequestBuilders.post("/api/v1/addItems")
						.content(objectMapper.writeValueAsString(items))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.quantity").value(10))
				.andExpect(jsonPath("$.item_name").value("bolt"));
	}

	@Test
	public void findByIdItemsTest() throws Exception {
		Long id = createItem("car", 1).getId();

		mvc.perform(MockMvcRequestBuilders.get("/api/v1/finditems{id}", id))
				.andDo(print())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.quantity").value(1))
				.andExpect(jsonPath("$.item_name").value("car"));
	}

	@Test
	public void findByIdItemsExceptionTest() throws Exception {
		Long id = 1L;

		mvc.perform(MockMvcRequestBuilders.get("/api/v1/finditems{id}", id))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(mvcResult -> mvcResult.getResolvedException().equals(ElementNotFoundException.class));
	}


	@Test
	void findAllItems() throws Exception {

		Items items = createItem("phone", 3);
		Items items1 = createItem("headphone", 2);


		mvc.perform(MockMvcRequestBuilders.get("/api/v1/findallitems"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(items.getId()))
				.andExpect(jsonPath("$[0].item_name").value("phone"))
				.andExpect(jsonPath("$[0].quantity").value(3))
				.andExpect(jsonPath("$[1].id").value(items1.getId()))
				.andExpect(jsonPath("$[1].item_name").value("headphone"))
				.andExpect(jsonPath("$[1].quantity").value(2));


	}


	@Test
	void deleteItems() throws Exception {
		Items items = createItem("box", 100);

		Long id = items.getId();

		mvc.perform(MockMvcRequestBuilders.delete("/api/v1/deleteitems{id}", id))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(String.format("данные id=%d items удалены", id)));
	}

	@Test
	void deleteItemsEception() throws Exception {
		Long id = 4L;

		mvc.perform(MockMvcRequestBuilders.delete("/api/v1/deleteitems{id}", id))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(mvcResult -> mvcResult.getResolvedException().equals(ElementNotFoundException.class));
	}


}