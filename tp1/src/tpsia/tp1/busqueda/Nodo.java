package tpsia.tp1.busqueda;

import tpsia.tp1.EstadoCelda;
import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.IAccion;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.VisionAmbiente;

public class Nodo implements Comparable<Nodo> {
	private Nodo padre;
	private IAccion accionGeneradora;
	
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
	
	public Nodo(Nodo padre, IAccion accionGeneradora, float promedio) {
		this.padre = padre;
		this.accionGeneradora = accionGeneradora;
		this.costo = this.padre.costo + this.accionGeneradora.getCosto() + promedio;
		
		// Copio el estado y le aplico la acción generadora para modificar el ambiente
		this.estadoNodo = (Estado)this.padre.estadoNodo.clone();
		this.accionGeneradora.ejecutar(this.estadoNodo.getAmbiente());
		
		/* Actualizo el estado del nodo enviándole una percepción, descubriendo como
		 * vacías las celdas desconocidas. */
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
				(int)(this.estadoNodo.getEnergia() - promedio), posicion);
		
		this.estadoNodo.actualizarEstado(p);
		
		/* Si el nodo provoca la muerte del Pacman, lo cortamos. Su costo
		 * es infinito. */
		if (this.estadoNodo.getEnergia() <= 0)
			this.costo = Float.POSITIVE_INFINITY;
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

	public IAccion getAccionGeneradora() {
		return accionGeneradora;
	}
}
