package com.example.pet_project.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Towens {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Size(min = 1, max = 30, message = "введите от 0 до 30 символов")
	private String town_name;
	@Positive(message = ">=0")
	private Integer distance;

	@OneToOne(mappedBy = "towen")
	private Shippings shippings;

	public Shippings getShippings() {
		return shippings;
	}

	public void setShippings(Shippings shippings) {
		this.shippings = shippings;
	}


}
