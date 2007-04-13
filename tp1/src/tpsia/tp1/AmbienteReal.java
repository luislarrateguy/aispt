package tpsia.tp1;

public class AmbienteReal implements IAmbiente {

	private int energiaPacman;
	private Matriz tablero;

	public Matriz getCeldasAdyacentes() {
		// TODO Auto-generated method stub
		return null;
	}

	public int energiaPacmanActual() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPosXIniPacman() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Float getEnergiaIniPacman() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPosYIniPacman() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void actualizar(int enePacman) {
		energiaPacman = enePacman;
	}

	public void moverPacman(OffsetX oX, OffsetY oY) {
		// TODO Actualizar la posición del  pacman en el ambiente
		// abajo ejemplo de como obtener el offset.
		oX.valor();
		oY.valor();
	}

}