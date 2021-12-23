package com.example.pet_project.sevice;


import com.example.pet_project.entity.Items;
import com.example.pet_project.entity.Shippings;
import com.example.pet_project.entity.Towens;
import com.example.pet_project.exeprion.DataStartAfterDataEnsException;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.repo.ShippingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ShippingsService {
	private final ItemsService itemsService;
	private final TowensService towensService;
	private final ShippingsRepository shippingsRepository;

	@Autowired
	public ShippingsService(ItemsService itemsService, TowensService towensService, ShippingsRepository shippingsRepository) {
		this.itemsService = itemsService;
		this.towensService = towensService;
		this.shippingsRepository = shippingsRepository;
	}

	@Transactional
	public Shippings createShippings(Shippings shippings) throws DataStartAfterDataEnsException {
		if (shippings.getEnd_data() == null) {
			return shippingsRepository.save(shippings);
		}
		if (shippings.getStart_data().isAfter(shippings.getEnd_data())) {
			throw new DataStartAfterDataEnsException("дата Start должна идти перед датой End");
		}

		return shippingsRepository.save(shippings);
	}

	@Transactional(readOnly = true)
	public Shippings firstByIdShippings(Long id) throws ElementNotFoundException {

		return shippingsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент shippings id=" + id + " не найден"));

	}

	@Transactional(readOnly = true)
	public List<Shippings> allShippings() {
		List<Shippings> list = new ArrayList<>();
		shippingsRepository.findAll().forEach(list::add);
		return list;

	}

	@Transactional
	public String deleteShippings(Long id) throws ElementNotFoundException {
		Shippings shippings = shippingsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент shippings id=" + id + " не найден для удаления"));
		shippingsRepository.delete(shippings);

		return "данные Shippings id=" + id + " удалены";
	}

	@Transactional
	public Shippings patchShippings(Long id, Shippings shippings) throws ElementNotFoundException {

		Shippings wasShippings = shippingsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент shippings id=" + id + " не найден для обновления"));

		wasShippings.setStart_data(shippings.getStart_data());
		wasShippings.setEnd_data(shippings.getEnd_data());

		return shippingsRepository.save(wasShippings);
	}


	public Shippings addItemToShippings(Long shippingsId, Long itemId) throws ElementNotFoundException {

		Shippings shippings = firstByIdShippings(shippingsId);
		Items item = itemsService.firstByIdItems(itemId);

		shippings.setItem(item);


		return shippingsRepository.save(shippings);
	}

	public Shippings addTowensToShippings(Long shippingsId, Long towenId) throws ElementNotFoundException {

		Shippings shippings = firstByIdShippings(shippingsId);
		Towens towens = towensService.firstByIdTowens(towenId);

		shippings.setTowen(towens);


		return shippingsRepository.save(shippings);
	}

}


