package org.utn.ia.tp1;

import java.util.Vector;

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
	System.out.println("SIM: Iniciando simulacion...");
		this.inicializarSimulacion();

		IAccion a;
		Percepcion p;
		int enePacman;
		while (!this.finSimulacion()) {
		System.out.println("SIM: Armando percepcion...");
			p = new Percepcion(amb.getCeldasAdyacentes(),
					amb.getEnergiaIniPacman(), amb.getPosXIniPacman(),
					amb.getPosYIniPacman());
		System.out.println("SIM: Enviando percepcion a Pacman");
			a = pacman.actuar(p);
			
			// avisar al calculador
		System.out.println("SIM: Calculando energia pacman");
			enePacman = calc.calcularEnergiaPacman(a.getTipoAccion());
		System.out.print("SIM: Calculando desempeño al efectuar ");
		System.out.println(a.getTipoAccion());
			calc.calcularPerformance(a.getTipoAccion());
			
			// ejecutar la acción y actualizar el ambiente
		System.out.println("SIM: Ejecutando accion en ambiente");
			a.ejecutar(amb);
		System.out.println("SIM: Actualizando ambiente");
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
		System.out.println("SIM: Chequeando si termina...");
		this.cont--;
		return (this.cont == 0);
	}
	public static Simulador GetInstancia() {
		if (instancia == null) {
			instancia = new Simulador();
		}
		return instancia;
	}
	public void mostrarPerformance() {
		System.out.println("SIM: Mostrando desempeño del PACMAN");
		Float f = calc.getPerformance();
		// TODO Agregar código para modificar la instancia
	}
}