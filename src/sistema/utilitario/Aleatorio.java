package sistema.utilitario;

import java.util.Random;

/**
 * @author Emanuel
 *
 */
public class Aleatorio {
	private static Random gerador;

	public Aleatorio() {
		gerador = new Random();
	}

	public static int escolherNum(int max) {
		return escolherNum(max, 0);
	}

	private static int escolherNum(int max, int min) {
		return gerador.nextInt(max - min) + min;
	}

	public static int[] escolherTemps(int max, int min) {
		int[] temps = new int[2];
		while (true) {
			temps[0] = escolherNum(max, min);
			temps[1] = escolherNum(max, min);
			if (temps[0] != temps[1]) {
				if (temps[0] < temps[1]) {
					int aux = temps[0];
					temps[0] = temps[1];
					temps[1] = aux;
					return temps;
				}
				return temps;
			}
		}
	}
}
