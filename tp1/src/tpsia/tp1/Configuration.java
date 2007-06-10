/*
 * Created on 23/10/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package tpsia.tp1;

/**
 * @author luis
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Configuration {

	private String estrategia;
	private int costoPelear;
	private int costoComer;
	private int costoAvanzarIzquierda;
	private int costoAvanzarDerecha;
	private int costoAvanzarArriba;
	private int costoAvanzarAbajo;
	
	public int getCostoAvanzarAbajo() {
		return costoAvanzarAbajo;
	}
	public void setCostoAvanzarAbajo(int costoAvanzarAbajo) {
		this.costoAvanzarAbajo = costoAvanzarAbajo;
	}
	public int getCostoAvanzarArriba() {
		return costoAvanzarArriba;
	}
	public void setCostoAvanzarArriba(int costoAvanzarArriba) {
		this.costoAvanzarArriba = costoAvanzarArriba;
	}
	public int getCostoAvanzarDerecha() {
		return costoAvanzarDerecha;
	}
	public void setCostoAvanzarDerecha(int costoAvanzarDerecha) {
		this.costoAvanzarDerecha = costoAvanzarDerecha;
	}
	public int getCostoAvanzarIzquierda() {
		return costoAvanzarIzquierda;
	}
	public void setCostoAvanzarIzquierda(int costoAvanzarIzquierda) {
		this.costoAvanzarIzquierda = costoAvanzarIzquierda;
	}
	public int getCostoComer() {
		return costoComer;
	}
	public void setCostoComer(int costoComer) {
		this.costoComer = costoComer;
	}
	public int getCostoPelear() {
		return costoPelear;
	}
	public void setCostoPelear(int costoPelear) {
		this.costoPelear = costoPelear;
	}
	public String getEstrategia() {
		return estrategia;
	}
	public void setEstrategia(String estrategia) {
		this.estrategia = estrategia;
	}

}
