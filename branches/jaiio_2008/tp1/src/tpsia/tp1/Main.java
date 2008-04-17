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
import calculador.Calculador; 
import tpsia.tp2.Simulador;

public class Main {

	private static String debugFile = "logger.config";
	private static String xmlPath = "config.xml";
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
			long time;
			log.debug("Iniciando simulación");

			SimuladorBusqueda s;
			Calculador calc,c;
			for(int i=1;i<=100;i++) {
				 calc = new Calculador();

				String[] xmls = {"configAvara.xml","configAvara2.xml",
						"configDesbalanceado.xml","configProfundo.xml"};
				
				for (int a=0; a<=3;a++) {	
					xmlPath = xmls[a];
					confLoader = new ConfLoader();
					confLoader.setConfiguration();
					time = System.nanoTime();
					c = calc.clone();
					
					s = new SimuladorBusqueda(c);
					s.comenzarSimulacion();
					s.mostrarPerformance();
					System.out.print(System.nanoTime()-time + ",,");
				}
				System.out.println();
				/*
				 * Ejecuta el de Prolog. Tendriamos que ver algunas cosas.
				 * Habria que revisar el tema de las coordenadas también.
				 * Y ver si la performance esta siendo bien calculada.
				time = System.nanoTime();
				Simulador sc = new Simulador(cCono);
				sc.comenzarSimulacion();
				sc.mostrarPerformance();
				System.out.println(System.nanoTime()-time + ",,");
				*/
			}
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
