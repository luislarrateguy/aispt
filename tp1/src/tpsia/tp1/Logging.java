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

public class Logging {
	private static boolean modoLoggingActivado = true;
	
	public static void logDebug(String mensaje) {
		if (!modoLoggingActivado) return;
		
		System.out.println("DEBUG: " + mensaje);
	}
	
	public static void logError(String mensaje) {
		/* Los errores se loguean siempre, no importa si el modo
		 * logging está activado o no. */
		System.err.println("ERROR: " + mensaje);
	}
	
	public static void activarModoLogging() {
		modoLoggingActivado = true;
	}
	
	public static void desactivarModoLogging() {
		modoLoggingActivado = false;
	}
}
