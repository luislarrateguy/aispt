package tpsia.tp2.prolog;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

/**********************************************************
 *  S O L V E R
 **********************************************************/
class solver {
    Stack todo;
    Stack done;
    Stack subst;
    Thread mythread;
    program lib;
    Vector uservars;
    prologtokenizer inp;
    Stack consultstack;
    static Hashtable bi_pred;
    long time;
    static term ASK;
    boolean wait;

    void bi(String s,int a,int n)
    {bi_pred.put(s+"/"+a,new Integer(n));}

    solver(program l) {
        if(ASK==null) {
            ASK=term.newconstant("ask user ");
            bi_pred=new Hashtable();
            bi("repeat",0,1);
            bi("fail",0,2);
            bi("true",0,3);
            bi("!",0,4);
            bi("=",2,5);
            bi("is",2,6);
            bi("=:=",2,7);
            bi("<",2,8);
            bi(">",2,9);
            bi("<=",2,10);
            bi(">=",2,11);
            bi("=\\=",2,12);
            bi("get",1,15);
            bi("get0",1,16);
            bi("seen",0,17);
            bi("nl",0,20);
            bi("put",1,21);
            bi("told",0,22);
            bi("newprogram",0,23);
            bi(prologop.listcons.name,2,25);
            bi("addStatement",1,26);
            bi("addStatementz",1,26);
            bi("addStatementa",1,27);
            bi("retract",1,28);
            bi("writeprogram",0,29);
            bi("op",3,30);
            bi("var",1,31);
            bi("nonvar",1,32);
            bi("atom",1,33);
            bi("integer",1,34);
            bi("=..",2,35);
            bi("name",2,36);
            bi("==",2,37);
            bi(";",2,38);
            bi(",",2,39);
            bi("compound",1,40);
            bi("random",3,41);
            bi("\\==",2,42);
            bi("writenoq",1,43);
            bi("writeq",1,44);
        }
        lib=l;
    }
    int getbinum(term r) {
        Integer i=(Integer)bi_pred.get(program.searchkey(r));
        if(i==null)
            return -1;
        return i.intValue();
    }

    void stacktodo(term q,rack r)
    { /*push all goals in q on the todo stack, with parent r.*/
        if(q==null)
            return;
        if(q.type==term.FUNCTOR&&q.name==prologop.AND) {
            stacktodo(q.arg[1],r);
            stacktodo(q.arg[0],r);
        } else todo.push(new rack(q,r));
    }

    void query(term q) {
        time=System.currentTimeMillis();
        todo=new Stack();
        done=new Stack();
        subst=new Stack();
        todo.push(new rack(ASK,null));
        uservars=new Vector();
        term.vars(q,uservars);
        stacktodo(q,null);
        run(5000); // max iters
        //mythread=new Thread(this);
        //mythread.start();
    }

