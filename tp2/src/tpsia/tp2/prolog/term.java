package tpsia.tp2.prolog;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

/**********************************************************
 *  T E R M
 **********************************************************/

class term {
    int type;
    public final static int EQ=211,OPEN=212,NUMBER=213,FUNCTOR=214;
    String name;//the functor or constant name.
    String varname;//the name this term would have as a variable
    String qname;/*the name with quotes, if necessary.*/
    int arity;//the number of arguments in a functor.
    term[] arg;//the arguments in case of functor;

    public final static int MAXARG=12;//the maximum number of arguments
    static term emptylist;//the unique empty list
    static String varstart="_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String normalstart=
        "abcdefghijklmnopqrstuvwxyz'+-*/\\^<>=`~:.?@#$&";
    static String numchar="1234567890";
    static int NaN=Integer.MIN_VALUE;/*Not a Number*/

    term() { /*make anonymous variable*/
        type=OPEN;
        arity=0;
    }
    term(String s) { /*named variable*/
        type=OPEN;
        arity=0;
        varname=s;
    }
    term(prologop pre1,term t) { /*unary operator*/
        type=FUNCTOR;
        name=pre1.name;
        qname=getqname(name);
        arity=0;
        addarg(t);
    }
    static term newconstant(String n)
    {return newconstant(n,getqname(n));}
    static term newconstant(String n,String qn) {
        /*make a constant (for example abc)*/
        term t=new term();
        t.type=FUNCTOR;
        t.name=n;
        t.qname=qn;
        return t;
    }

    static String getqname(String inp)
    /*decides wether a name should be quoted.*/
    {if(inp.length()!=0&&
        prologtokenizer.in(inp.charAt(0),normalstart))  {
        boolean simple1=true,simple2=true;
        for(int i=0;i<inp.length();i++)
            if(!prologtokenizer.in(inp.charAt(i),
                                   prologtokenizer.normalchar))
                {simple1=false;break;}
        if(!simple1)
            for(int i=0;i<inp.length();i++)
                if(!prologtokenizer.in(inp.charAt(i),
                                       prologtokenizer.opchar))
                    {simple2=false;break;}
        if(simple1||simple2)
            return inp;
    }
    return "'"+inp+"'";
    }

    term(term t,prologop in1,term t2) { /*infix operator*/
        type=FUNCTOR;
        name=in1.name;
        qname=getqname(name);
        arity=0;
        addarg(t);
        addarg(t2);
    }
    term(int n) { /*number*/
        type=NUMBER;
        arity=n;
    }

    static term asciilist(String s) {
        /*make a list of asciivalues*/
        term t=emptylist;
        for(int i=s.length()-1;i>=0;i--)
            t=makelist(new term((int)s.charAt(i)),t);
        return t;
    }

    static String readasciilist(term t) {
        /*make a string from a list of asciivalues*/
        StringBuffer buf=new StringBuffer();
        term num;
        t=skipeq(t);
        while(t.name!=emptylist.name) {
            if(t.type!=t.FUNCTOR||t.name!=prologop.listcons.name)
                return null;
            num=skipeq(t.arg[0]);
            if(num.type!=NUMBER||num.arity<0||num.arity>255)
                return null;
            buf.append((char)num.arity);
            t=skipeq(t.arg[1]);
        }
        return buf.toString();
    }

    static term makelist(term head,term tail)
    {return new term(head,prologop.listcons,tail);}

    void functor(String s)/*make this term a functor*/
    {functor(s,getqname(s));}
    void functor(String s,String qs) { /*make this term a functor*/
        type=FUNCTOR;
        name=s;
        qname=qs;
        arity=0;
    }

    void addarg(term g) { /*add an argument to this functor*/
        if(arg==null)
            arg=new term[MAXARG];
        arg[arity]=g;
        arity++;
    }

    static void is(term x,term y) {
        /*instatiate X to being the same as Y. */
        if(x==y)
            return;
        x.arity=0;
        x.type=EQ;
        x.addarg(y);
    }

    static term skipeq(term t)
    {
        while(t.type==EQ)
            t=t.arg[0];
        return t;
    }
    static boolean equal(term a,term b) {
        a=skipeq(a);
        b=skipeq(b);
        if(a.type!=b.type)
            return false;
        if(a.type==NUMBER)
            return a.arity==b.arity;
        if(a.type==OPEN)
            return a==b;
        if(!a.name.equals(b.name)||a.arity!=b.arity)
            return false;
        for(int i=0;i<a.arity;i++)
            if(!equal(a.arg[i],b.arg[i]))
                return false;
        return true;
    }

