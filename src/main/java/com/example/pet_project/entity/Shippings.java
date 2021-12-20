package com.example.pet_project.entity;

import lombok.*;


import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shippings {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;


	private LocalDate start_data;
	@Nullable
	private LocalDate end_data;




	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id")
	private Items item;

     @OneToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "towen_id")
	private Towens towen;

	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}

	public Towens getTowen() {
		return towen;
	}

	public void setTowen(Towens towen) {
		this.towen = towen;
	}

}
