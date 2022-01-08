package com.nighthawk.csa.mvc.DataOps.genericDataModel;

import lombok.Getter;
import lombok.Setter;

/* This is wrapper class...
 Objective would be to push more functionality into this Class to enforce consistent definition
 */
@Getter
@Setter
public abstract class Generics {
	public final String masterType = "Generic";
	private String type;	// extender should define themself

	// generic enumerated interface
	public interface KeyTypes {
		String name();
	}

	// force these methods to allow sort functionality to be handled abstractly
	public abstract KeyTypes getKey();
	public abstract void setKey(KeyTypes key);
	public abstract String toString();
}