    static boolean match(term in1,term in2, Stack substitutions) {
        /*match to variables. all variables that are instantiated
          are added to substitutions.*/
        Stack s=new Stack();
        Stack t=new Stack();
        term top1,top2;
        s.push(in1);
        t.push(in2);
        int height=substitutions.size();
        /*Instead of stacking pairs I have a pair of stacks. Both
          stacks will have the same number of elements.*/
        while(!s.empty()) {
            top1=skipeq((term)s.pop());
            top2=skipeq((term)t.pop());
            if(top1.type==OPEN) {
                is(top1,top2);
                substitutions.push(top1);
            } else if(top2.type==OPEN) {
                is(top2,top1);
                substitutions.push(top2);
            } else if(top1.type!=top2.type) {
                unmatch(substitutions,height);
                return false;
            } else if(top1.type==NUMBER) {
                if(top1.arity!=top2.arity)
                    { unmatch(substitutions,height);
                    return false;
                    }
            }
            else if(top1.arity!=top2.arity||!top1.name.equals(top2.name)) {
                unmatch(substitutions,height);
                return false;
            } else for(int i=0;i<top1.arity;i++) {
                s.push(top1.arg[i]);
                t.push(top2.arg[i]);
            }
        }
        return true;
    }

    void open() { /*Make this term an open variable*/
        type=OPEN;
        arity=0;
        arg=null;
    }

    static void unmatch(Stack subst,int height) {
        /*bring subst to the given height. Undo the instantations*/
        while(subst.size()>height)
            ((term)subst.pop()).open();
    }

    static int numbervalue(term t) {
        /*calculate the number represented by a term.
          the term must consist of NUMBER constants kept together
          by +,-,*,/,mod.*/
        t=skipeq(t);
        if(t.type==NUMBER)
            return t.arity;
        if(t.type!=FUNCTOR||t.arity==0)
            return NaN;
        int a1=numbervalue(t.arg[0]);
        if(a1==NaN)
            return NaN;
        if(t.arity==1) {
            if(t.name.equals("-"))
                return -a1;
            if(t.name.equals("+"))
                return  a1;
        }
        if(t.arity!=2)  return NaN;
        int a2=numbervalue(t.arg[1]);
        if(a2==NaN)     return NaN;
        if(t.name.equals("+"))    return a1+a2;
        if(t.name.equals("*"))    return a1*a2;
        if(t.name.equals("-"))    return a1-a2;
        if(t.name.equals("/")) {
            if(a2==0)   return NaN;
            return a1/a2;
        }
        if(t.name.equals("mod")) {
            if(a2==0)  return NaN;
            return a1%a2;
        }
        return NaN;
    }

    /*displaying terms as strings:*/

    /*ebbinghaus makes nice names for new variables.*/
    static String[] vowel={"a","u","i","o","e"};
    static String[] conso1={"B","D","F","G","H","K","L","M","N","Z","X"};
    static String[] conso2={"","g","f","l","n","m","s","p","t"};
    static int count=0;
    static int total=0;

    static String ebbinghaus() {
        /*return a nonsense syllable as varname*/
        if(total==0)
            total=conso1.length*vowel.length*conso2.length;
        count++;
        return   conso1[count%conso1.length]+
            vowel[count%vowel.length]+
            conso2[count%conso2.length];
    }
    String varname() {
        /*the name of this term if it were a variable*/
        if(varname==null)
            varname=ebbinghaus();
        return varname;
    }

    static String tailstring(term t,boolean quotes) {
        /*write a term as the end of a string (without [)*/
        t=skipeq(t);
        if(t.type==FUNCTOR)
            {if(t.name==emptylist.name)
                return "]";
            if(t.name==prologop.listcons.name)
                return ","+toString(t.arg[0],1000,quotes)
                    +tailstring(t.arg[1],quotes);
            }
        return "|"+t.toString()+"]";
    }

