package com.example.pet_project.sevice;

import com.example.pet_project.entity.Towens;
import com.example.pet_project.exeprion.ElementNotFoundException;
import com.example.pet_project.repo.TowensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TowensService {

	private final TowensRepository towensRepository;

	@Autowired
	public TowensService(TowensRepository towensRepository) {
		this.towensRepository = towensRepository;
	}

	@Transactional
	public Towens createTowens(Towens towens) {
		return towensRepository.save(towens);
	}

	@Transactional(readOnly = true)
	public Towens firstByIdTowens(Long id) throws ElementNotFoundException {

		return towensRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент towens id=" + id + " не найден"));


	}

	@Transactional(readOnly = true)
	public List<Towens> allTowens() {
		Iterable<Towens> iterable = towensRepository.findAll();
		return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
	}

	@Transactional
	public String deleteTowens(Long id) throws ElementNotFoundException {
		Towens towens = towensRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент towens id=" + id + " не найден для удаления"));
		towensRepository.delete(towens);
		return "данные id=" + id + " towens удалены";
	}

	@Transactional
	public Towens patchTowens(Long id, Towens towens) throws ElementNotFoundException {
		Towens wasTowens = towensRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Элемент towens id=" + id + " не найден для обновления"));

		wasTowens.setTown_name(towens.getTown_name());
		wasTowens.setDistance(towens.getDistance());

		return towensRepository.save(wasTowens);
	}


}


