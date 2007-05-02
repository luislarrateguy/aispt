package tpsia.tp1.agente;

import tpsia.tp1.busqueda.Nodo;

public class ObjetivoTP implements IObjetivo {
	private static ObjetivoTP instancia;
	
	public ObjetivoTP() {
		super();
	}
	
	public static ObjetivoTP getInstancia() {
		if (instancia == null)
			instancia = new ObjetivoTP();
		
		return instancia;
	}
	
	public boolean cumpleObjetivo(Estado estado) {
		boolean convienePelear = estado.getEnergia() >
			estado.getPromedioEnergiaPerdidaPelear();
		boolean convieneMoverse = estado.getEnergia() >
			estado.getPromEnergiaPerdidaAvanzar();
		
		if (estado.getAmbiente().conoceTodo()) {
			if (convieneMoverse &&
					(estado.getAmbiente().hayAlimentosSinComer() ||
							(estado.getAmbiente().hayEnemigosVivos() && convienePelear)))
				return false;
			else
				return true;
		}
		else {
			if (convieneMoverse)
				return false;
			else
				return true;
		}
			
	}

	public boolean cumpleObjetivo(Nodo nodo) {
		return this.cumpleObjetivo(nodo.getEstado());
	}

}