    static int FALSE=0,TRUE=1,ERROR=-1;
    int solve(rack r) {
        /*return one of FALSE=0,TRUE=1,ERROR=-1*/
        if(r.solveoption==rack.NOTAGAIN)
            return FALSE;
        term rpred=term.skipeq(r.pred);
        if(rpred.type!=term.FUNCTOR)
            return ERROR;
        int bi=getbinum(rpred);
        if(bi!=-1) {
            char c;
            term t,l;

            switch(bi){
            case 1:return TRUE;
            case 2:return FALSE;
            case 3:
                r.solveoption=rack.NOTAGAIN;
                return TRUE;
            case 4:
                r.solveoption=rack.NOTAGAIN;
                rack todo1;
                rack realparent=r.parent;
                while(realparent!=null&&
                      (realparent.pred.name==prologop.AND||
                       realparent.pred.name==prologop.OR))
                    {realparent=realparent.parent;}
                int todop=todo.size()-1;
                while(todop>=0) {
                    todo1=(rack)todo.elementAt(todop);
                    if(todo1.parent!=null&&
                       (todo1.parent.pred.name==prologop.AND||
                        todo1.parent.pred.name==prologop.OR) )
                        {
                            todo1.parent=realparent;
                            todop--;
                        }
                    else break;
                }
                r.parent=realparent;

                while(!done.empty()) {
                    if(done.peek()!=r.parent)
                        done.pop();
                    else
                        break;
                }
                if(r.parent!=null)
                    r.parent.solveoption=rack.NOTAGAIN;
                return TRUE;
            case 5:
                r.solveoption=rack.NOTAGAIN;
                if(term.match(rpred.arg[0],rpred.arg[1],subst))
                    return TRUE;
                else
                    return FALSE;
            case 6:
                r.solveoption=rack.NOTAGAIN;
                int n=term.numbervalue(rpred.arg[1]);
                if(n==term.NaN)
                    return ERROR;
                if(term.match(rpred.arg[0],new term(n),subst))
                    return TRUE;
                else
                    return FALSE;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                r.solveoption=rack.NOTAGAIN;
                int n1=term.numbervalue(rpred.arg[0]);
                int n2=term.numbervalue(rpred.arg[1]);
                if(n1==term.NaN||n2==term.NaN) {
                    System.out.println("No number");
                    return ERROR;
                }
                if((bi==8&&n1<n2)||
                   (bi==9&&n1>n2)||
                   (bi==10&&n1<=n2)||
                   (bi==11&&n1>=n2)||
                   (bi==7&&n1==n2)||
                   (bi==12&&n1!=n2))
                    return TRUE;
                else
                    return FALSE;
            case 15:
                r.solveoption=rack.NOTAGAIN;
                do{
                    c=inp.get0();
                }while(c<=32);
                if(term.match(rpred.arg[0],new term((int)c),subst))
                    return TRUE;
                else
                    return FALSE;
            case 16:
                r.solveoption=rack.NOTAGAIN;
                c=inp.get0();
                if(term.match(rpred.arg[0],new term((int)c),subst))
                    return TRUE;
                else
                    return FALSE;
            case 17:
                r.solveoption=rack.NOTAGAIN;
                inp=null;
                return TRUE;
            case 19:
                r.solveoption=rack.NOTAGAIN;
                return TRUE;
            case 20:
                r.solveoption=rack.NOTAGAIN;
                return TRUE;
            case 21:
                r.solveoption=rack.NOTAGAIN;
                t=term.skipeq(rpred.arg[0]);
                if(t.type==term.NUMBER&&t.arity>=0&&t.arity<256)
                    {
                        return TRUE;
                    }
                return ERROR;
            case 22:
                r.solveoption=rack.NOTAGAIN;
                return TRUE;
            case 23:
                r.solveoption=rack.NOTAGAIN;
                lib=new program();
                return TRUE;
            case 24:
            case 25:
                r.solveoption=rack.NOTAGAIN;
                t=term.skipeq(rpred.arg[0]);
                if(t.type!=term.FUNCTOR||t.arity!=0)
                    return ERROR;
                return TRUE;
            case 26:
                r.solveoption=rack.NOTAGAIN;
                if(addStatement(program.gramconvert(rpred.arg[0].copy())
                          ))
                    return TRUE;
                return ERROR;
            case 27:
                r.solveoption=rack.NOTAGAIN;
                if(program.addStatementa(lib.user,
                                   program.gramconvert(rpred.arg[0].copy())
                                   ))
                    return TRUE;
                return ERROR;
            case 28:
                r.solveoption=rack.NOTAGAIN;
                return retract(rpred.arg[0]);
            case 29:
                r.solveoption=rack.NOTAGAIN;
                return TRUE;
            case 30:
                r.solveoption=rack.NOTAGAIN;
                term nam=term.skipeq(rpred.arg[2]);
                if(nam.type!=term.FUNCTOR||nam.arity!=0)
                    return ERROR;
                term ty=term.skipeq(rpred.arg[1]);
                if(ty.type!=term.FUNCTOR||ty.arity!=0)
                    return ERROR;
                if(prologop.addoperator(nam.name,ty.name,
                                        term.numbervalue(rpred.arg[0])))
                    return TRUE;
                return ERROR;
            case 31:
                r.solveoption=rack.NOTAGAIN;
                t=term.skipeq(rpred.arg[0]);
                if(t.type==term.OPEN)
                    return TRUE;
                return FALSE;
            case 32:
                r.solveoption=rack.NOTAGAIN;
                t=term.skipeq(rpred.arg[0]);
                if(t.type!=term.OPEN)
                    return TRUE;
                return FALSE;
            case 33:
                r.solveoption=rack.NOTAGAIN;
                t=term.skipeq(rpred.arg[0]);
                if(t.type==term.FUNCTOR&&t.arity==0)
                    return TRUE;
                return FALSE;
            case 34:
                r.solveoption=rack.NOTAGAIN;
                t=term.skipeq(rpred.arg[0]);
                if(t.type==term.NUMBER)
                    return TRUE;
                return FALSE;
            case 35:
                r.solveoption=rack.NOTAGAIN;
                term left=term.skipeq(rpred.arg[0]);
                if(left.type==term.FUNCTOR)
                    {
                        term tail=term.emptylist;
                        for(int i=left.arity-1;i>=0;i--)
                            tail=term.makelist(left.arg[i],tail);
                        term head=term.newconstant(left.name,left.qname);
                        if(term.match(term.makelist(head,tail),rpred.arg[1],subst))
                            return TRUE;
                        return FALSE;
                    }
                term right=term.skipeq(rpred.arg[1]);
                if(right.type==term.FUNCTOR&&right.name==prologop.listcons.name)
                    {
                        term h=term.skipeq(right.arg[0]);
                        if(h.type==term.FUNCTOR&&h.arity==0)
                            {t=term.newconstant(h.name,h.qname);
                            l=term.skipeq(right.arg[1]);
                            while(l!=term.emptylist)
                                {
                                    if(t.arity==term.MAXARG||l.type!=term.FUNCTOR
                                       ||l.name!=prologop.listcons.name)
                                        return ERROR;
                                    t.addarg(term.skipeq(l.arg[0]));
                                    l=term.skipeq(l.arg[1]);
                                }
                            if(term.match(left,t,subst))
                                return TRUE;
                            return FALSE;
                            }
                    }
                return ERROR;
            case 36:
                r.solveoption=rack.NOTAGAIN;
                t=term.skipeq(rpred.arg[0]);
                if(t.type==term.FUNCTOR&&t.arity==0)
                    {if(term.match(rpred.arg[1],term.asciilist(t.name),subst))
                        return TRUE;
                    return FALSE;
                    }
                String str=term.readasciilist(rpred.arg[1]);
                if(str!=null&&term.match(rpred.arg[0],term.newconstant(str),subst))
                    return TRUE;
                return FALSE;
            case 37:
                r.solveoption=rack.NOTAGAIN;
                if(term.equal(rpred.arg[0],rpred.arg[1]))
                    return TRUE;
                else
                    return FALSE;
            case 38:
                if(r.solveoption==rack.UNKNOWN)
                    {r.solveoption=rack.BUILTIN;
                    stacktodo(rpred.arg[0],r);
                    return TRUE;
                    }
                r.solveoption=rack.NOTAGAIN;
                stacktodo(rpred.arg[1],r);
                return TRUE;
            case 39:
                r.solveoption=rack.NOTAGAIN;
                stacktodo(rpred.arg[1],r);
                stacktodo(rpred.arg[0],r);
                return TRUE;
            case 40:
                r.solveoption=rack.NOTAGAIN;
                t=term.skipeq(rpred.arg[0]);
                if(t.type==term.FUNCTOR&&t.arity>0)
                    return TRUE;
                return FALSE;
            case 41:
                r.solveoption=rack.NOTAGAIN;
                int a=term.numbervalue(rpred.arg[0]);
                int b=term.numbervalue(rpred.arg[1]);
                if(a<=b&&a!=term.NaN&&b!=term.NaN)
                    {
                        if(term.match(rpred.arg[2],
                                      new term(a+(int)(Math.random()*(b-a))),
                                      subst))
                            return TRUE;
                        return FALSE;
                    }
                return ERROR;
            case 42:
                r.solveoption=rack.NOTAGAIN;
                if(term.equal(rpred.arg[0],rpred.arg[1]))
                    return FALSE;
                else
                    return TRUE;
            case 43:
                r.solveoption=rack.NOTAGAIN;
                return TRUE;
            case 44:
                r.solveoption=rack.NOTAGAIN;
                return TRUE;
            default:
                System.out.println("bipred missing.");
                return ERROR;
            }}//OD switch, OD bi!=-1
        if(rpred==ASK)
            {
                if(uservars.size()==0)
                    return TRUE;
                substwrite();
                return FALSE;
            }
        /*No builtin predicate. Get a fitting rule out the
          clause lib, match the head, and stack the body.*/
        if(r.solveoption==rack.UNKNOWN) {
            r.clauses=lib.get(rpred);
            if(r.clauses==null) {
                System.out.println("undefined predicate: "+program.searchkey(rpred));
                return FALSE;
            }
        }
        else if(r.clauses!=term.emptylist)
            r.clauses=r.clauses.arg[1];
        r.solveoption=1;
        term theclause;
        while(r.clauses!=term.emptylist) {
            theclause=r.clauses.arg[0].copy();
            if(term.match(rpred,program.head(theclause),subst)) {
                stacktodo(program.body(theclause),r);
                return TRUE;
            }
            r.clauses=r.clauses.arg[1];
        }
        return FALSE;
    }

