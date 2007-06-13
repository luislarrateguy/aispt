package tpsia.tp2.logica.sentencias;

public class HayComida extends Posicion {
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof HayComida))
			return false;
		
		return super.equals(o);
	}
}
