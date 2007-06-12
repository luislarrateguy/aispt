package tpsia.tp2.pruebas;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import tpsia.tp2.logica.Prolog;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			/**
			 * Simple example howing how to use embedded Prolog in your Java
			 * programs:
			 */
			Prolog p = new Prolog();
			
			p.addStatement("mother(kerry,mark).");
			p.addStatement("father(mark,ron).");
			p.addStatement("father(john,mark).");
			p.addStatement("suma(X,Y,C):- C is X + Y."); //:-not father(X,_).");
			
			p.addStatement("abuelo(X,Z):-father(X,Y),father(Y,Z).");
			p.addStatement("abuelo(X,Z):-father(X,Y),mother(Y,Z).");
			
			p.addStatement("abuela(X,Z):-mother(X,Y),father(Y,Z).");
			p.addStatement("abuela(X,Z):-mother(X,Y),mother(Y,Z).");

			Vector v = p.solve("abuela(X,Z).");

			System.out.println("test results:");
			// Vector v = p.solve("permutation([1,2,3],X).");
			for (int i = 0; i < v.size(); i++) {
				System.out.println();
				Hashtable the_answers = (Hashtable) v.elementAt(i);
				Enumeration enumeration = the_answers.keys();
				while (enumeration.hasMoreElements()) {
					String var = (String) enumeration.nextElement();
					String val = (String) the_answers.get(var);
					System.out.println(" var: " + var + "   val: " + val);
				}
			}
		} else if (args.length > 1) {
			/**
			 * Two arguments: a prolog file and a query
			 */
			try {
				Prolog p = new Prolog();
				p.consultFile(args[0]);
				Vector v = p.solve(args[1]);
				for (int i = 0; i < v.size(); i++) {
					System.out.println("\nNext answer:");
					Hashtable the_answers = (Hashtable) v.elementAt(i);
					Enumeration enumeration = the_answers.keys();
					while (enumeration.hasMoreElements()) {
						String var = (String) enumeration.nextElement();
						String val = (String) the_answers.get(var);
						System.out.println(" var: " + var + "   val: " + val);
					}
				}
			} catch (Exception e) {
				System.out.println("error: " + e);
				e.printStackTrace();
			}
		}

	}

}
