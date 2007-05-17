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

package tpsia.tp1;

import java.util.Vector;

import tpsia.tp1.acciones.*;
import tpsia.tp1.agente.Agente;
import calculador.Calculador;
import calculador.Pair;

public class Simulador {
	
	private static Simulador instancia = null;
	private Calculador calculador;
	private AmbienteReal ambiente;
	private Agente pacman;


	public Simulador() {
		this.calculador = new CalculadorCustom("Grupo 28");
		this.ambiente = new AmbienteReal();
		
		/* Inicializando Acciones para acelerar ejecución */
		AvanzarAbajo.getInstancia();
		AvanzarArriba.getInstancia();
		AvanzarDerecha.getInstancia();
		AvanzarIzquierda.getInstancia();
		Pelear.getInstancia();
		Comer.getInstancia();
		NoAccion.getInstancia();
	}
	
	/**
	 * Inicializa la simulación (ver inicializarSimulacion). Entra en un
	 * bucle creando una percepción. Se la envía al pacman y espera una
	 * acción que éste le devuelva. Calcula su nueva energía por medio
	 * del Calculador. Ejecuta dicha acción en el ambiente real y actualiza
	 * la energía del pacman en el mismo.
	 */
	public void comenzarSimulacion() {
		Logging.logDebug("SIM: Iniciando simulación...");
		this.inicializarSimulacion();

		Accion a;
		Percepcion p;
		int energiaPacman;
		while (!this.finSimulacion()) {
			/* Creo la percepción, se la envío al agente, y espero una acción
			 * escogida por él. */
			Logging.logDebug("SIM: Armando percepcion...");
			p = new Percepcion(ambiente.getCeldasAdyacentes(),
					ambiente.getEnergiaPacman(), ambiente.getPosicionInicialPacman());
			Logging.logDebug("SIM: Enviando percepcion a Pacman");
			
			a = pacman.actuar(p);
			if (a.getClass().equals(NoAccion.class))
				break;
		
			// avisar al calculador
			Logging.logDebug("SIM: Calculando energia pacman");
			energiaPacman = calculador.calcularEnergiaPacMan(a.getTipoAccion());

			// ejecutar la acción y actualizar el ambiente
			Logging.logDebug("SIM: Ejecutando accion en ambiente");
			try {
				a.ejecutar(ambiente);
			} catch (Exception e) {
				// Mostrar la excepción.
				e.printStackTrace();
			}
			
			Logging.logDebug("SIM: Actualizando ambiente");
			ambiente.setEnergiaPacman(energiaPacman);
		}
		
		Logging.logMensaje(" ### Fin de la simulación ### ");
		this.pacman.mostrarEstadoFinal();
	}
	
	/**
	 * Crea el agente e enicializa la posición de los enemigos, la del
	 * pacman y la de la comida. Calcula la energía inicial de agente.
	 * Con todo esto inicializa el ambiente real.
	 */
	private void inicializarSimulacion() {
		Vector posicionesEnemigos = calculador.inicializarEnemigo();
		Pair posicionPacMan 	= calculador.getPosicionInicial();
		Vector posicionesComida = calculador.inicializarComida();
		
		/* FIXME: Esto no estaría bien. Tendría que haber un método
		 * que retorne la energía inicial, ya que así le estamos quitando,
		 * ¿o me equivoco? 
		 * FIXME: Esto fue resuelto con la nueva clase?
		 * */
		int energiaPacman = calculador.calcularEnergiaPacMan();
		
		this.pacman = new Agente(energiaPacman);
		
		this.ambiente.inicializar(energiaPacman,
				posicionPacMan,
				posicionesEnemigos,
				posicionesComida);
		
		Logging.logMensaje(this.ambiente.draw());
	}
	
	private boolean finSimulacion() {
		Logging.logDebug("SIM: Chequeando si termina...");
		/* La condición lógica de abajo es equivalente a 
		 * preguntar solamente si cumplióObjetivo o si murió
		 * debido a leyes de lógica
		 */
		/*
		return ( (this.pacman.cumplioObjetivo() && this.pacman.vivo()) ||
				!this.pacman.vivo());
		*/
		return this.pacman.cumplioObjetivo() || !this.ambiente.agenteVivo();
	}
	
	public static Simulador GetInstancia() {
		if (instancia == null) {
			instancia = new Simulador();
		}
		return instancia;
	}
	
	public void mostrarPerformance() {
		Logging.logDebug("SIM: Desempeño del PACMAN: " +
				this.calculador.getPerformance());
	}
}
