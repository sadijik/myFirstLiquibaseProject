package com.example.pet_project.repo;

import com.example.pet_project.entity.Items;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends CrudRepository<Items, Long> {

}
