package com.nighthawk.csa.algorithm.genericDataModel;
import com.nighthawk.csa.consoleUI.ConsoleMethods;

public class Cupcakes extends Generics {
	public enum KeyType {title, flavor, frosting, sprinkles}
	public static KeyType key = KeyType.title;
	private final String frosting;
	private final int sprinkles;
	private final String flavor;
	
	Cupcakes(String frosting, int sprinkles, String flavor)
	{
		this.setType("Cupcakes");
		this.frosting = frosting;
		this.sprinkles = sprinkles;
		this.flavor = flavor;
	}
	
	/* 
	 * toString provides output based off of this.key setting
	 */
	@Override
	public String toString() {		
		String output="";
		switch(key) {
		case flavor:
			output += this.flavor;
			break;
		case frosting:
			output += this.frosting;
			break;
		case sprinkles:
			output += "00" + this.sprinkles;
			output = output.substring(output.length()-2);
			break;
		case title:
		default:
			output = super.getType() + ": " + this.flavor + ", " + this.frosting + ", " + this.sprinkles;
		}
		return output;
	}
	
	public static Generics[] cupCakeData() {
		return new Generics[]{
				new Cupcakes("Red", 4, "Red Velvet"),
			    new Cupcakes("Orange", 5, "Orange"),
			    new Cupcakes("Yellow", 6, "Lemon"),
			    new Cupcakes("Green", 7, "Apple"),
			    new Cupcakes("Blue", 8, "Blueberry"),
			    new Cupcakes("Purple", 9, "Blackberry"),
			    new Cupcakes("Pink", 10, "Strawberry"),
			    new Cupcakes("Tan", 11, "Vanilla"),
			    new Cupcakes("Brown", 12, "Chocolate"),
		};
	}
	
	public static void main(String[] args)
	{
		Generics[] cupCake = cupCakeData();
		for(Generics c:  cupCake)
			ConsoleMethods.println(c);
	}
	
}
