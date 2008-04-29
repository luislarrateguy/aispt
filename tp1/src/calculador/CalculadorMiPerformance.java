package calculador;
import java.lang.Math;
import java.util.Vector;


public class CalculadorMiPerformance extends Calculador {

	@Override
	public int getPerformance(){
		double p = -1;

		// Esta condición evita que se llame al método getPerformance() más de una vez.-
		if (!yaObtuvoPerformance){
			yaObtuvoPerformance = true;
			if (energiaPacman <= 0 )
				p = 0;
			else {
//				p = (contEnemigosMuertos / cantEnemigos) * 30 +
//				    (contComidaComida / cantComida) * 30 +
//				    (contEnemigosMuertos + contComidaComida) / (cantEnemigos + cantComida) * 40 +
//                    ((cantMovimientos - contMovimientos) / cantMovimientos) * 30 +
//                    (energiaPacman / energiaPacmanOriginal) * 70;
				p = (contEnemigos +1.0) / (cantEnemigos + 1.0) * 40.0 +
			    	(contComida +1.0) / (cantComida + 1.0) * 40.0;
				
				double pond = 0.0;
				
				if (contMovimientos <= 5 )
					pond = (1.0 / contMovimientos) * 20;
				else if (contMovimientos <= 10)
					pond = (6.0 / contMovimientos) * 10;
				else if (contMovimientos <= 16)
					pond = (5.0 / contMovimientos) * 10;
				else if (contMovimientos <= 19)
					pond = (2.0 / contMovimientos) * 10;
				else 
					pond = 0 - p * 0.1;
					
//			    	energiaPacman / (energiaPacmanOriginal * 1.0) * 25.0 +
				// p = p / (contMovimientos *1.0 / cantMovimientos * 1.0);
				p = p + pond;
			    	//cantMovimientos / (contMovimientos  * 1.0);
//			System.out.print(contEnemigosMuertos+" ");
//			System.out.print(cantEnemigos+", ");
//			
//			System.out.print(contComidaComida+" ");
//			System.out.print(cantComida+", ");
//			
//			System.out.print(contMovimientos+" ");
//			System.out.println(cantMovimientos);
//			
			}
		}
		return (int) p;
	}

}
