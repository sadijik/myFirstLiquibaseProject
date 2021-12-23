package com.example.pet_project.sevice;

import com.example.pet_project.entity.Items;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.repo.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ItemsService {


	private final ItemsRepository itemsRepository;

	@Autowired
	public ItemsService(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	@Transactional
	public Items createItems(Items items) {

		return itemsRepository.save(items);
	}

	@Transactional(readOnly = true)
	public Items firstByIdItems(Long id) throws ElementNotFoundException {
		return itemsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент items id=" + id + " не найден"));
	}

	@Transactional(readOnly = true)
	public List<Items> allItems() {
		Iterable<Items> all = itemsRepository.findAll();
		List<Items> list = new ArrayList<>();
		all.forEach(list::add);
		return list;
	}

	@Transactional
	public String deleteItems(Long id) throws ElementNotFoundException {


		Items items = itemsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент items id=" + id + " не найден для удаления"));

		itemsRepository.delete(items);
		return "данные id=" + id + " items удалены";

	}

	@Transactional
	public Items patchItems(Long id, Items items) throws ElementNotFoundException {

		Items wasItems = itemsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент items id=" + id + " не найден для обновления"));

		wasItems.setItem_name(items.getItem_name());
		wasItems.setQuantity(items.getQuantity());


		return itemsRepository.save(wasItems);
	}


}


