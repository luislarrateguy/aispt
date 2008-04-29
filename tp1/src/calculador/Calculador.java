package calculador;
import java.lang.Math;
import java.util.Vector;


public class Calculador implements Cloneable{

	protected int energiaPacman, energiaEnemigo, energiaAlimentos = 0;
	protected int x, y;
	protected int xIni, yIni;
	protected int performance;
	//private Vector posiciones;
	protected Vector<Pair> comida;
	protected Vector<Pair> enemigos;
	protected Vector<String> acciones;	//Lista que almacena las acciones ejecutadas por el agente.-
	protected String grupo;	// Almacena el nombre del grupo que se va a mostrar en el simulador.-
	protected int energiaPacmanOriginal;	//Almacena la energía que tuvo Pacman en el inicio.-

	protected boolean yaObtuvoPerformance;

	protected int posiciones[][];
	protected int posicionesInicial[][];
	protected int contComida;
	protected int contMovimientos;
	protected int contEnemigos;
	
	protected int cantComida,cantMovimientos,cantEnemigos;

	/** Permite indicar que una celda está desocupada */
	final private static int EMPTY = 0; 
	/** Permite indicar que en una celda está el pacman */
	final private static int PACMAN = 1; 
	/** Permite indicar que en una celda hay un enemigo */
	final private static int ENEMY = 2; 
	/** Permite indicar que en una celda hay comida */
	final private static int FOOD = 3; 

	/**
	 * Crea una instancia de la clase Calculador asignando el nombre del grupo.
	 * @param grupo: Es el nombre del grupo
	 */
	public Calculador(String grupo){
		super();
		inicializar(grupo);
	}
	/**
	 * Crea una instancia de la clase Calculador sin asignar un nombre al grupo.
	 */
	public Calculador(){
		super();
		inicializar("Grupo sin nombre");
	}

	private void inicializar(String grupo){
		  if (grupo.equals(""))
			  grupo = "Grupo sin nombre";
		  this.grupo = grupo;
		  yaObtuvoPerformance = false;
		
	      energiaPacman = new Double(Math.random()* 40 + 10).intValue();
	      energiaPacmanOriginal = energiaPacman;
	      energiaEnemigo = new Double(Math.random()* energiaPacman + 3).intValue();
	      energiaAlimentos = new Double(Math.random()* 20 + 5).intValue();

	      performance = 0;
	      contEnemigos = 0;
	      contComida = 0;
	      contMovimientos = 0;
	      
	      posiciones = new int[4][4];
	      posicionesInicial = new int[4][4];
	      for (int i=0;i<posiciones.length;i++)
	          for (int j=0;j<posiciones.length;j++)
	          {
	        	  posiciones[i][j]=Calculador.EMPTY;
	        	  posicionesInicial[i][j]=Calculador.EMPTY;
	          }
	      
	      acciones = new Vector<String>();
	      comida = new Vector<Pair>();
	      enemigos = new Vector<Pair>();
	      
	      /* x = new Double(Math.random()* 4+1).intValue();
	      y = new Double(Math.random()* 4+1).intValue();
	      Pair pos = new Pair(x, y);*/

	      x = new Double(Math.random()* 4+1).intValue();
		  y = new Double(Math.random()* 4+1).intValue();
		  xIni = x;
		  yIni = y;
		  
		  //supongo que si pacman es un capo debería hacer todo en 16 movimientos, ni más ni menos.
		  cantMovimientos = 10;
		  
		  Pair pos = new Pair(x,y);
		  //posiciones[x-1][y-1]=Calculador.PACMAN;
		  //posicionesInicial[x-1][y-1]=Calculador.PACMAN;
		  
		  //inicializa las posiciones del enemigo
		  generarPosicionesEnemigo();
		  //inicializa las posiciones de la comida
		  generarPosicionesComida();

	}

	/**
	 * Devuelve las posiciones de los enemigos. 
	 * @return Retorna un vector de Pairs que contiene las coordenadas x,y de los enemigos. 
	 */
	public Vector<Pair> inicializarEnemigo(){
		return enemigos;
	}

	/**
	 * Inicializa las posiciones de los enemigos.
	 * 
	 */
	private void generarPosicionesEnemigo(){
		int tamanioVector = new Double(Math.random()*4 + 2).intValue();
		Vector<Pair> salida = new Vector<Pair>(tamanioVector);	
		Pair pos;
		int x,y;
		cantEnemigos = tamanioVector;
		
		for (int i=0; i< tamanioVector; i++){
			do{
			 x = new Double(Math.random()* 4+1).intValue();
			 y = new Double(Math.random()* 4+1).intValue();
		  	 pos = new Pair(x,y);
		  	 }while(repetido((Pair)pos));
		     //System.out.println("-- Pos Enemigo: " + x + "," + y);
			 salida.add(pos);
			 enemigos.add(pos);
			 posiciones[x-1][y-1]=Calculador.ENEMY;
			 posicionesInicial[x-1][y-1]=Calculador.ENEMY;
		  	}
	}


	private boolean repetido(Pair posicion){

		if (posicionesInicial[posicion.x()-1][posicion.y()-1]!=0 | (posicion.x()==x & posicion.y()==y)){
			return true;
		}

		return false;
	}
	/**
	 * Devuelve las posiciones de la comida. 
	 * @return Retorna un vector de Pairs que contiene las coordenadas x,y de la comida. 
	 */
	public Vector<Pair> inicializarComida(){
		return comida;
	}

