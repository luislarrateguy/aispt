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

import tpsia.tp1.acciones.AvanzarAbajo;
import tpsia.tp1.acciones.AvanzarArriba;
import tpsia.tp1.acciones.AvanzarDerecha;
import tpsia.tp1.acciones.AvanzarIzquierda;
import tpsia.tp1.acciones.Comer;
import tpsia.tp1.acciones.Pelear;

public class Main {

	private static String debugFile = "logger.config";
	private static String xmlPath;
	private static ConfLoader confLoader;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Agrego un for por si queremos hacer varias
		 * simulaciones.
		 */

		if (parseArguments(args)) {
			PropertyConfigurator.configure(debugFile);
			Logger log = Logger.getLogger(Main.class);
			confLoader = ConfLoader.GetInstance();
			confLoader.setConfiguration();
			
			log.debug("Iniciando simulación");
			Simulador s = new Simulador();
			s.comenzarSimulacion();
			s.mostrarPerformance();

		}
	}
	
    public static boolean parseArguments(String[] args) {
    	int cant = args.length;
        if (cant > 0) {
            //String arg = args[0];
            debugFile = args[0];
            xmlPath = args[1];
            /*
            if (arg.startsWith("?") || arg.equals("-?") || arg.equals("-h") || arg.startsWith("--h")) {
                mostrarAyuda();
                return false;
            }
        	if (cant == 3 && args[1].equals("--busqueda")) {
        		if (args[2].equals("aestrella") ||
        				args[2].equals("avara") ||
        				args[2].equals("profundida") ||
        				args[2].equals("amplitud") ||
        				args[2].equals("costouniforme"))
            	busqueda = args[2];
        		debugFile = args[0];
            }
        	return true;
        	*/
        }
        return true;
    }

	private static void mostrarAyuda() {
		/*
		System.out.println("\nUso:");
		System.out.println("java -jar simulador.jar --help");
		System.out.println("\t muestra esta ayuda");
		System.out.println("java -jar simulador.jar logger.config --busqueda [tipo_busqueda]");
		System.out.println("\t permite seleccionar el tipo de busqueda que");
		System.out.println("\t utilizará el Agente.");
		System.out.println("\t\t tipo_busqueda: amplitud,avara,aestrella,");
		System.out.println("\t\t                profundidad,costouniforme");
		*/
	}

	public static String getXmlPath() {
		return xmlPath;
	}
}
