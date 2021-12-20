package com.example.pet_project.dto;

import com.example.pet_project.entity.Items;

import lombok.Data;


@Data
public class ItemsDto {
	private Long id;


	private String item_name;

	private Integer quantity;


	public static ItemsDto toItemsDto(Items items) {
		ItemsDto itemsDto = new ItemsDto();
		itemsDto.setId(items.getId());
		itemsDto.setItem_name(items.getItem_name());
		itemsDto.setQuantity(items.getQuantity());

		return itemsDto;

	}
}
