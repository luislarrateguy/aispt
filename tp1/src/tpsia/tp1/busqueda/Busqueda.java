package tpsia.tp1.busqueda;

import java.util.ArrayList;

import tpsia.tp1.acciones.Accion;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;

public abstract class Busqueda {
	protected Estado estado;
	protected IObjetivo objetivo;
	
	public Busqueda(Estado estado, IObjetivo objetivo) {
		this.estado = estado;
		this.objetivo = objetivo;
	}
	
	public abstract ArrayList<Accion> buscarSolucion();
}
