package tpsia.tp1.busqueda;

import java.util.ArrayList;

import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public class BusquedaAvara extends Busqueda {

	public BusquedaAvara(Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estadosAlcanzadosAgente) {
		super(estado, objetivo, estadosAlcanzadosAgente);
	}
	
	@Override
	protected float calcularPrioridad(Nodo unNodo) {
		float heuristica = unNodo.getEstado().getAmbiente().cantidadCeldasDesconocidas();
		
		return heuristica;
	}

}
