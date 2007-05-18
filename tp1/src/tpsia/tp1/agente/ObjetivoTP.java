package tpsia.tp1.agente;

import org.apache.log4j.Logger;

import tpsia.tp1.acciones.AvanzarAbajo;
import tpsia.tp1.acciones.Pelear;
import tpsia.tp1.busqueda.Nodo;

public class ObjetivoTP implements IObjetivo {
	private static ObjetivoTP instancia;
	
	public ObjetivoTP() {
		super();
	}
	
	public static ObjetivoTP getInstancia() {
		if (instancia == null)
			instancia = new ObjetivoTP();
		
		return instancia;
	}
	
	public boolean cumpleObjetivo(Estado estado) {
		boolean cumplio = false;
		boolean convienePelear,convieneMoverse,conoceTodo,hayAlimentos,hayEnemigos;
		
		Logger log = Logger.getLogger("Pacman.Busqueda.Objetivo");
		
		if (estado.getEnergia() <= 0) {
			log.debug("El Pacman está muerto");
			cumplio = false;
		} else {
			convienePelear = estado.getEnergia() >
				estado.getPromedioVarEnergia(Pelear.getInstancia());
			convieneMoverse = estado.getEnergia() >
				estado.getPromedioVarEnergia(AvanzarAbajo.getInstancia());			
			conoceTodo = estado.getAmbiente().conoceTodo();
			hayAlimentos = estado.getAmbiente().hayAlimentosSinComer();
			hayEnemigos = estado.getAmbiente().hayEnemigosVivos();

			
			
			// Condicion del objetivo
			boolean cumplio1 = conoceTodo && !convieneMoverse;
			boolean cumplio2 = conoceTodo && convieneMoverse && !hayAlimentos && !(hayEnemigos && convienePelear);
			boolean cumplio3 = !conoceTodo && !convieneMoverse;

			cumplio = cumplio1 || cumplio2 || cumplio3;
			/*
			 * Version simplificada:
			 * cumplio = 
			 */
			
			/*
			if (conoceTodo) {
				if (convieneMoverse &&
						(hayAlimentos ||
								(estado.getAmbiente().hayEnemigosVivos() && convienePelear))) {
					cumplio = false;
				} else {
					cumplio = true;
				}
					
			} else {
				if (convieneMoverse) {
					return false;
				} else {
					return true;
				}
			}
			*/
			if (cumplio) {
				log.debug("Conviene pelear: "+convienePelear + " Conviene moverse: "+convieneMoverse +
						" Conoce todo: "+conoceTodo + " Hay alimentos: "+hayAlimentos + " Hay enemigos: "+hayEnemigos);
			}
		}
		
		return cumplio;
	}

	public boolean cumpleObjetivo(Nodo nodo) {
		return this.cumpleObjetivo(nodo.getEstado());
	}

}
