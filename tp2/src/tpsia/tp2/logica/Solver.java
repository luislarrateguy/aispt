package tpsia.tp2.logica;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

/*******************************************************************************
 * S O L V E R
 ******************************************************************************/
class Solver {
	Stack todo;

	Stack done;

	Stack subst;

	Thread mythread;

	Program lib;

	Vector uservars;

	PrologTokenizer inp;

	Stack consultstack;

	static Hashtable bi_pred;

	long time;

	static Term ASK;

	boolean wait;

	void bi(String s, int a, int n) {
		bi_pred.put(s + "/" + a, new Integer(n));
	}

	Solver(Program l) {
		if (ASK == null) {
			ASK = Term.newconstant("ask user ");
			bi_pred = new Hashtable();
			bi("repeat", 0, 1);
			bi("fail", 0, 2);
			bi("true", 0, 3);
			bi("!", 0, 4);
			bi("=", 2, 5);
			bi("is", 2, 6);
			bi("=:=", 2, 7);
			bi("<", 2, 8);
			bi(">", 2, 9);
			bi("<=", 2, 10);
			bi(">=", 2, 11);
			bi("=\\=", 2, 12);
			bi("get", 1, 15);
			bi("get0", 1, 16);
			bi("seen", 0, 17);
			bi("nl", 0, 20);
			bi("put", 1, 21);
			bi("told", 0, 22);
			bi("newprogram", 0, 23);
			bi(Prologop.listcons.name, 2, 25);
			bi("addStatement", 1, 26);
			bi("addStatementz", 1, 26);
			bi("addStatementa", 1, 27);
			bi("retract", 1, 28);
			bi("writeprogram", 0, 29);
			bi("op", 3, 30);
			bi("var", 1, 31);
			bi("nonvar", 1, 32);
			bi("atom", 1, 33);
			bi("integer", 1, 34);
			bi("=..", 2, 35);
			bi("name", 2, 36);
			bi("==", 2, 37);
			bi(";", 2, 38);
			bi(",", 2, 39);
			bi("compound", 1, 40);
			bi("random", 3, 41);
			bi("\\==", 2, 42);
			bi("writenoq", 1, 43);
			bi("writeq", 1, 44);
		}
		lib = l;
	}

	int getbinum(Term r) {
		Integer i = (Integer) bi_pred.get(Program.searchkey(r));
		if (i == null)
			return -1;
		return i.intValue();
	}

	void stacktodo(Term q, Rack r) { /*
										 * push all goals in q on the todo
										 * stack, with parent r.
										 */
		if (q == null)
			return;
		if (q.type == Term.FUNCTOR && q.name == Prologop.AND) {
			stacktodo(q.arg[1], r);
			stacktodo(q.arg[0], r);
		} else
			todo.push(new Rack(q, r));
	}

	void query(Term q) {
		time = System.currentTimeMillis();
		todo = new Stack();
		done = new Stack();
		subst = new Stack();
		todo.push(new Rack(ASK, null));
		uservars = new Vector();
		Term.vars(q, uservars);
		stacktodo(q, null);
		run(5000); // max iters
		// mythread=new Thread(this);
		// mythread.start();
	}

	static int FALSE = 0, TRUE = 1, ERROR = -1;