	/**
	 * Genera las posiciones iniciales de la comida.
	 * 
	 */
	private void generarPosicionesComida(){
		int tamanioVector = new Double(Math.random()*6 + 2).intValue();
		Vector<Pair> salida = new Vector<Pair>(tamanioVector);	
		Pair pos;
		int x,y;
		cantComida = tamanioVector;
		
		for (int i=0; i< tamanioVector; i++){
			do{
			 x = new Double(Math.random()* 4+1).intValue();
			 y = new Double(Math.random()* 4+1).intValue();
		  	 pos = new Pair(x,y);
		  	 }while(repetido(pos));
		    //System.out.println("-- Pos comida: " + x + "," + y);
			salida.add(pos);
			comida.add(pos);
			posiciones[x-1][y-1]=Calculador.FOOD;
			posicionesInicial[x-1][y-1]=Calculador.FOOD;
		  	}
	}


	protected Vector<Pair> getComida(){
		return comida;
	}

	protected Vector<Pair> getEnemigos(){
		return enemigos;
	}

	public Pair getPosicionInicial(){
		Pair salida;

		salida = new Pair(xIni,yIni);

		return salida;
	}    	  

	public int getPerformance(){
		int p = -1;
		// Esta condición evita que se llame al método getPerformance() más de una vez.-
		if (!yaObtuvoPerformance){
			yaObtuvoPerformance = true;
			p = performance;
		}
		return p;
	}

	public int calcularEnergiaPacMan(String a){
		if (energiaPacman > 0){
			if (a.toLowerCase()=="arriba" | a.toLowerCase()=="abajo" | a.toLowerCase()=="izquierda" | a.toLowerCase()=="derecha"){
				//Esta condición es necesaria porque sino la energía nunca llega a cero
				//con solo caminar.-
				if (energiaPacman < 10)
					energiaPacman = energiaPacman - 1;
				else
					energiaPacman = energiaPacman - (int)(energiaPacman * 0.1);
				
				performance--;
				contMovimientos++;
				
				actualizarPosicionPacman(a.toLowerCase());
				
				acciones.add(a.toLowerCase());
			}
			else{
				//System.out.println("-- Posición Pacman: "+x+","+y);
				if (a.toLowerCase() == "pelear" & posiciones[x-1][y-1]==Calculador.ENEMY)
				{
					posiciones[x-1][y-1] = Calculador.EMPTY;
					                
					energiaPacman = energiaPacman - new Double(energiaPacman * .15 + energiaEnemigo * .15).intValue();
					contEnemigos ++;
					performance += 5 * contEnemigos;

					acciones.add(a.toLowerCase());
				}else{
					if (a.toLowerCase() == "comer" & posiciones[x-1][y-1]==Calculador.FOOD){
						posiciones[x-1][y-1] = Calculador.EMPTY;

						energiaPacman = energiaPacman + (int)(energiaAlimentos * .80);
						performance = performance + 5 + (int)(energiaAlimentos * .2);
						contComida ++;
						acciones.add(a.toLowerCase());
					}
				}
			}
		}
					
		if (energiaPacman < 0)
			energiaPacman = 0;
		
		return energiaPacman;
	}

	public int calcularEnergiaPacMan(){
		return energiaPacman;
	}

	protected int getEnergiaInicial(){
		return energiaPacmanOriginal;
	}

	protected int getEnergiaEnemigo(){
		return energiaEnemigo;
	}

	protected int getEnergiaComida(){
		return energiaAlimentos;
	}

	protected String getGrupo(){
		return grupo;
	}

	protected Vector<String> getAcciones(){
		return acciones;
	}

	private void actualizarPosicionPacman(String a){
		if (a.toLowerCase()=="arriba"){
			if (x==1)
				x=4;
			else
				x--;
		}
		if (a.toLowerCase()=="izquierda")
			if (y==1)
				y=4;
			else
				y--;
		if (a.toLowerCase()=="abajo")
			if (x==4)
				x=1;
			else
				x++;
		if (a.toLowerCase()=="derecha")
			if (y==4)
				y=1;
			else
				y++;
		
	}
	
	public Calculador clone() {
		Calculador c = null;
//		try {

			c = new CalculadorMiPerformance();
//			c = (Calculador) super.clone();
//			c.acciones = new Vector<String>();
//			c.comida = new Vector<Pair>();
//			c.enemigos = new Vector<Pair>();
//			
//			for ( Pair cua :  this.comida ) {
//				c.comida.add(cua.clone());
//			}
//			for ( Pair cua :  this.enemigos ) {
//				c.enemigos.add(cua.clone());
//			}
//			for ( String cua :  this.acciones ) {
//				c.acciones.add(cua);
//			}
			
			c.comida = (Vector<Pair>) this.comida.clone();
			c.enemigos =  (Vector<Pair>) this.enemigos.clone();
			c.acciones =  (Vector<String>) this.acciones.clone();

			for (int i=0;i<4;i++)
				for (int j=0;j<4;j++) {
					c.posiciones[i][j] = this.posiciones[i][j];
					c.posicionesInicial[i][j] = this.posicionesInicial[i][j];
				}
			
			c.energiaAlimentos = this.energiaAlimentos;
			c.energiaEnemigo = this.energiaEnemigo;
			c.energiaPacman = this.energiaPacman;
			c.energiaPacmanOriginal = this.energiaPacmanOriginal;
			c.performance = this.performance;
			c.yaObtuvoPerformance = this.yaObtuvoPerformance;
			c.contEnemigos = this.contEnemigos;
			c.cantEnemigos = this.cantEnemigos;
			c.cantComida = this.cantComida;
			c.contComida = this.contComida;
			c.cantMovimientos =this.cantMovimientos;
			c.contMovimientos = this.contMovimientos;
			c.x = this.x;
			c.y = this.y;
			c.xIni = this.xIni;
			c.yIni = this.yIni;
			

//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return c;
	}
}
