package tpsia.tp1.busqueda;

import java.util.ArrayList;

import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public class BusquedaAmplitud extends Busqueda {

	public BusquedaAmplitud(Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estadosAlcanzadosAgente) {
		super(estado, objetivo, estadosAlcanzadosAgente);
	}
	
	@Override
	protected float calcularPrioridad(Nodo unNodo) {
		// TODO Auto-generated method stub
		return (unNodo.getPadre().getPrioridadExpansion() + 1);
	}

	@Override
	protected String nombreEstrategia() {
		// TODO Auto-generated method stub
		return "Amplitud";
	}

}
