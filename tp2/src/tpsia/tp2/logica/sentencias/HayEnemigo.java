package tpsia.tp2.logica.sentencias;

public class HayEnemigo extends Posicion {
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof HayEnemigo))
			return false;
		
		return super.equals(o);
	}
}
