package tpsia.tp2.logica.sentencias;

public class PromedioPorPelear extends Sentencia {
	private Float promedio;
	
	public PromedioPorPelear() {
		super();
		
		this.promedio = null;
	}
	
	public PromedioPorPelear(float promedio, int tiempo) {
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
		if (!(o instanceof PromedioPorPelear))
			return false;
		
		PromedioPorPelear p = (PromedioPorPelear)o;
		
		if (!super.equals(p) || this.promedio.equals(p.promedio))
			return false;
		
		return true;
	}
}
