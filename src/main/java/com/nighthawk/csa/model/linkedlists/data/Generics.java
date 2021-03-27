package com.nighthawk.csa.model.linkedlists.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Generics {
	public final String masterType = "Generic";
	private String type;

	// force toString
	public abstract String toString();
	
	// object comparison
	public int compareTo(Object o) {
		return this.toString().compareTo(o.toString());
	}
}