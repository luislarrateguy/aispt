package tpsia.tp1;

public class FuncionesUtiles {
	/**
	 * Suma una cantidad 'incremento' a la 'posicion' dada.
	 * @param posicion
	 * @param incremento
	 * @return
	 */
	public static int sumarPosiciones (int posicion, int incremento) {
		
		if ( (posicion + incremento) >= 0)
			return ( (posicion + incremento) % 4);
		
		// Esto no está muy bien, pero no hay problemas para el simulador
		return (4 + (posicion + incremento));
	}
	
	public static int[] sumarPosiciones (int[] posicionActual, Offset o) {
		int[] resultado = new int[2];
		
		resultado[0] = sumarPosiciones(posicionActual[0], o.x());
		resultado[1] = sumarPosiciones(posicionActual[1], o.y());
		
		return resultado;
	}
}
