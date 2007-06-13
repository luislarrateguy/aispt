package tpsia.tp2.logica.sentencias;

public abstract class Sentencia {
	protected Integer tiempo;
	
	public Sentencia() {
		this.tiempo = null;
	}
	
	public Sentencia(int tiempo) {
		this.tiempo = tiempo;
	}

	public int getTiempo() {
		return tiempo;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Sentencia))
			return false;
		
		Sentencia s = (Sentencia)o;
		
		if (s.tiempo != this.tiempo)
			return false;
		
		return true;
	}
}
