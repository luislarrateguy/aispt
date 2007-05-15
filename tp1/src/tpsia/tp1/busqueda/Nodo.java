package tpsia.tp1.busqueda;

import tpsia.tp1.EstadoCelda;
import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.Accion;
import tpsia.tp1.acciones.Pelear;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.VisionAmbiente;

public class Nodo implements Comparable<Nodo> {
	private Nodo padre;
	private Accion accionGeneradora;
	
	private Estado estadoNodo;
	private float costo;
	
	/**
	 * Este constructor sólo debe ser utilizado para generar el
	 * nodo del estado inicial.
	 * @param estado
	 * @param costo
	 */
	public Nodo (Estado estado) {
		this.padre = null;
		this.accionGeneradora = null;
		this.estadoNodo = estado;
		this.costo = 0;
	}
	
	public Nodo(Nodo padre, Accion accionGeneradora, float promVarEnergia) {
		this.padre = padre;
		this.accionGeneradora = accionGeneradora;
		/* El tema del promedio, al sumarlo, afecta en forma lineal al costo total
		 * Además el promedio puede ser negativo, positivo, o nulo.
		 * Ya que pudo haber ganado o perdido energía.
		 * ¿ No sería mejor multiplicar por ese promedio ?
 		 * CostoAccion = (PonderacionDeLaAccion+promVarEnergia)/promVarEnergia
		 * 
		 * CostoAccion siempre debe ser positivo, sino tiene un costo menor que el padre.
		 * CostoDelNodo = CostoDelPadre + CostoAccion
		 * 
		 */ 
		//original
		// this.costo = this.padre.costo + this.accionGeneradora.getCosto() - promVarEnergia;
		
		/* Una simple que pondera siempre comer, caminar y luego pelear. En ese orden.
		 * 
		 */
		this.costo = this.padre.costo + this.accionGeneradora.getCosto();
		
		/* Una opcion que calcula costo en base a la información que tiene
		 * Le paso sus propios parámetros para que sea más claro el código
		 * en la función
		 */
		//this.costo =  this.calcularCosto(this.padre.costo,this.accionGeneradora, promVarEnergia);
		
		//otra
		//float pond = (float) ((promVarEnergia<0)? 1.5:0.5);
		//this.costo = this.padre.costo + this.accionGeneradora.getCosto()*pond;
	
		// Copio el estado y le aplico la acción generadora para modificar el ambiente
		this.estadoNodo = (Estado)this.padre.estadoNodo.clone();
		
		try {
			this.accionGeneradora.ejecutar(this.estadoNodo.getAmbiente());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* Actualizo el estado del nodo "enviándole una percepción", descubriendo como
		 * vacías las celdas desconocidas. */
		/* TODO: Se podría sacar a otro método o aplicar el patrón Factory para 
		 * crear el objeto Percepcion, ya que su creacion aca complica la lectura
		 * del resto del código de búsqueda */ 
		VisionAmbiente visionAmbienteNodoActual = this.estadoNodo.getAmbiente();
		EstadoCelda celdasAdyacentes[] = visionAmbienteNodoActual.getCeldasAdyacentes();
		EstadoCelda nuevasCeldasAdyacentes[] = new EstadoCelda[4];
		
		for (int i=0; i<4; i++) {
			nuevasCeldasAdyacentes[i] = celdasAdyacentes[i];
			
			if (celdasAdyacentes[i] == EstadoCelda.Desconocida)
				nuevasCeldasAdyacentes[i] = EstadoCelda.Vacia;
		}
		
		int [] posicion = this.estadoNodo.getAmbiente().getPosicionPacman();
		Percepcion p = new Percepcion(nuevasCeldasAdyacentes,
				(int)(this.estadoNodo.getEnergia() + promVarEnergia), posicion);
		
		this.estadoNodo.actualizarEstado(p);
		
		/* Si el nodo provoca la muerte del Pacman, lo cortamos. Su costo
		 * es infinito. */
		if (this.estadoNodo.getEnergia() <= 0)
			this.costo = Float.POSITIVE_INFINITY;
	}

	private float calcularCosto(float c, Accion accion, float var) {
		float aux;
		
		/* Si no sabe lo que es pelear, que se arriesgue.
		 * Para eso le asigno un costo un poco menor que la comida. 
		 * var es la variancia */
		if (accion == Pelear.getInstancia() && 
				var == 0.0) {
			aux = (float) (c + 0.5);
		} else {
			aux = c + accion.getCosto() * ((var < 0)? 2 : 1) ;
		}
		return aux;
	}

	public int compareTo(Nodo arg0) {
		if (this.costo == arg0.costo)
			return 0;
		else if (this.costo < arg0.costo)
			return -1;
		else
			return 1;
	}

	public Estado getEstado() {
		return estadoNodo;
	}

	public Nodo getPadre() {
		return padre;
	}

	public Accion getAccionGeneradora() {
		return accionGeneradora;
	}

	public void toXML() {

	}
}
