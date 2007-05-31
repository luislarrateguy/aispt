package tpsia.tp2.prolog;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**********************************************************
 *  P R O G R A M
 **********************************************************/

class program {
    static Hashtable prelude;
    Hashtable user;
    /*the hashtables stores lists of clause of certain name and arity.*/
    solver sv;

    program() {
        if(prelude==null)
            makeprelude();
        user=new Hashtable();
        fillwith(user,prelude);
    }
    void setsolver(solver S) { sv=S; }

    static void makeprelude() {
        prelude=new Hashtable();
        addStatement(prelude,"member(X,[X|_]).");
        addStatement(prelude,"member(X,[_|H]):-member(X,H).");
        addStatement(prelude,"not(X):-X,!,fail.");
        addStatement(prelude,"not(X).");
        addStatement(prelude,"append([],B,B).");
        addStatement(prelude,"append([A|B],C,[A|D]):-append(B,C,D).");
        addStatement(prelude,"select([X|B],X,B).");
        addStatement(prelude,"select([A|B],X,[A|C]):-select(B,X,C).");
        addStatement(prelude,"reverse(X,XR):-reverse(X,[],XR).");
        addStatement(prelude,"reverse([],XR,XR).");
        addStatement(prelude,"reverse([H|T],TR,XR):-reverse(T,[H|TR],XR).");
        addStatement(prelude,"permutation([],[]).");
        addStatement(prelude,"permutation(LX,[X|LP]):-select(LX,X,L),permutation(L,LP).");
    }
    void remove(String s) {
        /*remove a searchkey.*/
        user.remove(s);
    }

    static void fillwith(Hashtable to,Hashtable from) {
        Enumeration enumeration=from.elements();
        term oldlist,newlist;
        
        while(enumeration.hasMoreElements()) {
            oldlist=(term)enumeration.nextElement();
            if(oldlist!=term.emptylist)
                to.put(searchkey(head(oldlist.arg[0])),copylist(oldlist));
        }
    }
    static term copylist(term list) {
        if(list==term.emptylist)
            return list;
        return term.makelist(list.arg[0],copylist(list.arg[1]));
    }
    static void listaddz(term list,term t) {
        list=term.skipeq(list);
        while(list.arg[1]!=term.emptylist)
            list=list.arg[1];
        list.arg[1]=term.makelist(t,term.emptylist);
    }

    public String toString() {
        Enumeration enumeration=user.elements();
        Vector v;
        term t;
        StringBuffer buf=new StringBuffer("\n");
        while(enumeration.hasMoreElements()) {
            term list=(term)enumeration.nextElement();
            while(list!=term.emptylist) {
                buf.append(list.arg[0] +".\n");
                list=list.arg[1];
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    static boolean addStatement(Hashtable h,String s)
    {return addStatement(h,new prologtokenizer(s).gettermdot(null));}

    static term gramconvert(term t) {
        //we assume no eq's in the term (it is parsed or a copy).
        if(t.type!=term.FUNCTOR||t.arity!=2||!t.name.equals(prologop.REWRITE))
            return t;
        term a,b;
        a=new term();/*differencelist a-b*/
        b=new term();
        t.arg[0].addarg(a);
        t.arg[0].addarg(b);
        t.arg[1]=makediflist(t.arg[1],a,b);
        t.name=prologop.ARROW;
        return t;
    }

    static term makediflist(term t,term a,term b) {
        //t has no eq's
        if(t.type!=term.FUNCTOR)
            return term.newconstant("error");
        if(t.name.equals("[]")&&t.arity==0) {
            term is=term.newconstant(prologop.MATCH);
            is.addarg(a);
            is.addarg(b);
            return is;
        }
        if(t.name.equals(prologop.listcons.name)) {
            listend(t,b);
            term is=term.newconstant(prologop.MATCH);
            is.addarg(a);
            is.addarg(t);
            return is;
        }
        if(t.name.equals(prologop.AND)) {
            term c=new term();
            t.arg[0]=makediflist(t.arg[0],a,c);
            t.arg[1]=makediflist(t.arg[1],c,b);
            return t;
        }
        if(t.name.equals(prologop.OR)) {
            t.arg[0]=makediflist(t.arg[0],a,b);
            t.arg[1]=makediflist(t.arg[1],a,b);
            return t;
        }
        t.addarg(a);
        t.addarg(b);
        return t;
    }

    static void listend(term list,term t) {
        while(list.arg[1]!=term.emptylist)
            list=list.arg[1];
        list.arg[1]=t;
    }

    static boolean addStatement(Hashtable h,term t) {
        if(t==null)  return false;
        term thead=head(t);
        if(thead==null)  return false;
        term list=(term)h.get(searchkey(thead));
        if(list==null||list==term.emptylist) {
            list=term.makelist(t,term.emptylist);
            h.put(searchkey(thead),list);
        }
        else  listaddz(list,t);
        return true;
    }

    static boolean addStatementa(Hashtable h,term t) {
        if(t==null)  return false;
        //t=term.skipeq(t);
        term thead=head(t);
        if(thead==null)  return false;
        term list=(term)h.get(searchkey(thead));
        if(list==null)
            list=term.makelist(t,term.emptylist);
        else
            list=term.makelist(t,list);
        h.put(searchkey(thead),list);
        return true;
    }

    static String searchkey(term t)
    /*calculates a key for a head, to find it in a hashtable*/
    {return t.name+"/"+t.arity;}

    term get(term head)
    /*give a predicate, it returns a list of all clauses */
    {return (term)user.get(searchkey(head));}

    static term head(term t) {
        if(t.type!=t.FUNCTOR)               return null;
        if(t.name.equals(prologop.ARROW))   return t.arg[0];
        else return t;
    }
    static term body(term t) {
        if(t.type==t.FUNCTOR&&t.name.equals(prologop.ARROW))
            return t.arg[1];
        return null;
    }
}
