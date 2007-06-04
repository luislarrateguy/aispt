package tpsia.tp2.prolog;

/*******************************************************************************
 * R A C K
 ******************************************************************************/

class Rack {
	Term pred;

	int solveoption;

	static int BUILTIN = -4, NOTAGAIN = -2, UNKNOWN = -1;

	Term clauses;

	int substdone;

	Rack parent;

	Rack(Term h, Rack p) {
		pred = h;
		solveoption = UNKNOWN;
		substdone = 0;
		parent = p;
	}
}
