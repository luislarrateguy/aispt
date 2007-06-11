/*
 * Created on 23/10/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package tpsia.tp1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import tpsia.tp1.acciones.Accion;
import tpsia.tp1.acciones.AvanzarAbajo;
import tpsia.tp1.acciones.AvanzarArriba;
import tpsia.tp1.acciones.AvanzarDerecha;
import tpsia.tp1.acciones.AvanzarIzquierda;
import tpsia.tp1.acciones.Comer;
import tpsia.tp1.acciones.Pelear;
import tpsia.tp1.busqueda.Busqueda;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author luis
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConfLoader {

	private static ConfLoader lc = new ConfLoader();
	private Configuration conf;
	
	private ConfLoader() {
		XStream xstream = new XStream(new DomDriver());
		String xml = "";
		String line = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					Main.getXmlPath())));
			while((line = br.readLine()) != null) {
				xml += line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		xstream.alias("Configuration", Configuration.class);
		this.conf = (Configuration) xstream.fromXML(xml);
	}
	public static ConfLoader GetInstance() {
		return lc;
	}
	public void setConfiguration() {
		Logger log = Logger.getLogger(Simulador.class);	
		log.info("CostoAvanzarAarriba:");
		log.info(this.conf.getCostoAvanzarArriba());
		AvanzarArriba.getInstancia().setCosto(this.conf.getCostoAvanzarArriba());
		
		log.info("CostoAvanzarDerecha:");
		log.info(this.conf.getCostoAvanzarDerecha());
		AvanzarDerecha.getInstancia().setCosto(this.conf.getCostoAvanzarDerecha());
		
		log.info("CostoAvanzarAbajo:");
		log.info(this.conf.getCostoAvanzarAbajo());
		AvanzarAbajo.getInstancia().setCosto(this.conf.getCostoAvanzarAbajo());
		
		log.info("CostoAvanzarIzquierda:");
		log.info(this.conf.getCostoAvanzarIzquierda());
		AvanzarIzquierda.getInstancia().setCosto(this.conf.getCostoAvanzarIzquierda());
		
		log.info("CostoPelear:");
		log.info(this.conf.getCostoPelear());
		Pelear.getInstancia().setCosto(this.conf.getCostoPelear());
		
		log.info("CostoComer:");
		log.info(this.conf.getCostoComer());
		Comer.getInstancia().setCosto(this.conf.getCostoComer());
	
		log.info("Estragegia:");
		log.info(this.conf.getEstrategia());
		Simulador.setEstrategia(this.conf.getEstrategia());
		
		log.info("Heuristica1:");
		log.info(this.conf.getHeuristica1());
		Busqueda.setHeuristica1(this.conf.getHeuristica1());
		
		log.info("Heuristica2:");
		log.info(this.conf.getHeuristica2());
		Busqueda.setHeuristica2(this.conf.getHeuristica2());
		
		log.info("Heuristica3:");
		log.info(this.conf.getHeuristica3());
		Busqueda.setHeuristica3(this.conf.getHeuristica3());
	}

}
