package sistema.controlador.jogo;

import java.util.Random;

/**
 * Classe que controla os dados da IA
 * 
 * @author Emanuel
 */
public class ContDaIA {
	private Random aleatorio;

	/**
	 * Cria o objeto controlador da IA
	 */
	public ContDaIA() {
		aleatorio = new Random();
	}

	/**
	 * Obtem o movimento aleatório
	 * 
	 * @param velocidadeMaxima
	 * @return int
	 */
	public int obterMovimentacao(int velocidadeMaxima) {
		return aleatorio.nextInt(velocidadeMaxima + 1);
	}

	/**
	 * Obtem uma direção aleatória
	 * 
	 * @return int
	 */
	public int obterDirecao() {
		return aleatorio.nextInt(4);
	}
}
