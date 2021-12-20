package com.example.pet_project.advice;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
class Animal {
	private  String name;
	private int age;
	private String view;

	public Animal(String name, int age, String view) {
		this.name = name;
		this.age = age;
		this.view = view;
	}
}

public class P {



	public static Map<String,List<Animal>>  groupAinmal (List<Animal> list1) {
		return list1.stream().collect(Collectors.groupingBy(Animal::getView));
	}

	public static void main(String[] args) {
		List<Animal> list = new ArrayList<>();
		{
			list.add(new Animal("Тигр",23,"хищник"));
			list.add(new Animal("пес",10,"хищник"));
			list.add(new Animal("Корова",23,"травоядное"));
			list.add(new Animal("курица", 23, "птица"));
			list.add(new Animal("пингвин", 23, "птица"));
		}


		Map<String, List<Animal>> stringListMap = P.groupAinmal(list);
		System.out.println("sss "+stringListMap);
	}


}