	int solve(Rack r) {
		/* return one of FALSE=0,TRUE=1,ERROR=-1 */
		if (r.solveoption == Rack.NOTAGAIN)
			return FALSE;
		Term rpred = Term.skipeq(r.pred);
		if (rpred.type != Term.FUNCTOR)
			return ERROR;
		int bi = getbinum(rpred);
		if (bi != -1) {
			char c;
			Term t, l;

			switch (bi) {
			case 1:
				return TRUE;
			case 2:
				return FALSE;
			case 3:
				r.solveoption = Rack.NOTAGAIN;
				return TRUE;
			case 4:
				r.solveoption = Rack.NOTAGAIN;
				Rack todo1;
				Rack realparent = r.parent;
				while (realparent != null
						&& (realparent.pred.name == Prologop.AND || realparent.pred.name == Prologop.OR)) {
					realparent = realparent.parent;
				}
				int todop = todo.size() - 1;
				while (todop >= 0) {
					todo1 = (Rack) todo.elementAt(todop);
					if (todo1.parent != null
							&& (todo1.parent.pred.name == Prologop.AND || todo1.parent.pred.name == Prologop.OR)) {
						todo1.parent = realparent;
						todop--;
					} else
						break;
				}
				r.parent = realparent;

				while (!done.empty()) {
					if (done.peek() != r.parent)
						done.pop();
					else
						break;
				}
				if (r.parent != null)
					r.parent.solveoption = Rack.NOTAGAIN;
				return TRUE;
			case 5:
				r.solveoption = Rack.NOTAGAIN;
				if (Term.match(rpred.arg[0], rpred.arg[1], subst))
					return TRUE;
				else
					return FALSE;
			case 6:
				r.solveoption = Rack.NOTAGAIN;
				int n = Term.numbervalue(rpred.arg[1]);
				if (n == Term.NaN)
					return ERROR;
				if (Term.match(rpred.arg[0], new Term(n), subst))
					return TRUE;
				else
					return FALSE;
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
				r.solveoption = Rack.NOTAGAIN;
				int n1 = Term.numbervalue(rpred.arg[0]);
				int n2 = Term.numbervalue(rpred.arg[1]);
				if (n1 == Term.NaN || n2 == Term.NaN) {
					System.out.println("No number");
					return ERROR;
				}
				if ((bi == 8 && n1 < n2) || (bi == 9 && n1 > n2)
						|| (bi == 10 && n1 <= n2) || (bi == 11 && n1 >= n2)
						|| (bi == 7 && n1 == n2) || (bi == 12 && n1 != n2))
					return TRUE;
				else
					return FALSE;
			case 15:
				r.solveoption = Rack.NOTAGAIN;
				do {
					c = inp.get0();
				} while (c <= 32);
				if (Term.match(rpred.arg[0], new Term((int) c), subst))
					return TRUE;
				else
					return FALSE;
			case 16:
				r.solveoption = Rack.NOTAGAIN;
				c = inp.get0();
				if (Term.match(rpred.arg[0], new Term((int) c), subst))
					return TRUE;
				else
					return FALSE;
			case 17:
				r.solveoption = Rack.NOTAGAIN;
				inp = null;
				return TRUE;
			case 19:
				r.solveoption = Rack.NOTAGAIN;
				return TRUE;
			case 20:
				r.solveoption = Rack.NOTAGAIN;
				return TRUE;
			case 21:
				r.solveoption = Rack.NOTAGAIN;
				t = Term.skipeq(rpred.arg[0]);
				if (t.type == Term.NUMBER && t.arity >= 0 && t.arity < 256) {
					return TRUE;
				}
				return ERROR;
			case 22:
				r.solveoption = Rack.NOTAGAIN;
				return TRUE;
			case 23:
				r.solveoption = Rack.NOTAGAIN;
				lib = new Program();
				return TRUE;
			case 24:
			case 25:
				r.solveoption = Rack.NOTAGAIN;
				t = Term.skipeq(rpred.arg[0]);
				if (t.type != Term.FUNCTOR || t.arity != 0)
					return ERROR;
				return TRUE;
			case 26:
				r.solveoption = Rack.NOTAGAIN;
				if (addStatement(Program.gramconvert(rpred.arg[0].copy())))
					return TRUE;
				return ERROR;
			case 27:
				r.solveoption = Rack.NOTAGAIN;
				if (Program.addStatementa(lib.user, Program
						.gramconvert(rpred.arg[0].copy())))
					return TRUE;
				return ERROR;
			case 28:
				r.solveoption = Rack.NOTAGAIN;
				return retract(rpred.arg[0]);
			case 29:
				r.solveoption = Rack.NOTAGAIN;
				return TRUE;
			case 30:
				r.solveoption = Rack.NOTAGAIN;
				Term nam = Term.skipeq(rpred.arg[2]);
				if (nam.type != Term.FUNCTOR || nam.arity != 0)
					return ERROR;
				Term ty = Term.skipeq(rpred.arg[1]);
				if (ty.type != Term.FUNCTOR || ty.arity != 0)
					return ERROR;
				if (Prologop.addoperator(nam.name, ty.name, Term
						.numbervalue(rpred.arg[0])))
					return TRUE;
				return ERROR;
			case 31:
				r.solveoption = Rack.NOTAGAIN;
				t = Term.skipeq(rpred.arg[0]);
				if (t.type == Term.OPEN)
					return TRUE;
				return FALSE;
			case 32:
				r.solveoption = Rack.NOTAGAIN;
				t = Term.skipeq(rpred.arg[0]);
				if (t.type != Term.OPEN)
					return TRUE;
				return FALSE;
			case 33:
				r.solveoption = Rack.NOTAGAIN;
				t = Term.skipeq(rpred.arg[0]);
				if (t.type == Term.FUNCTOR && t.arity == 0)
					return TRUE;
				return FALSE;
			case 34:
				r.solveoption = Rack.NOTAGAIN;
				t = Term.skipeq(rpred.arg[0]);
				if (t.type == Term.NUMBER)
					return TRUE;
				return FALSE;
			case 35:
				r.solveoption = Rack.NOTAGAIN;
				Term left = Term.skipeq(rpred.arg[0]);
				if (left.type == Term.FUNCTOR) {
					Term tail = Term.emptylist;
					for (int i = left.arity - 1; i >= 0; i--)
						tail = Term.makelist(left.arg[i], tail);
					Term head = Term.newconstant(left.name, left.qname);
					if (Term.match(Term.makelist(head, tail), rpred.arg[1],
							subst))
						return TRUE;
					return FALSE;
				}
				Term right = Term.skipeq(rpred.arg[1]);
				if (right.type == Term.FUNCTOR
						&& right.name == Prologop.listcons.name) {
					Term h = Term.skipeq(right.arg[0]);
					if (h.type == Term.FUNCTOR && h.arity == 0) {
						t = Term.newconstant(h.name, h.qname);
						l = Term.skipeq(right.arg[1]);
						while (l != Term.emptylist) {
							if (t.arity == Term.MAXARG
									|| l.type != Term.FUNCTOR
									|| l.name != Prologop.listcons.name)
								return ERROR;
							t.addarg(Term.skipeq(l.arg[0]));
							l = Term.skipeq(l.arg[1]);
						}
						if (Term.match(left, t, subst))
							return TRUE;
						return FALSE;
					}
				}
				return ERROR;
			case 36:
				r.solveoption = Rack.NOTAGAIN;
				t = Term.skipeq(rpred.arg[0]);
				if (t.type == Term.FUNCTOR && t.arity == 0) {
					if (Term.match(rpred.arg[1], Term.asciilist(t.name), subst))
						return TRUE;
					return FALSE;
				}
				String str = Term.readasciilist(rpred.arg[1]);
				if (str != null
						&& Term.match(rpred.arg[0], Term.newconstant(str),
								subst))
					return TRUE;
				return FALSE;
			case 37:
				r.solveoption = Rack.NOTAGAIN;
				if (Term.equal(rpred.arg[0], rpred.arg[1]))
					return TRUE;
				else
					return FALSE;
			case 38:
				if (r.solveoption == Rack.UNKNOWN) {
					r.solveoption = Rack.BUILTIN;
					stacktodo(rpred.arg[0], r);
					return TRUE;
				}
				r.solveoption = Rack.NOTAGAIN;
				stacktodo(rpred.arg[1], r);
				return TRUE;
			case 39:
				r.solveoption = Rack.NOTAGAIN;
				stacktodo(rpred.arg[1], r);
				stacktodo(rpred.arg[0], r);
				return TRUE;
			case 40:
				r.solveoption = Rack.NOTAGAIN;
				t = Term.skipeq(rpred.arg[0]);
				if (t.type == Term.FUNCTOR && t.arity > 0)
					return TRUE;
				return FALSE;
			case 41:
				r.solveoption = Rack.NOTAGAIN;
				int a = Term.numbervalue(rpred.arg[0]);
				int b = Term.numbervalue(rpred.arg[1]);
				if (a <= b && a != Term.NaN && b != Term.NaN) {
					if (Term.match(rpred.arg[2], new Term(a
							+ (int) (Math.random() * (b - a))), subst))
						return TRUE;
					return FALSE;
				}
				return ERROR;
			case 42:
				r.solveoption = Rack.NOTAGAIN;
				if (Term.equal(rpred.arg[0], rpred.arg[1]))
					return FALSE;
				else
					return TRUE;
			case 43:
				r.solveoption = Rack.NOTAGAIN;
				return TRUE;
			case 44:
				r.solveoption = Rack.NOTAGAIN;
				return TRUE;
			default:
				System.out.println("bipred missing.");
				return ERROR;
			}
		}// OD switch, OD bi!=-1
		if (rpred == ASK) {
			if (uservars.size() == 0)
				return TRUE;
			substwrite();
			return FALSE;
		}
		/*
		 * No builtin predicate. Get a fitting rule out the clause lib, match
		 * the head, and stack the body.
		 */
		if (r.solveoption == Rack.UNKNOWN) {
			r.clauses = lib.get(rpred);
			if (r.clauses == null) {
				System.out.println("undefined predicate: "
						+ Program.searchkey(rpred));
				return FALSE;
			}
		} else if (r.clauses != Term.emptylist)
			r.clauses = r.clauses.arg[1];
		r.solveoption = 1;
		Term theclause;
		while (r.clauses != Term.emptylist) {
			theclause = r.clauses.arg[0].copy();
			if (Term.match(rpred, Program.head(theclause), subst)) {
				stacktodo(Program.body(theclause), r);
				return TRUE;
			}
			r.clauses = r.clauses.arg[1];
		}
		return FALSE;
	}

