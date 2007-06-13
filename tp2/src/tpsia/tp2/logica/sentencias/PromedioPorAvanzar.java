package tpsia.tp2.logica.sentencias;

public class PromedioPorAvanzar extends Sentencia {
	private Float promedio;
	
	public PromedioPorAvanzar() {
		super();
		
		this.promedio = null;
	}
	
	public PromedioPorAvanzar(float promedio, int tiempo) {
		super(tiempo);
		
		this.promedio = promedio;
	}

	public Float getPromedio() {
		return promedio;
	}

	public void setPromedio(Float promedio) {
		this.promedio = promedio;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PromedioPorAvanzar))
			return false;
		
		PromedioPorAvanzar p = (PromedioPorAvanzar)o;
		
		if (!super.equals(p) || this.promedio.equals(p.promedio))
			return false;
		
		return true;
	}
}
