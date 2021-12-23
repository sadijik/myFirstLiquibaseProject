package com.example.pet_project.rest;

import com.example.pet_project.PetProjectApplicationTests;
import com.example.pet_project.entity.Towens;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.repo.TowensRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class RestControllerTowensTest extends PetProjectApplicationTests {

	@Autowired
	private TowensRepository towensRepository;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	@AfterEach
	public void resetDb() {
		towensRepository.deleteAll();
	}


	private Towens createTowens(String town_name, Integer distance) {
		Towens towens = Towens.builder()
				.town_name(town_name)
				.distance(distance)
				.build();
		return towensRepository.save(towens);

	}

	@Test
	void startTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v2"))
				.andExpect(status().isOk())
				.andExpect(content().string("Towens"))
				.andDo(print());

	}

	@Test
	void createItems() throws Exception {
		Towens towens = createTowens("Moscow", 100);
		assertNotNull(towens.getTown_name());
		assertNotNull(towens.getDistance());


		mvc.perform(MockMvcRequestBuilders.post("/api/v2/addTowens")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(towens)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.town_name").value("Moscow"))
				.andExpect(jsonPath("$.distance").value("100"))
				.andDo(print());
	}

	@Test
	void findByIdTowens() throws Exception {
		Towens towens = createTowens("London", 200);
		Long id = towens.getId();

		mvc.perform(MockMvcRequestBuilders.get("/api/v2/findtowens{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.town_name").value("London"))
				.andExpect(jsonPath("$.distance").value(200))
				.andDo(print());
	}

	@Test
	void findByIdTowensException() throws Exception {

		Long id = 2L;

		mvc.perform(MockMvcRequestBuilders.get("/api/v2/findtowens{id}", id))
				.andExpect(status().isBadRequest())
				.andExpect(mvcResult -> mvcResult.getResolvedException().equals(ElementNotFoundException.class))
				.andDo(print());
	}

	@Test
	void findAllTowens() throws Exception {
		Towens towens = createTowens("Paris", 300);
		Towens towens1 = createTowens("Madrid", 250);

		mvc.perform(MockMvcRequestBuilders.get("/api/v2/findalltowens"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(towens.getId()))
				.andExpect(jsonPath("$[0].town_name").value("Paris"))
				.andExpect(jsonPath("$[0].distance").value(300))
				.andExpect(jsonPath("$[1].id").value(towens1.getId()))
				.andExpect(jsonPath("$[1].town_name").value("Madrid"))
				.andExpect(jsonPath("$[1].distance").value(250));
	}

	@Test
	void deleteTowens() throws Exception {
		Towens towens = createTowens("Berline", 150);

		Long id = towens.getId();

		mvc.perform(MockMvcRequestBuilders.delete("/api/v2/deletetowens{id}", id))
				.andExpect(status().isOk())
				.andExpect(content().string(String.format("данные id=%d towens удалены", id)))
				.andDo(print());
	}

	@Test
	void deleteTowensException() throws Exception {

		Long id = 2L;

		mvc.perform(MockMvcRequestBuilders.delete("/api/v2/deletetowens{id}", id))
				.andExpect(status().isBadRequest())
				.andExpect(mvcResult -> mvcResult.getResolvedException().equals(ElementNotFoundException.class))
				.andDo(print());
	}
}