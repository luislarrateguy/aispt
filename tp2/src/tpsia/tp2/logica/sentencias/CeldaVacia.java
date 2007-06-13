package tpsia.tp2.logica.sentencias;

public class CeldaVacia extends Posicion {
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CeldaVacia))
			return false;
		
		return super.equals(o);
	}
}