    public void run(int max_iter) {
        rack current;
        wait=false;
        int substnum;

        int iter = 0;
        while(iter++ < max_iter) {
            if(todo.size()==0) {
                System.out.println("\nyes");
                return;
            }
            current=(rack)todo.pop();
            int v=solve(current);
            if(v==ERROR) {
                do {
                    System.out.println("Error in: "+current.pred);
                    current=current.parent;
                } while(current!=null);
                return;
            }
            else if(v==TRUE) {
                current.substdone=subst.size();
                done.push(current);
            } else {//v==FALSE
                current.solveoption=rack.UNKNOWN;
                todo.push(current);
                if(done.isEmpty())  return;
                current=(rack)done.pop();
                while(((rack)todo.peek()).parent==current)
                    {todo.pop();}
                todo.push(current);
                if(!done.isEmpty())
                    substnum=((rack)done.peek()).substdone;
                else
                    substnum=0;
                term.unmatch(subst,substnum);
            }
        }
    }

    term executepred(term X) {
        /*returns A if X == (:-A), or null.*/
        if(X==null)
            return null;
        X=term.skipeq(X);
        if(X.type==term.FUNCTOR&&X.arity==1&&X.name.equals(":-"))
            return X.arg[0];
        return null;
    }

    boolean addStatement(term X)
    {  return program.addStatement(lib.user,X);
    }

    int retract(term t) {
        term list=lib.get(t);
        if(list==null||list==term.emptylist)
            return FALSE;
        if(term.match(t,program.head(list.arg[0]),subst)) {
            lib.user.put(lib.searchkey(t),list.arg[1]);
            return TRUE;
        }
        term superlist=list;
        list=list.arg[1];
        while(list!=term.emptylist) {
            if(term.match(t,list.arg[0],subst)) {
                superlist.arg[1]=list.arg[1];
                return TRUE;
            }
            superlist=list;
            list=list.arg[1];
        }
        return FALSE;
    }

    void substwrite() {
        term g;
        Hashtable hash = new Hashtable();
        for(int i=0;i<uservars.size();i++) {
            g=(term)uservars.elementAt(i);
            String rightside=""+g;
            String leftside=g.varname();
            if(!leftside.equals("_")&&!leftside.equals(rightside)) {
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
