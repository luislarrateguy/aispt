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

import tpsia.tp1.acciones.IAccion;
import tpsia.tp1.agente.Agente;
import calculador.*;

public class Simulador {
	
	private static Simulador instancia = null;
	private Calculador calc;
	private AmbienteReal amb;
	private Agente pacman;
	
	int cont;

	private Simulador() {
		this.calc = new Calculador();
		this.amb = new AmbienteReal();
		this.cont = 5;
	}
	
	public void comenzarSimulacion() {
		Logging.logDebug("SIM: Iniciando simulación...");
		this.inicializarSimulacion();

		IAccion a;
		Percepcion p;
		int enePacman;
		while (!this.finSimulacion()) {
			Logging.logDebug("SIM: Armando percepcion...");
			p = new Percepcion(amb.getCeldasAdyacentes(),
					amb.getEnergiaIniPacman(), amb.getPosXIniPacman(),
					amb.getPosYIniPacman());
			Logging.logDebug("SIM: Enviando percepcion a Pacman");
			a = pacman.actuar(p);
			
			// avisar al calculador
			Logging.logDebug("SIM: Calculando energia pacman");
			enePacman = calc.calcularEnergiaPacMan(a.getTipoAccion());

			// ejecutar la acción y actualizar el ambiente
			Logging.logDebug("SIM: Ejecutando accion en ambiente");
			a.ejecutar(amb);
			Logging.logDebug("SIM: Actualizando ambiente");
			amb.actualizar(enePacman);
		}
	}

	private void inicializarSimulacion() {
		pacman = new Agente();
		Vector posicionesEnemigos = calc.inicializarEnemigo();
		Pair posicionPacMan 	= calc.getPosicionInicial();
		Vector posicionesComida = calc.inicializarComida();
		//TODO Inicializar el ambiente del simulador
	}
	
	private boolean finSimulacion() {
		// TODO Agregar la condición de fin de simulacion
		Logging.logDebug("SIM: Chequeando si termina...");
		this.cont--;
		// return pacman.cumplioObjetivo();
		return (this.cont == 0);
	}
	
	public static Simulador GetInstancia() {
		if (instancia == null) {
			instancia = new Simulador();
		}
		return instancia;
	}
	
	public void mostrarPerformance() {
		Logging.logDebug("SIM: Mostrando desempeño del PACMAN");
		int f = calc.getPerformance();
		// TODO Agregar código para mostrar
	}
}
