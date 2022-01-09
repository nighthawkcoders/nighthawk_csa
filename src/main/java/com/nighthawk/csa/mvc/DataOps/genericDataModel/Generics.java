package com.nighthawk.csa.mvc.DataOps.genericDataModel;

import com.nighthawk.csa.utility.ConsoleMethods;
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
	protected abstract KeyTypes getKey();  	// this method helps force usage of KeyTypes

	// this method is used to establish key order
	public abstract String toString();

	// static print method used by extended classes
	public static void print(Object[] objs) {
		// print 'Object' properties
		ConsoleMethods.println(objs.getClass() + " " + objs.length);

		// print 'Generics' properties
		if (objs.length > 0) {
			Generics g = (Generics) objs[0];	// Cast to Generics
			ConsoleMethods.println(
					g.getMasterType() +
					":" + g.getType() +
					" listed by " +
					g.getKey());
		}

		// print 'Object' details
		for(Object o : objs)
			ConsoleMethods.println(o);

		ConsoleMethods.println();
	}
}