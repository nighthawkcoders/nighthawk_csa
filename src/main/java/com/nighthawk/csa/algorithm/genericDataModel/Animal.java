package com.nighthawk.csa.algorithm.genericDataModel;
import com.nighthawk.csa.consoleUI.ConsoleMethods;

/*
 * Animal class extends Generics and defines abstract methods
 */
public class Animal extends Generics {
	public enum KeyType {title, name, age, color} //Used with radio button selection
	public static KeyType key = KeyType.title;  //Static variable: a key that applies to all Objects
	private final String name;
	private final int age;
	private final String color;


	/* constructor
	 * 
	 */
	public Animal(String name, int age, String color)
	{
		super.setType("Animal");
		this.name = name; 
		this.age = age; 
		this.color = color; 
	}
	
	/* Generics requires toString override
	 * toString provides conditional output based off of key
	 */
	@Override
	public String toString()
	{
		String output="";
		switch(key) {
		case name:
			output += this.name;
			break;
		case age:
			output += "00" + this.age;
			output = output.substring(output.length()-2);
			break;
		case color:
			output += this.color;
			break;
		case title:
		default:
			output += super.getType() + ": " + this.name  + ", " + this.color + ", " + this.age;
		}
		return output;
		
	}
	
	/* Initialize Animal data
	 * 
	 */
	public static Generics[] animalData() {
		return new Generics[]{
				new Animal("Lion", 8, "Gold"),
				new Animal("Pig", 3, "Pink"),
				new Animal("Robin", 7, "Red"),
				new Animal("Cat", 10, "Black"),
				new Animal("Kitty", 1, "Calico"),
				new Animal("Dog", 14, "Brown")
		};
	}
	
	/* main to test Animal class
	 * 
	 */
	public static void main(String[] args)
	{
		Generics[] animData = animalData();	//shows abstract class usage
		for(Generics a : animData)
			ConsoleMethods.println("" + a);	//shows polymorphic behavior
	}

}
