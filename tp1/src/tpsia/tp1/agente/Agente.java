package tpsia.tp1.agente;
import java.util.ArrayList;

import tpsia.tp1.Estado;
import tpsia.tp1.Percepcion;
import tpsia.tp1.acciones.IAccion;
import tpsia.tp1.busqueda.BusquedaCostoUniforme;
import tpsia.tp1.busqueda.IBusqueda;

public class Agente {

	private Estado est;
	private IBusqueda bus;
	private ArrayList<IAccion> acciones;
	private Objetivo o;
	
	public Agente() {
		this.est = new Estado();
		this.bus = new BusquedaCostoUniforme();
	}

	public IAccion actuar(Percepcion p) {
		this.actualizarEstado(p);
		this.acciones = bus.buscarSolucion(this.est);
		IAccion a = this.acciones.get(0);
		a.ejecutar(est.getVAmbiente());
		/* TODO A esta altura del código podríamos guardar cosas 
		 * como	última acción ejecutada y demás. */
		return a;
	}

	private void actualizarEstado(Percepcion p2) {
		// TODO actualizar visión del ambiente con la nueva percepcion
		System.out.println("PACMAN: actualizar visi�n del ambiente con la nueva percepcion");
		
	}

	private void formularObjetivo() {
		// TODO formular objetivo
		System.out.println("PACMAN: formular objetivo");
		this.o = new Objetivo(this.est);
	}
}