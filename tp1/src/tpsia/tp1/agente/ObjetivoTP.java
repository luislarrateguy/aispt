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
		boolean convienePelear,convieneMoverse,conoceTodo,hayAlimentos,hayEnemigos,paradoSobreAlimento;
		
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
			paradoSobreAlimento = estado.getAmbiente().paradoSobreAlimento();
			
			// Condicion del objetivo
			// Suposición 1: convienePelear => convieneMoverse
			// Suposición 2: paradoSobreAlimento => hayAlimentos
			
			/* Aca hay que armar una tabla como esta para armar todas las condiciones donde se cumple
			 * el objetivo:
			 * 
			 * conoceTodo	hayAlimentos	convieneMoverse 	hayEnemigos		convienePelear	paradoSobreAlimento 	¿Objetivo cumplido?
			 * 1			1				1					1				1				1						Si			
			 * ...
			 * ...
			 * ...
			 */
			
			// Estas son las condiciones que estoy armando, pero todavía no se usan...
			boolean condicion1 = conoceTodo && !hayAlimentos && !hayEnemigos;
			boolean condicion2 = conoceTodo && hayAlimentos && !convieneMoverse && !paradoSobreAlimento;
			boolean condicion3 = conoceTodo && hayEnemigos && !hayAlimentos && !convienePelear;
			
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
