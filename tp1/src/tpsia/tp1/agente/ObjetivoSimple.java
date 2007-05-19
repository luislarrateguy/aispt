package tpsia.tp1.agente;

import org.apache.log4j.Logger;

import tpsia.tp1.acciones.AvanzarAbajo;
import tpsia.tp1.acciones.Pelear;
import tpsia.tp1.busqueda.Nodo;

public class ObjetivoSimple implements IObjetivo {
	private static ObjetivoSimple instancia;
	
	public ObjetivoSimple() {
		super();
	}
	
	public static ObjetivoSimple getInstancia() {
		if (instancia == null)
			instancia = new ObjetivoSimple();
		
		return instancia;
	}
	
	public boolean cumpleObjetivo(Estado estado) {
		boolean cumplio = false;
		boolean conoceTodo,hayAlimentos,hayEnemigos;
		
		Logger log = Logger.getLogger("Pacman.Busqueda.Objetivo");	
		if (estado.getEnergia() <= 0) {
			cumplio = false;
		} else {		
			conoceTodo = estado.getAmbiente().conoceTodo();
			hayAlimentos = estado.getAmbiente().hayAlimentosSinComer();
			hayEnemigos = estado.getAmbiente().hayEnemigosVivos();

			cumplio =  conoceTodo && !hayAlimentos && !hayEnemigos;

			if (cumplio) {
				log.debug("Conoce todo: "+conoceTodo + " Hay alimentos: "+hayAlimentos + 
						" Hay enemigos: "+hayEnemigos);
			}
		}
		
		return cumplio;
	}

	public boolean cumpleObjetivo(Nodo nodo) {
		return this.cumpleObjetivo(nodo.getEstado());
	}

}
