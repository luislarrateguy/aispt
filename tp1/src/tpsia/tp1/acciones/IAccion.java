package tpsia.tp1.acciones;

import tpsia.tp1.IAmbiente;


public interface IAccion {

	public void ejecutar(IAmbiente amb);
	public String getTipoAccion();

}