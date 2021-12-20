package com.example.pet_project.dto;

import com.example.pet_project.entity.Towens;
import lombok.Data;


@Data
public class TowensDto {
	private Long id;

	private String town_name;

	private Integer distance;

	public static TowensDto toModelTowens(Towens towens) {
		TowensDto towensDto=new TowensDto();
		towensDto.setId(towens.getId());
		towensDto.setTown_name(towens.getTown_name());
		towensDto.setDistance(towens.getDistance());

		return towensDto;

	}


}
