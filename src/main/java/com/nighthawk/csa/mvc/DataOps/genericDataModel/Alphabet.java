package com.nighthawk.csa.mvc.DataOps.genericDataModel;
import com.nighthawk.csa.utility.ConsoleMethods;

public class Alphabet extends Generics {
	public enum KeyType implements KeyTypes {title, letter}
	public static KeyTypes key;
	private static final int size = 26;
	private final char letter;
	
	/*
	 * single letter object
	 */
	public Alphabet(char l)
	{
		this.setType("Alphabet");
		this.setKey(KeyType.title);

		this.letter = l;
	}

	@Override
	public KeyTypes getKey() { return Alphabet.key; }
	@Override
	public void setKey(KeyTypes key) { Alphabet.key = key; }


	/* 
	 * toString provides output based off of key setting
	 */
	@Override
	public String toString()
	{
		String output="";
		if (KeyType.letter.equals(this.getKey())) {
			output += this.letter;
		} else {
			output += super.getType() + ": " + this.letter;
		}
		return output;
	}
	
	/*
	 * upper case alphabet array
	 */
	public static Generics[] alphabetData() 
	{	
		Generics[] alphabet = new Alphabet[Alphabet.size];
		for (int i = 0; i < Alphabet.size; i++)
		{
			alphabet[i] = new Alphabet( (char)('A' + i) );
		} 	
		return alphabet;
	}
	
	/* 
	 * main to test Animal class
	 */
	public static void main(String[] args)
	{
		Generics[] alphaData = alphabetData();
		for(Generics a : alphaData)
			ConsoleMethods.println("" + a);
	}
	
}
		
	
	