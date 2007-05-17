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

import org.apache.log4j.*;
import org.apache.log4j.BasicConfigurator;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Agrego un for por si queremos hacer varias
		 * simulaciones.
		 */
		

		Logger log = Logger.getLogger(Main.class);
		PropertyConfigurator.configure(args[0]);
		
		//BasicConfigurator.configure();

		for (int i=1; i<=1; i++) {
				log.debug("Iniciando simulación nro : "+ Integer.toString(i));
			Simulador s = new Simulador();
			s.comenzarSimulacion();
			s.mostrarPerformance();
		}
	}
}
