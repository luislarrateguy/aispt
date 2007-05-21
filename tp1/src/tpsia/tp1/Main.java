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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {

	private static String busqueda = "aestrella";
	private static String debugFile = "logger.config";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Agrego un for por si queremos hacer varias
		 * simulaciones.
		 */

		if (parseArguments(args)) {
			PropertyConfigurator.configure(args[0]);
			Logger log = Logger.getLogger(Main.class);
			
			for (int i=1; i<=1; i++) {
					log.debug("Iniciando simulación nro : "+ Integer.toString(i));
				Simulador s = new Simulador(busqueda);
				s.comenzarSimulacion();
				s.mostrarPerformance();
			}
		}
	}
	
    public static boolean parseArguments(String[] args) {
    	int cant = args.length;
        if (cant > 0) {
            String arg = args[0];
            if (arg.startsWith("?") || arg.equals("-?") || arg.equals("-h") || arg.startsWith("--h")) {
                mostrarAyuda();
                return false;
            }
        	if (cant == 3 && args[1].equals("--busqueda")) {
        		if (args[2].equals("aestrella") ||
        				args[1].equals("avara") ||
        				args[1].equals("profundida") ||
        				args[1].equals("amplitud") ||
        				args[1].equals("costouniforme"))
            	busqueda = args[1];
        		debugFile = args[0];
            }
        }
        return true;
    }

	private static void mostrarAyuda() {
		System.out.println("\nUso:");
		System.out.println("java -jar simulador.jar --help");
		System.out.println("\t muestra esta ayuda");
		System.out.println("java -jar simulador.jar logger.config --busqueda [tipo_busqueda]");
		System.out.println("\t permite seleccionar el tipo de busqueda que");
		System.out.println("\t utilizará el Agente.");
		System.out.println("\t\t tipo_busqueda: amplitud,avara,aestrella,");
		System.out.println("\t\t                profundidad,costouniforme");
		
	}
}
