package com.example.pet_project.sevice;

import com.example.pet_project.entity.Items;
import com.example.pet_project.entity.Shippings;
import com.example.pet_project.exeprion.DataStartAfterDataEnsException;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.repo.ItemsRepository;
import com.example.pet_project.repo.ShippingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.List;



@Service
public class ItemsService {
	@Autowired
	private ShippingsRepository shippingsRepository;

	@Autowired
	private ItemsRepository itemsRepository;

	public Items createItems(Items items) {

		return itemsRepository.save(items);
	}

	public Items firstByIdItems(Long id) throws ElementNotFoundException {
		return itemsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент items id=" + id + " не найден"));
	}

	public List<Items> allItems() {
		Iterable<Items> all = itemsRepository.findAll();
		List<Items> list = new ArrayList<>();
		all.forEach(list::add);
		return list;
	}


	public String deleteItems(Long id) throws ElementNotFoundException {


		Items items = itemsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент items id=" + id + " не найден для удаления"));

		itemsRepository.delete(items);
		return "данные id=" + id + " items удалены ";

	}

	public Items patchItems(Long id, Items items) throws ElementNotFoundException {

		Items wasItems = itemsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент items id=" + id + " не найден для обновления"));

		wasItems.setItem_name(items.getItem_name());
		wasItems.setQuantity(items.getQuantity());

		return itemsRepository.save(wasItems);
	}


}


