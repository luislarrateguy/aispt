package tpsia.tp2.logica;

import java.util.Hashtable;

/*******************************************************************************
 * P R O L O G O P
 ******************************************************************************/

class Prologop {
	boolean prex, postx;

	int place, priority;

	static int pre = 1, in = 2, post = 3;

	String name;

	static String AND, OR, MATCH, ARROW, CUT, REWRITE;

	/* all operators in play are defined here: */
	static Hashtable preops, inops, postops;

	static Prologop listcons;

	Prologop() {
	}/*
		 * empty constructor. use make as a (sometimes failing) constructor
		 */

	static Prologop make(String n, String type, int prior) {
		/* returns such an operator, or null */
		if (prior < 0 || prior > 1200)
			return null;
		Prologop p = new Prologop();
		p.name = n;
		p.priority = prior;
		if (type.length() == 2 && type.charAt(0) == 'f') {
			p.place = pre;
			if (type.equals("fx"))
				p.postx = true;
			else if (type.equals("fy"))
				p.postx = false;
			else
				return null;
			return p;
		} else if (type.length() == 2 && type.charAt(1) == 'f') {
			p.place = post;
			if (type.equals("xf"))
				p.prex = true;
			else if (type.equals("fy"))
				p.prex = false;
			else
				return null;
			return p;
		} else if (type.length() == 3 && type.charAt(1) == 'f') {
			p.place = in;
			if (type.equals("xfx")) {
				p.prex = true;
				p.postx = true;
			} else if (type.equals("xfy")) {
				p.prex = true;
				p.postx = false;
			} else if (type.equals("yfx")) {
				p.prex = false;
				p.postx = true;
			}/* note that yfy would give rise to ambiguity */
			else
				return null;
			return p;
		}
		return null;
	}

	public static void makeops() {
		if (Term.emptylist != null)
			return;
		Term.emptylist = Term.newconstant("[]", "[]");

		AND = ",";
		OR = ";";
		MATCH = "=";
		ARROW = ":-";
		CUT = "!";
		REWRITE = "-->";

		preops = new Hashtable();
		inops = new Hashtable();
		postops = new Hashtable();
		addoperator("?-", "fx", 1200);//
		addoperator(ARROW, "xfx", 1200);// the if
		addoperator(ARROW, "fx", 1200);// the do in programs
		addoperator(REWRITE, "xfx", 1200);// grammar rules
		addoperator("not", "fx", 900);
		addoperator(OR, "xfy", 1100);// the ;
		addoperator(AND, "xfy", 1000);// the ,
		addoperator(MATCH, "xfx", 700);// matchable
		addoperator("==", "xfx", 700);// exactly the same
		addoperator("\\==", "xfx", 700);// not the same
		addoperator(">", "xfx", 700);// compare values
		addoperator("<", "xfx", 700);// compare values
		addoperator(">=", "xfx", 700);// compare values
		addoperator("<=", "xfx", 700);// compare values
		addoperator("is", "xfx", 700);// calculate right
		addoperator("=:=", "xfx", 700);// values equal
		addoperator("=\\=", "xfx", 700);// values unequal
		addoperator("=..", "xfx", 700);// compose a(b)=..[a,b]

		addoperator("+", "yfx", 500);
		addoperator("-", "yfx", 500);
		addoperator("-", "fx", 500);
		addoperator("+", "fx", 500);
		addoperator("*", "yfx", 400);
		addoperator("/", "yfx", 400);
		addoperator("div", "yfx", 400);
		addoperator("mod", "xfx", 300);

		listcons = make(".", "xfy", 600);
	}

	public static boolean addoperator(String s, String type, int level) {
		Prologop op = make(s, type, level);
		if (op == null)
			return false;
		if (op.place == Prologop.pre)
			preops.put(s, op);
		else if (op.place == op.in)
			inops.put(s, op);
		else
			postops.put(s, op);
		return true;
	}

	public static Prologop preop(String name) {
		return (Prologop) preops.get(name);
	}

	public static Prologop inop(String name) {
		return (Prologop) inops.get(name);
	}

	public static Prologop postop(String name) {
		return (Prologop) postops.get(name);
	}

	int under(Prologop o1, Prologop o2) {
		/*
		 * 1 means that that o1 can be under o2: like 3*4+2 2 means 2+3*4. 0
		 * means they cannot be combined. for example let <-- be xfx, then a <--
		 * b <-- c is a syntax error.
		 */
		if (o1.priority < o2.priority)
			return 1;
		if (o1.priority > o2.priority)
			return 2;
		if (!o2.prex)
			return 1;
		if (!o1.postx)
			return 2;
		return 0;
	}

	int leftunderlevel() {
		if (prex)
			return priority - 1;
		return priority;
	}

	int rightunderlevel() {
		if (postx)
			return priority - 1;
		return priority;
	}

}
