package tpsia.tp1.busqueda;

import java.util.ArrayList;

import tpsia.tp1.acciones.*;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public class BusquedaAEstrella extends Busqueda {

	public BusquedaAEstrella(Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estadosAlcanzadosAgente) {
		super(estado, objetivo, estadosAlcanzadosAgente);
	}
	
	@Override
	protected float calcularPrioridad(Nodo unNodo) {
		/* Calculo el costo */
		float costo = unNodo.getPadre().getPrioridadExpansion() + unNodo.getAccionGeneradora().getCosto();
		
		/* Calculo la función heurística, que consiste en la cantidad de celdas que
		 * son desconocidas. */
		float heuristica1 = unNodo.getEstado().getAmbiente().cantidadCeldasDesconocidas();
		/*
		float heuristica2 = unNodo.getEstado().getAmbiente().cantidadComidaVisible();
		float heuristica3 = unNodo.getEstado().getAmbiente().cantidadEnemigosVisible();
		
		float promedioComer = unNodo.getEstado().getPromedioVarEnergia(Comer.getInstancia());
		float promedioPelear = unNodo.getEstado().getPromedioVarEnergia(Pelear.getInstancia());
		int energiaActual = unNodo.getEstado().getEnergia();
		
		// Si cuando come le beneficia para ganar en este momento, pondero la comida
		heuristica2 *= (promedioComer + energiaActual > promedioPelear)?10:5; 
		
		// Si cuando pelea puede llegar a ganar 
		heuristica3 *= (promedioPelear >= energiaActual)?0:5; 
		
		float heuristica = heuristica1 + heuristica2 + heuristica3;
		*/
		/* Retorno el valor de la función de evaluación = costo + heuristica */
		return (costo + heuristica1);
	}
}
