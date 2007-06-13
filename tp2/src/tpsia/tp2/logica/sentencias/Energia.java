package tpsia.tp2.logica.sentencias;

public class Energia extends Sentencia {
	private Integer energia;
	
	public Energia() {
		super();
		
		this.energia = null;
	}
	
	public Energia(int energia, int tiempo) {
		super(tiempo);
		
		this.energia = energia;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Energia))
			return false;
		
		Energia e = (Energia)o;
		
		if (!super.equals(e) || this.energia.equals(e.energia))
			return false;
		
		return true;
	}
}
