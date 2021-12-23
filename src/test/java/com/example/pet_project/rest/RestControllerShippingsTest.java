package com.example.pet_project.rest;

import com.example.pet_project.PetProjectApplicationTests;
import com.example.pet_project.entity.Items;
import com.example.pet_project.entity.Shippings;
import com.example.pet_project.entity.Towens;
import com.example.pet_project.exeprion.DataStartAfterDataEnsException;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.repo.ItemsRepository;
import com.example.pet_project.repo.ShippingsRepository;
import com.example.pet_project.repo.TowensRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RestControllerShippingsTest extends PetProjectApplicationTests {

	@Autowired
	private ShippingsRepository shippingsRepository;
	@Autowired
	private ItemsRepository itemsRepository;
	@Autowired
	private TowensRepository towensRepository;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;

	private Shippings createShippings(LocalDate start, LocalDate end) {
		Shippings shippings = Shippings.builder()
				.start_data(start)
				.end_data(end)
				.build();
		return shippingsRepository.save(shippings);
	}

	@AfterEach
	public void resetDb() {
		shippingsRepository.deleteAll();
	}

	@Test
	void start() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v3/"))
				.andExpect(status().isOk())
				.andExpect(content().string("Shippings"))
				.andDo(print());
	}

	@Test
	void createShippings() throws Exception {
		Shippings shippings = createShippings(LocalDate.of(2010, 2, 12), LocalDate.of(2015, 3, 15));
		assertNotNull(shippings.getStart_data());
		assertNotNull(shippings.getEnd_data());

		mvc.perform(MockMvcRequestBuilders.post("/api/v3/addshippings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(shippings)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.start_data").value("2010-02-12"))
				.andExpect(jsonPath("$.end_data").value("2015-03-15"))
				.andDo(print());
	}

	@Test
	void createShippingsDataException() throws Exception {
		Shippings shippings = createShippings(LocalDate.of(2110, 2, 12), LocalDate.of(2015, 3, 15));
		assertNotNull(shippings.getStart_data());
		assertNotNull(shippings.getEnd_data());

		mvc.perform(MockMvcRequestBuilders.post("/api/v3/addshippings")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(shippings)))
				.andExpect(status().isBadRequest())
				.andExpect(mvcResult -> mvcResult.getResolvedException().equals(DataStartAfterDataEnsException.class))
				.andDo(print());
	}

	@Test
	void createShippingsException() throws Exception {
		Long id = 3L;

		mvc.perform(MockMvcRequestBuilders.post("/api/v3/addshippings"))
				.andExpect(status().isBadRequest())
				.andExpect(mvcResult -> mvcResult.getResolvedException().equals(ElementNotFoundException.class))
				.andDo(print());

	}

	@Test
	void findByIdshppings() throws Exception {
		Shippings shippings = createShippings(LocalDate.of(2090, 12, 6), LocalDate.of(2115, 9, 15));
		Long id = shippings.getId();
		mvc.perform(MockMvcRequestBuilders.get("/api/v3/findshippings{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.start_data").value("2090-12-06"))
				.andExpect(jsonPath("$.end_data").value("2115-09-15"))
				.andDo(print());
	}

	@Test
	void findByIdshppingsException() throws Exception {

		Long id = 5L;
		mvc.perform(MockMvcRequestBuilders.get("/api/v3/findshippings{id}", id))
				.andExpect(status().isBadRequest())
				.andExpect(mvcResult -> mvcResult.getResolvedException().equals(ElementNotFoundException.class))
				.andDo(print());
	}

	@Test
	void findAllShippings() throws Exception {
		Shippings shippings = createShippings(LocalDate.of(2005, 3, 26), LocalDate.of(2010, 11, 15));
		Shippings shippings1 = createShippings(LocalDate.of(2006, 5, 13), LocalDate.of(2009, 8, 23));
		mvc.perform(MockMvcRequestBuilders.get("/api/v3/findallshippings")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(shippings.getId()))
				.andExpect(jsonPath("$[0].start_data").value("2005-03-26"))
				.andExpect(jsonPath("$[0].end_data").value("2010-11-15"))
				.andExpect(jsonPath("$[1].id").value(shippings1.getId()))
				.andExpect(jsonPath("$[1].start_data").value("2006-05-13"))
				.andExpect(jsonPath("$[1].end_data").value("2009-08-23"))
				.andDo(print());
	}

	@Test
	void deleteShuppings() throws Exception {
		Shippings shippings = createShippings(LocalDate.of(2007, 11, 16), LocalDate.of(2009, 12, 25));
		Long id = shippings.getId();
		mvc.perform(MockMvcRequestBuilders.delete("/api/v3/deleteshippings{id}", id))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	void deleteShuppingsException() throws Exception {
		Long id = 3L;
		mvc.perform(MockMvcRequestBuilders.delete("/api/v3/deleteshippings{id}", id))
				.andExpect(status().isBadRequest())
				.andExpect(mvcResult -> mvcResult.getResolvedException().equals(ElementNotFoundException.class))
				.andDo(print());
	}

	@Test
	void addItemToShippingsTest() throws Exception {
		Items items = Items.builder()
				.item_name("keyboard")
				.quantity(22)
				.build();
		itemsRepository.save(items);

		Shippings shippings = createShippings(LocalDate.of(2001, 10, 12), LocalDate.of(2004, 9, 20));
		mvc.perform(MockMvcRequestBuilders.post("/api/v3/{shippingsId}/items/{itemId}/add", shippings.getId(), items.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(items))
						.content(objectMapper.writeValueAsString(shippings)))
				.andExpect(status().isOk())
				.andDo(print());


	}

	@Test
	void addTowenToShippingsTest() throws Exception {
		Towens towens = Towens.builder()
				.town_name("Monaco")
				.distance(222)
				.build();
		towensRepository.save(towens);

		Shippings shippings = createShippings(LocalDate.of(2002, 7, 17), LocalDate.of(2008, 3, 18));
		mvc.perform(MockMvcRequestBuilders.post("/api/v3/{shippingsId}/towens/{towenId}/add", shippings.getId(), towens.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(towens))
						.content(objectMapper.writeValueAsString(shippings)))
				.andExpect(status().isOk())
				.andDo(print());
	}
}