package tpsia.tp2.prolog;

/**
 * CKI Prolog is an prolog interpreter
 * Sieuwert van Otterloo
 * http://www.students.cs.uu.nl/~smotterl/prolog
 * smotterl@cs.uu.nl
 * 1999
 */

/**
 * Note: from on the web site
 * http://www.students.cs.uu.nl/~smotterl/prolog
 * is a message:
 * "The sourcecode of CKI prolog is free. This means
 * that it can be modified by anyone."
 *
 * In addition, I received specific permission from
 * Sieuwert van Otterloo use this code for an example
 * for my Java AI book.  -Mark Watson
 */

/**
 * Note #2: Sieuwert's original code was a nice
 * Java applet with a user interface for Prolog.
 * I removed the user interface code and added an
 * API (top level Prolog class) for using the
 * Prolog engine in Java applications. -Mark Watson
 */

import java.util.*;
import java.io.*;

/*******************************************************************************
 * PROLOG
 ******************************************************************************/

public class Prolog {
	Program prog;

	Solver sv;

	static private boolean once = true;

	public Prolog() {
		Prologop.makeops();
		if (once) {
			System.out
					.println("CKI Prolog Engine. By Sieuwert van Otterloo.\n");
			once = false;
		}
		prog = new Program();
		sv = new Solver(prog);
		prog.setsolver(sv);
	}

	public void addStatement(String s) {
		Term inp = (new PrologTokenizer(s)).gettermdot(null);
		if (sv.addStatement(inp) == false) {
			System.out.println("Error addStatementing: " + s);
		}
	}

	public Vector solve(String s) {
		Term inp = (new PrologTokenizer(s)).gettermdot(null);
		sv.query(inp);
		return sv.getAnswers();
	}

	public boolean consultFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
				line = line.trim();
				if (line.startsWith("%") || line.length() < 1)
					continue;
				if (line.endsWith(".")) {
					addStatement(line);
				} else {
					while (line.endsWith(".") == false) {
						String s2 = br.readLine();
						System.out.println(s2);
						line = line + " " + s2;
					}
					addStatement(line);
				}
			}
			br.close();
			return true; // OK
		} catch (Exception e) {
			System.out.println("consultFile error: " + e);
			e.printStackTrace();
		}
		return false;
	}

}
