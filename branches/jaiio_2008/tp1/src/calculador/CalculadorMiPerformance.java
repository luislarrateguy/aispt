package calculador;
import java.lang.Math;
import java.util.Vector;


public class CalculadorMiPerformance extends Calculador {

	public int getPerformance(){
		double p = -1;
		if ( contComidaComida == 0) 
			contComidaComida = 1;
		if ( contEnemigosMuertos == 0) 
			contEnemigosMuertos = 1;
		// Esta condición evita que se llame al método getPerformance() más de una vez.-
		if (!yaObtuvoPerformance){
			yaObtuvoPerformance = true;
			if (energiaPacman == 0 )
				p = 0;
			else
//				p = (contEnemigosMuertos / cantEnemigos) * 30 +
//				    (contComidaComida / cantComida) * 30 +
//				    (contEnemigosMuertos + contComidaComida) / (cantEnemigos + cantComida) * 40 +
//                    ((cantMovimientos - contMovimientos) / cantMovimientos) * 30 +
//                    (energiaPacman / energiaPacmanOriginal) * 70;
				p = (contEnemigosMuertos / cantEnemigos * 1.0) * 45.0 +
			    (contComidaComida / cantComida* 1.0) * 30.0 +
                (energiaPacman / energiaPacmanOriginal* 1.0) * 25.0 + 
                ((cantMovimientos - contMovimientos) / cantMovimientos* 1.0) * 20.0;
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
		return (int) p;
	}

}
