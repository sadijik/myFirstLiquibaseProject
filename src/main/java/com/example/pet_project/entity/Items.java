package com.example.pet_project.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Items {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;



	@Size(min=1,max = 30, message = "введите имя от 1 до 30 символов")
	private String item_name;
	@Positive(message = "введите целое число > 0")
	private Integer quantity;

	@OneToOne(mappedBy = "item",fetch = FetchType.EAGER)
	private Shippings shippings;



	public Shippings getShippings() {
		return shippings;
	}

	public void setShippings(Shippings shippings) {
		this.shippings = shippings;
	}



}