    static String toString(term t,int level,boolean q) {
        /*write a term as a string.*/
        t=skipeq(t);
        switch(t.type) {
        case NUMBER:
            return t.arity+"";
        case FUNCTOR:
            if(t.name==prologop.listcons.name)/*try list display*/
                return "["+toString(t.arg[0],999,q)+tailstring(t.arg[1],q);
            if(t.arity==0)
                if(q)
                    return t.qname;
                else
                    return t.name;
            prologop o1=prologop.preop(t.name);/*try prefix operator*/
            if(t.arity==1&&o1!=null)
                {    if(o1.priority<=level)
                    return t.name+" "+
                        toString(t.arg[0],o1.rightunderlevel(),q);
                return "("+t.name+" "+
                    toString(t.arg[0],o1.rightunderlevel(),q)+")";
                }
            o1=prologop.postop(t.name);/*try postfix*/
            if(t.arity==1&&o1!=null) {
                if(o1.priority<=level)
                    return toString(t.arg[0],o1.leftunderlevel(),q)
                        +" "+t.name;
                return "("+toString(t.arg[0],o1.leftunderlevel(),q)
                    +" "+t.name+")";
            }
            o1=prologop.inop(t.name);/*try infix*/
            if(t.arity==2&&o1!=null) {
                String s=toString(t.arg[0],o1.leftunderlevel(),q)+
                    " "+t.name+" "+
                    toString(t.arg[1],o1.rightunderlevel(),q);
                if(o1.priority<=level)
                    return s;
                return "("+s+")";
            }
            String s;
            if(q)
                s=t.qname;
            else
                s=t.name;
            s+="("+t.toString(t.arg[0],999,q);
            for(int i=1;i<t.arity;i++)
                s+=","+toString(t.arg[i],999,q);
            return s+")";
        case OPEN:
            return t.varname();
        }
        return null;
    }
    public String toString()
    {return toString(this,1201,true);}
    public String toString(boolean q)
    {return toString(this,1201,q);}

    /************** P A R S I N G *************/

    public static term getTerm(prologtokenizer tok)
    /*get a term out of a tokenizer.*/
    {return parset(tok,new Hashtable(),1200);}

    static term parset(prologtokenizer tok,Hashtable vars, int level) {
        /*parse a tokenized string to a term, with operatorlevel<=level
          The hashtable contains the variables that are allready made. */
        if(!tok.more())
            return null;
        int tokpos=tok.getpos();

        prologop p1=prologop.preop(tok.gettoken());
        if(p1!=null&&p1.priority<=level) {
            term t1=parset(tok,vars,p1.rightunderlevel());
            if(t1!=null) {
                t1=readfurther(new term(p1,t1),p1.priority,tok,vars,level);
                if(t1!=null)
                    return t1;
            }
        }/*:the paring of [prefixop][term]. (like -4)*/

        tok.jumpto(tokpos);
        term t=parsetbasic(tok,vars);
        if(t!=null) {
            t=readfurther(t,0,tok,vars,level);
            if(t!=null)
                return t;
        }/*:the parsing of [term]*/
        tok.jumpto(tokpos);
        return null;
    }

    static term readfurther(term t1,int t1level,
                            prologtokenizer tok,Hashtable vars,int level) {
        /*try to add postfix and infix operators*/
        int tokpos=tok.getpos();
        term t;
        if(!tok.more())
            return t1;
        prologop p1=prologop.postop(tok.gettoken());
        if(p1!=null&&p1.priority<=level&&t1level<p1.leftunderlevel())  {
            t=readfurther(new term(p1,t1),p1.priority,
                          tok,vars,level);
            if(t!=null)
                return t;
        }
        tok.jumpto(tokpos);
        p1=prologop.inop(tok.gettoken());
        if(p1!=null&&p1.priority<=level&&t1level<p1.leftunderlevel()) {
            term t2=parset(tok,vars,p1.rightunderlevel());
            if(t2!=null) {
                t=fixin(t1,p1,t2,tok,vars,level);
                if(t!=null)
                    return t;
            }
        }
        tok.jumpto(tokpos);
        return t1;/*don't take next operator, wrong level.*/
    }

    static term fixin(term t1, prologop o1,term t2,
                      prologtokenizer tok,Hashtable vars,int highlevel)  {
        if(!tok.more())
            return new term(t1,o1,t2);
        int tokpos=tok.getpos();
        term t;
        prologop o2=prologop.inop(tok.gettoken());
        if(o2!=null&&o2.priority<=highlevel) {
            term t3=parset(tok,vars,o2.rightunderlevel());
            if(t3!=null) {
                if(o1.under(o1,o2)==1) {
                    t=fixin(new term(t1,o1,t2),o2,t3,tok,vars,highlevel);
                    if(t!=null)
                        return t;
                } else if(o1.under(o1,o2)==2)
                    return new term(t1,o1,new term(t2,o2,t3));
                //fail: operators cannot be combined
            }//if t3 is null: fail.
        } else { /*there is no or a too high operator. succeed*/
            tok.jumpto(tokpos);
            return new term(t1,o1,t2);
        }
        tok.jumpto(tokpos);
        return null;
    }

