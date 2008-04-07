package calculador;
import java.lang.Math;
import java.util.Vector;
public class Calculador {
private int energiaPacman, energiaEnemigo, energiaAlimentos = 0;
private int x, y, contEnemigosMuertos;
private int performance; 
private Vector posiciones;

public Calculador(){
	super();
	inicializar();
	
}
public void inicializar(){
      energiaPacman = new Double(Math.random()* 50 + 10).intValue();
      energiaEnemigo = new Double(Math.random()* 50 + 10).intValue();
      energiaAlimentos = new Double(Math.random()* 20 + 5).intValue();

      performance = 0;
      contEnemigosMuertos = 0;
      posiciones = new Vector();
      
      /* x = new Double(Math.random()* 4+1).intValue();
      y = new Double(Math.random()* 4+1).intValue();
      Pair pos = new Pair(x, y);*/

      x = new Double(Math.random()* 4+1).intValue();
	  y = new Double(Math.random()* 4+1).intValue();
	  posiciones.add(new Pair(x,y));

}

public Vector inicializarEnemigo(){
	int tamanioVector = new Double(Math.random()*4 + 2).intValue();
	Vector salida = new Vector(tamanioVector);	
	Pair pos;
	for (int i=0; i< tamanioVector; i++){
		do{
		 x = new Double(Math.random()* 4+1).intValue();
		 y = new Double(Math.random()* 4+1).intValue();
	  	 pos = new Pair(x,y);
	  	 }while(repetido((Pair)pos));
		salida.add(pos);
		posiciones.add(pos);
	  	}
	return salida;
}

private boolean repetido(Pair posicion){
	int i = 0;
	boolean esta = false;
	while( !esta && i< posiciones.size()){
		if(((Pair) posiciones.elementAt(i)).equal(posicion))
			esta = true;
		i++;
	}
	return esta;
}
public Vector inicializarComida(){
	int tamanioVector = new Double(Math.random()*4 + 2).intValue();
	Vector salida = new Vector(tamanioVector);	
	Pair pos;
	for (int i=0; i< tamanioVector; i++){
		do{
		 x = new Double(Math.random()* 4+1).intValue();
		 y = new Double(Math.random()* 4+1).intValue();
	  	 pos = new Pair(x,y);
	  	 }while(repetido(pos));
		salida.add(pos);
		posiciones.add(pos);
	  	}
	return salida;
}

public Pair getPosicionInicial(){
	Pair salida;

	salida = new Pair(x,y);

	return salida;
}    	  

public int getPerformance(){
	return performance;
}

public int calcularEnergiaPacMan(String a){
	if (a.toLowerCase()=="arriba" || a.toLowerCase()=="abajo" || a.toLowerCase()=="izquierda" || a.toLowerCase()=="derecha"){
		energiaPacman = energiaPacman - (int)(energiaPacman * 0.1);
		performance += 1;}
	else
		if (a.toLowerCase() == "pelear")
		{
			energiaPacman = energiaPacman - new Double(energiaPacman * .2 + energiaEnemigo * .2).intValue();
			contEnemigosMuertos +=1;
			performance += 5 * contEnemigosMuertos;
			if (energiaPacman < 0)
				energiaPacman = 0;
		}else
			if (a.toLowerCase() == "comer")
				energiaPacman = energiaPacman + (int)(energiaAlimentos * .80);
				
	return energiaPacman;
}

public int calcularEnergiaPacMan(){
	return energiaPacman;
		
}

}
