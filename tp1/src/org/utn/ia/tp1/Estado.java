package org.utn.ia.tp1;

public class Estado {

	private int energia;
	private float energiaPromedio;
	private VisionAmbiente visionAmb;
	private Estado() {
		this.energia = 0;
		this.energiaPromedio =  (float) 0.00;
		this.visionAmb = new VisionAmbiente();
	}
	public IAmbiente getVAmbiente() {
		// TODO Auto-generated method stub
		return visionAmb;
	}
}