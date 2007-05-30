/*

 Copyright (c) 2007 by Luis I. Larrateguy y Milton Pividori
 All Rights Reserved

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */

package tpsia.tp2;

import java.util.Vector;

import org.apache.log4j.*;
import tpsia.tp2.acciones.*;
import tpsia.tp2.agente.Agente;
import calculador.Calculador;
import calculador.Pair;

public class Simulador {
	
	private static Simulador instancia = null;
	private Calculador calculador;
	private AmbienteReal ambiente;
	private Agente pacman;
	private String busqueda;
	
	public Simulador() {
		this.calculador = new Calculador("Grupo 7");
		this.ambiente = new AmbienteReal();
		this.busqueda = "aestrella";
		
		/* Inicializando Acciones para acelerar ejecución */
		Pelear.getInstancia();
		Comer.getInstancia();
		AvanzarArriba.getInstancia();
		AvanzarDerecha.getInstancia();
		AvanzarAbajo.getInstancia();
		AvanzarIzquierda.getInstancia();
	}
	
	public Simulador(String tipoBusqueda) {
		this();
		this.busqueda = tipoBusqueda;
	}
	
	/**
	 * Inicializa la simulación (ver inicializarSimulacion). Entra en un
	 * bucle creando una percepción. Se la envía al pacman y espera una
	 * acción que éste le devuelva. Calcula su nueva energía por medio
	 * del Calculador. Ejecuta dicha acción en el ambiente real y actualiza
	 * la energía del pacman en el mismo.
	 */
	public void comenzarSimulacion() {
		Logger log = Logger.getLogger(Simulador.class);
		
		log.info("Iniciando simulación...");
		log.info("Utilizando estrategia: " + this.busqueda);
		this.inicializarSimulacion();

		Accion a;
		Percepcion p;
		int energiaPacman;
		while (!this.finSimulacion()) {
			/* Creo la percepción, se la envío al agente, y espero una acción
			 * escogida por él. */
			log.debug("Armando percepcion");
			p = new Percepcion(ambiente.getCeldasAdyacentes(),
					ambiente.getEnergiaPacman(), ambiente.getPosicionInicialPacman());
			log.debug("Enviando percepcion a Pacman");
			
			a = pacman.actuar(p);
			try {
				log.debug("Calculando energia pacman");
				energiaPacman = calculador.calcularEnergiaPacMan(a.getTipoAccion());
				log.debug("Ejecutando accion en ambiente");
				a.ejecutar(ambiente);
				log.debug("Actualizando ambiente");
				ambiente.setEnergiaPacman(energiaPacman);
			} catch (Exception e) {
				// No se hace nada. La ejecución termina en la 
				// proxima iteración del NullPointer
			}
		}
		
		log.info("### Fin de la simulación ###");
		this.pacman.mostrarEstadoFinal();
	}
	
	/**
	 * Crea el agente e enicializa la posición de los enemigos, la del
	 * pacman y la de la comida. Calcula la energía inicial de agente.
	 * Con todo esto inicializa el ambiente real.
	 */
	private void inicializarSimulacion() {
		Logger log = Logger.getLogger(Simulador.class);
		
		Vector posicionesEnemigos = calculador.inicializarEnemigo();
		Pair posicionPacMan 	= calculador.getPosicionInicial();
		Vector posicionesComida = calculador.inicializarComida();
		
		int energiaPacman = calculador.calcularEnergiaPacMan();
		
		this.pacman = new Agente(energiaPacman,this.busqueda);
		
		this.ambiente.inicializar(energiaPacman,
				posicionPacMan,
				posicionesEnemigos,
				posicionesComida);
		
		log.info(this.ambiente);
	}
	
	private boolean finSimulacion() {
		Logger log = Logger.getLogger(Simulador.class);
		log.debug("Chequeando si termina");
		
		return !this.ambiente.agenteVivo() || this.pacman.cumplioObjetivo();
	}
	
	public static Simulador GetInstancia() {
		if (instancia == null) {
			instancia = new Simulador();
		}
		return instancia;
	}
	
	public void mostrarPerformance() {
		Logger log = Logger.getLogger(Simulador.class);
		log.info("Desempeño del PACMAN: " +
				this.calculador.getPerformance());
	}
}
