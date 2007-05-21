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
			//log.debug("El Pacman está muerto");
			cumplio = false;
		} else {
			
			convienePelear = (estado.getEnergia() +	estado.getPromedioVarEnergia(Pelear.getInstancia())) > 0;
			convieneMoverse = (estado.getEnergia() + estado.getPromedioVarEnergia(AvanzarAbajo.getInstancia())) > 0;			
				
			conoceTodo = estado.getAmbiente().conoceTodo();
			hayAlimentos = estado.getAmbiente().hayAlimentosSinComer();
			hayEnemigos = estado.getAmbiente().hayEnemigosVivos();
			
			/*
			// Condicion del objetivo
			boolean cumplio1 = false; //conoceTodo && !convieneMoverse;
			//boolean cumplio2 = conoceTodo && convieneMoverse && !hayAlimentos && !(hayEnemigos && convienePelear);
			boolean cumplio2 = conoceTodo && !hayAlimentos && !hayEnemigos;
			boolean cumplio3 = false; //!conoceTodo && !convieneMoverse;
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
			// Nacho dice:
			// Parado sobre alimento no creo que deba estar definido en el objetivo. Acordate que
			// el objetivo se mide en cualquier nodo e indica si el agente llegó a su meta o no.. no nos desviemos
			// Observa las salidas que te mandé. A veces hay enemigos y la variable "hayEnemigos" retorna falso!
			// y encima conoce todo! no encuentro el error!
			/*
			boolean condicion1 = conoceTodo && !hayAlimentos && !hayEnemigos;
			boolean condicion2 = conoceTodo && hayAlimentos && !convieneMoverse && !paradoSobreAlimento;
			boolean condicion3 = conoceTodo && hayEnemigos && !hayAlimentos && !convienePelear;
			
			boolean cumplio1 = conoceTodo && !convieneMoverse;
			boolean cumplio2 = conoceTodo && convieneMoverse && !hayAlimentos && !(hayEnemigos && convienePelear);
			boolean cumplio3 = !conoceTodo && !convieneMoverse;

			cumplio = (conoceTodo || !convieneMoverse) && (!hayAlimentos && (!hayEnemigos || !convienePelear));
			*/
			
			if (conoceTodo) {
				if (convieneMoverse &&
						(hayAlimentos ||
								(hayEnemigos && convienePelear))) {
					cumplio = false;
				} else {
					cumplio = true;
				}
					
			} else {
				if (convieneMoverse) {
					cumplio = false;
				} else {
					cumplio = true;
				}
			}
			
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
