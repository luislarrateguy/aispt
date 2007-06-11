package tpsia.tp1.busqueda;

import java.util.ArrayList;

import tpsia.tp1.EstadoCelda;
import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public class BusquedaAvara2 extends Busqueda {

	public BusquedaAvara2(Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estadosAlcanzadosAgente) {
		super(estado, objetivo, estadosAlcanzadosAgente);
	}
	
	@Override
	protected float calcularPrioridad(Nodo unNodo) {
		float heuristica1 = unNodo.getEstado().getAmbiente().cantidadCeldasDesconocidas();
		float heuristica2 = unNodo.getEstado().getAmbiente().cantidadComidaVisible();
		float heuristica3 = unNodo.getEstado().getAmbiente().cantidadEnemigosVisible();
		
		// Heurística rara que estoy probando :)
		float heuristica4 = unNodo.getEstado().getAmbiente().distanciaObjeto(EstadoCelda.Comida)*2;
		float heuristica5 = unNodo.getEstado().getAmbiente().distanciaObjeto(EstadoCelda.Enemigo);
		
		
		float heuristica = heuristica1*h1 + heuristica2*h2 + heuristica3*h3 + heuristica4 + heuristica5;
		
		return heuristica;
	}

	@Override
	protected String nombreEstrategia() {
		return "Avara 2";
	}

}
