package tpsia.tp1.busqueda;

import java.util.ArrayList;

import tpsia.tp1.agente.Estado;
import tpsia.tp1.agente.IObjetivo;
import tpsia.tp1.agente.VisionAmbiente;

public class BusquedaFactory {
	public static Busqueda Create(String tipoBusqueda, Estado estado, IObjetivo objetivo, ArrayList<VisionAmbiente> estadosAlcanzados) {
		Busqueda b;
		if (tipoBusqueda.compareToIgnoreCase("aestrella") == 0)
				b = new BusquedaAEstrella(estado, objetivo, estadosAlcanzados);
		else if (tipoBusqueda.compareToIgnoreCase("avara") == 0) 
				b = new BusquedaAvara(estado, objetivo, estadosAlcanzados);
		else if (tipoBusqueda.compareToIgnoreCase("profundidad") == 0)
				b = new BusquedaProfundidad(estado, objetivo, estadosAlcanzados);
		else if (tipoBusqueda.compareToIgnoreCase("costouniforme") == 0)
			b = new BusquedaCostoUniforme(estado, objetivo, estadosAlcanzados);
		else if (tipoBusqueda.compareToIgnoreCase("amplitud") == 0)
			b = new BusquedaAmplitud(estado, objetivo, estadosAlcanzados);
		else
			b = null;

		return b;
		
	}
}
