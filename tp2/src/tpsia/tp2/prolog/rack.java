package tpsia.tp2.prolog;

/**********************************************************
 *  R A C K
 **********************************************************/

class rack {
    term pred;
    int solveoption;
    static int BUILTIN=-4,NOTAGAIN=-2,UNKNOWN=-1;
    term clauses;
    int substdone;
    rack parent;

    rack(term h,rack p) {
        pred=h;
        solveoption=UNKNOWN;
        substdone=0;
        parent=p;
    }
}
