package com.nighthawk.csa.algorithm.genericDataModel;

import lombok.Getter;
import lombok.Setter;

/* This is wrapper class...
 Objective would be to push more functionality into this Class to drive HTML definition
 */
@Getter
@Setter
public abstract class Generics {
	public final String masterType = "Generic";
	private String type;

	// force toString
	public abstract String toString();
}