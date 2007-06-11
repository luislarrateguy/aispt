/*

 Copyright (c) 2007 by Luis I. Larrateguy y Milton Pividori
 All Rights Reserved

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

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
	private int heuristica1;
	private int heuristica2;
	private int heuristica3;
	
	public int getCostoAvanzarAbajo() {
		return costoAvanzarAbajo;
	}
	public void setCostoAvanzarAbajo(int costoAvanzarAbajo) {
		this.costoAvanzarAbajo = costoAvanzarAbajo;
	}
	public int getHeuristica1() {
		return heuristica1;
	}
	public void setHeuristica1(int h1) {
		this.heuristica1 = h1;
	}
	public int getHeuristica2() {
		return heuristica2;
	}
	public void setHeuristica2(int h2) {
		this.heuristica2 = h2;
	}
	public int getHeuristica3() {
		return heuristica3;
	}
	public void setHeuristica3(int h3) {
		this.heuristica3 = h3;
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
