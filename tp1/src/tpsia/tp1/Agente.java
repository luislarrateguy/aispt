package tpsia.tp1;
import java.util.*;

public class Agente {

	private Estado est;
	private IBusqueda bus;
	private ArrayList<IAccion> acciones;
	private IProblema p;
	private Objetivo o;
	
	public Agente() {
		this.est = new Estado();
		this.bus = new BusquedaCostoUniforme();
	}

	public IAccion actuar(Percepcion p) {
		this.actualizarEstado(p);
		this.formularObjetivo(); //hace falta?
		this.formularProblema(); //hace falta?
		this.acciones = bus.buscarSolucion(this.p);
		IAccion a = this.acciones.get(0);
		a.ejecutar(est.getVAmbiente());
		/* TODO A esta altura del código podríamos guardar cosas 
		 * como	última acción ejecutada y demás. */
		return a;
	}

	private void actualizarEstado(Percepcion p2) {
		// TODO actualizar visión del ambiente con la nueva percepcion
		System.out.println("PACMAN: actualizar visión del ambiente con la nueva percepcion");
		
	}

	private void formularObjetivo() {
		// TODO formular objetivo
		System.out.println("PACMAN: formular objetivo");
		this.o = new Objetivo(this.est);
	}

	private void formularProblema() {
		// TODO formular problema;
		System.out.println("PACMAN: formular problema");
		this.p = new Problema(this.est,this.o);
	}


}