package org.utn.ia.tp1;

import java.util.Vector;

public class Simulador {
	
	private static Simulador instancia = null;
	private Calculador calc;
	private AmbienteReal amb;
	private Agente pacman;

	private Simulador() {
		this.calc = new Calculador();
		this.amb = new AmbienteReal();
	}
	public void comenzarSimulacion() {
		this.inicializarSimulacion();

		IAccion a;
		Percepcion p;
		int enePacman;
		while (this.finSimulacion()) {
			p = new Percepcion(amb.getCeldasAdyacentes(),
					amb.getEnergiaIniPacman(), amb.getPosXIniPacman(),
					amb.getPosYIniPacman());
			a = pacman.actuar(p);
			
			// avisar al calculador
			enePacman = calc.calcularEnergiaPacman(a.getTipoAccion());
			calc.calcularPerformance(a.getTipoAccion());
			
			// ejecutar la acción y actualizar el ambiente
			a.ejecutar(amb);
			amb.actualizar(enePacman);
		}
	}

	private void inicializarSimulacion() {
		pacman = new Agente();
		Vector pene = calc.inicializarEnemigo();
		Vector ppac = calc.inicializarPacman();
		//TODO Inicializar el ambiente del simulador
	}
	private boolean finSimulacion() {
		// TODO Agregar la condición de fin de simulacion
		return true;
	}
	public static Simulador GetInstancia() {
		if (instancia == null) {
			instancia = new Simulador();
		}
		return instancia;
	}
	public void mostrarPerformance() {
		Float f = calc.getPerformance();
		// TODO Agregar código para modificar la instancia
	}
}