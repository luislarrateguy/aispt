package tpsia.tp2.logica.sentencias;

public class Conoce extends Posicion {
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Conoce))
			return false;
		
		return super.equals(o);
	}
}
