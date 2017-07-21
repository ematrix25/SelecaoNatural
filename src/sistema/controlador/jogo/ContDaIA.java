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
	 * Obtem o movimento aleat�rio
	 * 
	 * @param velocidadeMaxima
	 * @return int
	 */
	public int obterMovimentacao(int velocidadeMaxima) {
		return aleatorio.nextInt(velocidadeMaxima + 1);
	}

	/**
	 * Obtem uma dire��o aleat�ria
	 * 
	 * @return int
	 */
	public int obterDirecao() {
		return aleatorio.nextInt(4);
	}
}
