package tpsia.tp2.logica.sentencias;

@SuppressWarnings("serial")
public class SentenciaException extends Exception {

	public SentenciaException() {
		super("Hubo algún error con alguna sentencia");
	}
	
	public SentenciaException(String msj) {
		super(msj);
	}
}
