package com.example.pet_project.dto;


import com.example.pet_project.entity.Shippings;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ShippingsDto {

	private Long id;


	private LocalDate start_data;
	private LocalDate end_data;

	private ItemsDto itemsDto;
	private TowensDto towensDto;


	public static ShippingsDto toSippingslDto(Shippings shippings) {
		ShippingsDto shippingsDto = new ShippingsDto();

		shippingsDto.setId(shippings.getId());
		shippingsDto.setStart_data(shippings.getStart_data());
		shippingsDto.setEnd_data(shippings.getEnd_data());


		if (shippings.getItem() == null && shippings.getTowen() == null) {
			return shippingsDto;
		} else if (shippings.getItem() == null && shippings.getTowen() != null) {
			shippingsDto.setTowensDto(TowensDto.toModelTowens(shippings.getTowen()));
			return shippingsDto;
		} else if (shippings.getItem() != null && shippings.getTowen() == null) {
			shippingsDto.setItemsDto(ItemsDto.toItemsDto(shippings.getItem()));
			return shippingsDto;
		}

		shippingsDto.setTowensDto(TowensDto.toModelTowens(shippings.getTowen()));
		shippingsDto.setItemsDto(ItemsDto.toItemsDto(shippings.getItem()));
		return shippingsDto;


	}

}