	public void run(int max_iter) {
		Rack current;
		wait = false;
		int substnum;

		int iter = 0;
		while (iter++ < max_iter) {
			if (todo.size() == 0) {
				System.out.println("\nyes");
				return;
			}
			current = (Rack) todo.pop();
			int v = solve(current);
			if (v == ERROR) {
				do {
					System.out.println("Error in: " + current.pred);
					current = current.parent;
				} while (current != null);
				return;
			} else if (v == TRUE) {
				current.substdone = subst.size();
				done.push(current);
			} else {// v==FALSE
				current.solveoption = Rack.UNKNOWN;
				todo.push(current);
				if (done.isEmpty())
					return;
				current = (Rack) done.pop();
				while (((Rack) todo.peek()).parent == current) {
					todo.pop();
				}
				todo.push(current);
				if (!done.isEmpty())
					substnum = ((Rack) done.peek()).substdone;
				else
					substnum = 0;
				Term.unmatch(subst, substnum);
			}
		}
	}

	Term executepred(Term X) {
		/* returns A if X == (:-A), or null. */
		if (X == null)
			return null;
		X = Term.skipeq(X);
		if (X.type == Term.FUNCTOR && X.arity == 1 && X.name.equals(":-"))
			return X.arg[0];
		return null;
	}

	boolean addStatement(Term X) {
		return Program.addStatement(lib.user, X);
	}

	int retract(Term t) {
		Term list = lib.get(t);
		if (list == null || list == Term.emptylist)
			return FALSE;
		if (Term.match(t, Program.head(list.arg[0]), subst)) {
			lib.user.put(lib.searchkey(t), list.arg[1]);
			return TRUE;
		}
		Term superlist = list;
		list = list.arg[1];
		while (list != Term.emptylist) {
			if (Term.match(t, list.arg[0], subst)) {
				superlist.arg[1] = list.arg[1];
				return TRUE;
			}
			superlist = list;
			list = list.arg[1];
		}
		return FALSE;
	}

	void substwrite() {
		Term g;
		Hashtable hash = new Hashtable();
		for (int i = 0; i < uservars.size(); i++) {
			g = (Term) uservars.elementAt(i);
			String rightside = "" + g;
			String leftside = g.varname();
			if (!leftside.equals("_") && !leftside.equals(rightside)) {
				hash.put(leftside, rightside);
			}
		}
		answers.addElement(hash);
	}

	Vector answers = new Vector();

	Vector getAnswers() {
		Vector ret = answers;
		answers = new Vector(); // clear for next time
		return ret;
	}
}
