package calculador;

public class Pair {
private int x;
private int y;

public Pair(int posx, int posy){
	super();
	inicializar(posx,posy);
}
public Pair(){
	super();
	inicializar(0,0);
}

public void inicializar(int posx, int posy){
	x = posx;
	y = posy;
}
public boolean equal(Pair unPar){
	if ((unPar.x() == x) && (unPar.y()== y))
		return true;
	else
		return false;
}
public int x(){
	return x;
}
public int y(){
	return y;
}
}