    static term listread(prologtokenizer tok,Hashtable vars) {
        /*listread transforms a tokenized string  3,4,5] to a list*/
        int tokpos=tok.getpos();
        term head=parset(tok,vars,999);
        if(head==null) {
            tok.jumpto(tokpos);
            return null;
        }
        int afterhead=tok.getpos();
        if("]".equals(tok.gettoken()))
            return makelist(head,emptylist);
        tok.jumpto(afterhead);
        if(",".equals(tok.gettoken())) {
            term tail=listread(tok,vars);
            if(tail==null)
                {tok.jumpto(tokpos);
                return null;
                }
            return makelist(head,tail);
        }
        tok.jumpto(afterhead);
        if("|".equals(tok.gettoken())) {
            term tail=parset(tok,vars,699);/*under =*/
            if(tail!=null&&"]".equals(tok.gettoken()))
                return makelist(head,tail);
        }
        tok.jumpto(tokpos);
        return null;
    }

    static term parsetbasic(prologtokenizer tok,Hashtable vars) {
        /*null-pointer indicates failure.*/
        term t;
        if(!tok.more())
            return null;
        int tokpos=tok.getpos();
        String f1=tok.peek();
        char first=f1.charAt(0);
        if(f1.equals("!")) {
            tok.gettoken();
            t=newconstant(prologop.CUT,prologop.CUT);
            return t;
        }
        if(f1.equals("(")) {
            tok.gettoken();
            t=parset(tok,vars,1200);
            if(")".equals(tok.gettoken()))
                return t;
            else {
                tok.jumpto(tokpos);
                return null;
            }
        }
        if(f1.equals("[")) {
            tok.gettoken();
            if("]".equals(tok.peek())) {
                tok.gettoken();
                return emptylist;
            }
            return listread(tok,vars);
        }
        if(first=='"') {
            tok.gettoken();
            return asciilist(f1.substring(1));
        }

        if(tok.in(first,varstart)) {
            tok.gettoken();
            term old=(term)vars.get(f1);
            if(old!=null&&!f1.equals("_"))
                return old;
            t=new term(f1);
            vars.put(f1,t);
            return t;
        }
        else if(tok.in(first,numchar)) {
            int n;
            try{n=Integer.parseInt(tok.gettoken());}
            catch(NumberFormatException e)
                {return null;}
            return new term(n);
        }
        else if(tok.in(first,normalstart)) {
            tok.gettoken();
            t=new term();
            if(first==39)
                t.functor(f1.substring(1));
            else
                t.functor(f1);
            if("(".equals(tok.peek())) {  //try adding arguments
                tok.gettoken();//get the (
                for(int arc=0;arc<MAXARG;arc++) {
                    term q=parset(tok,vars,999);/*under , */
                    if(q==null) return null;//failure
                    t.addarg(q);
                    if(")".equals(tok.peek())) {
                        tok.gettoken();
                        return t;
                    }
                    if(!",".equals(tok.gettoken())) {
                        tok.jumpto(tokpos);
                        return null;
                    }
                }
            }
            return t;
        }
        tok.jumpto(tokpos);
        return null;
    }
    /*make a copy of a term:*/
    term copy()
    {return copy(new Hashtable());}

    term copy(Hashtable h) {
        term t;
        switch(type)
            {case EQ: return arg[0].copy();
            case NUMBER: return new term(arity);
            case OPEN:
                t=(term)h.get(this);
                if(t==null) {
                    t=new term();
                    h.put(this,t);
                }
                return t;
            case FUNCTOR:
                t=newconstant(name,qname);
                for(int i=0;i<arity;i++)
                    t.addarg(arg[i].copy(h));
                return t;
            }
        return null;
    }

    static void vars(term t,Vector v) {
        /*put all vars in term t in the vector*/
        t=skipeq(t);
        if(t.type==OPEN) {
            if(!v.contains(t))
                v.addElement(t);
        }
        else if(t.type==FUNCTOR)
            for(int i=0;i<t.arity;i++)
                vars(t.arg[i],v);
    }
}
