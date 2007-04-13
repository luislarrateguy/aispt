package tpsia.tp1;


public class Agente {

	private Estado est;
	
	public Agente() {
		est = new Estado();
	}

	public IAccion actuar(Percepcion p) {
		// TODO actualizar visión del ambiente con la nueva percepcion
		System.out.println("PACMAN: actualizar visión del ambiente con la nueva percepcion");
		// TODO formular objetivo
		System.out.println("PACMAN: formular objetivo");
		// TODO buscar accion
		System.out.println("PACMAN: buscar accion");
		IAccion a = AvanzarArriba.getInstancia(); // Obtenida de búsqueda
		// TODO ejecutar accion
		System.out.println("PACMAN: ejecutar accion");
		a.ejecutar(est.getVAmbiente());
		// TODO actualizar estado
		return a;
	}

}