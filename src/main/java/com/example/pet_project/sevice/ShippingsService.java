package com.example.pet_project.sevice;


import com.example.pet_project.entity.Items;
import com.example.pet_project.entity.Shippings;
import com.example.pet_project.entity.Towens;
import com.example.pet_project.exeprion.DataStartAfterDataEnsException;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.repo.ShippingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;



@Service
public class ShippingsService {
	@Autowired
	private ItemsService itemsService;
	@Autowired
	private TowensService towensService;
	@Autowired
	private ShippingsRepository shippingsRepository;

	public Shippings createShippings(Shippings shippings) throws DataStartAfterDataEnsException   {
		if (shippings.getEnd_data() == null) {
			return shippingsRepository.save(shippings);
		}
		if (shippings.getStart_data().isAfter(shippings.getEnd_data())) {
			throw new DataStartAfterDataEnsException("дата Start должна идти перед датой End");
		}

		return shippingsRepository.save(shippings);
	}


	public Shippings firstByIdShippings(Long id) throws ElementNotFoundException {

		return shippingsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент shippings id=" + id + " не найден"));

	}

		public List<Shippings> allShippings() {
			List<Shippings> list = new ArrayList<>();
			shippingsRepository.findAll().forEach(list::add);
			return list;

	}

	public String deleteShippings(Long id) throws  ElementNotFoundException {
		Shippings shippings = shippingsRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент shippings id=" + id + " не найден для удаления"));
		shippingsRepository.delete(shippings);

		return "данные Shippings id=" + id + " удалены";
	}


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
		Towens towens= towensService.firstByIdTowens(towenId);

		shippings.setTowen(towens);


		return shippingsRepository.save(shippings);
	}

}


