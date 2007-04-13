package tpsia.tp1;

import java.util.Vector;

public interface ICalculador {
	public int calcularEnergiaPacman(String accion);
	public void calcularPerformance(String accion);
	public Float getPerformance();
	public Vector inicializarEnemigo();
	public Vector inicializarPacman(); 
}
