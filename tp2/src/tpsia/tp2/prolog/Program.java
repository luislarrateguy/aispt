package tpsia.tp2.prolog;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/*******************************************************************************
 * P R O G R A M
 ******************************************************************************/

class Program {
	static Hashtable prelude;

	Hashtable user;

	/* the hashtables stores lists of clause of certain name and arity. */
	Solver sv;

	Program() {
		if (prelude == null)
			makeprelude();
		user = new Hashtable();
		fillwith(user, prelude);
	}

	void setsolver(Solver S) {
		sv = S;
	}

	static void makeprelude() {
		prelude = new Hashtable();
		addStatement(prelude, "member(X,[X|_]).");
		addStatement(prelude, "member(X,[_|H]):-member(X,H).");
		addStatement(prelude, "not(X):-X,!,fail.");
		addStatement(prelude, "not(X).");
		addStatement(prelude, "append([],B,B).");
		addStatement(prelude, "append([A|B],C,[A|D]):-append(B,C,D).");
		addStatement(prelude, "select([X|B],X,B).");
		addStatement(prelude, "select([A|B],X,[A|C]):-select(B,X,C).");
		addStatement(prelude, "reverse(X,XR):-reverse(X,[],XR).");
		addStatement(prelude, "reverse([],XR,XR).");
		addStatement(prelude, "reverse([H|T],TR,XR):-reverse(T,[H|TR],XR).");
		addStatement(prelude, "permutation([],[]).");
		addStatement(prelude,
				"permutation(LX,[X|LP]):-select(LX,X,L),permutation(L,LP).");
	}

	void remove(String s) {
		/* remove a searchkey. */
		user.remove(s);
	}

	static void fillwith(Hashtable to, Hashtable from) {
		Enumeration enumeration = from.elements();
		Term oldlist;

		while (enumeration.hasMoreElements()) {
			oldlist = (Term) enumeration.nextElement();
			if (oldlist != Term.emptylist)
				to.put(searchkey(head(oldlist.arg[0])), copylist(oldlist));
		}
	}

	static Term copylist(Term list) {
		if (list == Term.emptylist)
			return list;
		return Term.makelist(list.arg[0], copylist(list.arg[1]));
	}

	static void listaddz(Term list, Term t) {
		list = Term.skipeq(list);
		while (list.arg[1] != Term.emptylist)
			list = list.arg[1];
		list.arg[1] = Term.makelist(t, Term.emptylist);
	}

	public String toString() {
		Enumeration enumeration = user.elements();
		StringBuffer buf = new StringBuffer("\n");
		while (enumeration.hasMoreElements()) {
			Term list = (Term) enumeration.nextElement();
			while (list != Term.emptylist) {
				buf.append(list.arg[0] + ".\n");
				list = list.arg[1];
			}
			buf.append("\n");
		}
		return buf.toString();
	}

	static boolean addStatement(Hashtable h, String s) {
		return addStatement(h, new PrologTokenizer(s).gettermdot(null));
	}

	static Term gramconvert(Term t) {
		// we assume no eq's in the term (it is parsed or a copy).
		if (t.type != Term.FUNCTOR || t.arity != 2
				|| !t.name.equals(Prologop.REWRITE))
			return t;
		Term a, b;
		a = new Term();/* differencelist a-b */
		b = new Term();
		t.arg[0].addarg(a);
		t.arg[0].addarg(b);
		t.arg[1] = makediflist(t.arg[1], a, b);
		t.name = Prologop.ARROW;
		return t;
	}

	static Term makediflist(Term t, Term a, Term b) {
		// t has no eq's
		if (t.type != Term.FUNCTOR)
			return Term.newconstant("error");
		if (t.name.equals("[]") && t.arity == 0) {
			Term is = Term.newconstant(Prologop.MATCH);
			is.addarg(a);
			is.addarg(b);
			return is;
		}
		if (t.name.equals(Prologop.listcons.name)) {
			listend(t, b);
			Term is = Term.newconstant(Prologop.MATCH);
			is.addarg(a);
			is.addarg(t);
			return is;
		}
		if (t.name.equals(Prologop.AND)) {
			Term c = new Term();
			t.arg[0] = makediflist(t.arg[0], a, c);
			t.arg[1] = makediflist(t.arg[1], c, b);
			return t;
		}
		if (t.name.equals(Prologop.OR)) {
			t.arg[0] = makediflist(t.arg[0], a, b);
			t.arg[1] = makediflist(t.arg[1], a, b);
			return t;
		}
		t.addarg(a);
		t.addarg(b);
		return t;
	}

	static void listend(Term list, Term t) {
		while (list.arg[1] != Term.emptylist)
			list = list.arg[1];
		list.arg[1] = t;
	}

	static boolean addStatement(Hashtable h, Term t) {
		if (t == null)
			return false;
		Term thead = head(t);
		if (thead == null)
			return false;
		Term list = (Term) h.get(searchkey(thead));
		if (list == null || list == Term.emptylist) {
			list = Term.makelist(t, Term.emptylist);
			h.put(searchkey(thead), list);
		} else
			listaddz(list, t);
		return true;
	}

	static boolean addStatementa(Hashtable h, Term t) {
		if (t == null)
			return false;
		// t=term.skipeq(t);
		Term thead = head(t);
		if (thead == null)
			return false;
		Term list = (Term) h.get(searchkey(thead));
		if (list == null)
			list = Term.makelist(t, Term.emptylist);
		else
			list = Term.makelist(t, list);
		h.put(searchkey(thead), list);
		return true;
	}

	static String searchkey(Term t)
	/* calculates a key for a head, to find it in a hashtable */
	{
		return t.name + "/" + t.arity;
	}

	Term get(Term head)
	/* give a predicate, it returns a list of all clauses */
	{
		return (Term) user.get(searchkey(head));
	}

	static Term head(Term t) {
		if (t.type != Term.FUNCTOR)
			return null;
		if (t.name.equals(Prologop.ARROW))
			return t.arg[0];
		else
			return t;
	}

	static Term body(Term t) {
		if (t.type == Term.FUNCTOR && t.name.equals(Prologop.ARROW))
			return t.arg[1];
		return null;
	}
}
