package tpsia.tp2.logica.sentencias;

public class CumplioObjetivo extends Sentencia {
	private Boolean cumplioObjetivo;
	
	public CumplioObjetivo() {
		super();
		
		this.cumplioObjetivo = null;
	}
	
	public CumplioObjetivo(boolean cumplioObjetivo, int tiempo) {
		super(tiempo);
		
		this.cumplioObjetivo = cumplioObjetivo;
	}
}
