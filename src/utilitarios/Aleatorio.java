package utilitarios;

import java.util.Random;

import entidade.tipo.Forma;
import entidade.tipo.Movimento;
import entidade.tipo.Tipo;

/**
 * @author Emanuel
 *
 */
public class Aleatorio {
	private static Random gerador;

	public Aleatorio() {
		gerador = new Random();
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

	public static Tipo escolherTipo() {
		Forma formaAleatoria = Forma.escolhaAleatoria();
		switch (formaAleatoria) {
		case Coccus:
			return new Tipo(formaAleatoria, Movimento.Deslizamento, true);
		case Bacillus:
			return new Tipo(formaAleatoria, Movimento.Flagelo, false);
		case Spiral:
			return new Tipo(formaAleatoria, Movimento.Contracao, false);
		}
		return null;
	}
}
