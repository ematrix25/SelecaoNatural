package sistema.controlador.jogo.movimento;

import java.util.Random;

/**
 * Classe que controla os dados da IA
 * 
 * @author Emanuel
 */
public class ContDaIA implements ContDaEntMovel {
	private Random aleatorio;

	/**
	 * Cria o objeto controlador da IA
	 */
	public ContDaIA() {
		aleatorio = new Random();
	}

	/**
	 * Obtem o movimento aleat�rio dado a velocidade m�xima permitida
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterMovimentacao(int)
	 */
	public int obterMovimentacao(int velocidadeMaxima) {
		return aleatorio.nextInt(velocidadeMaxima + 1);
	}

	/**
	 * Obtem uma dire��o aleat�ria
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterDirecao()
	 */
	public int obterDirecao() {
		// Dire��o = 0 (cima) | 1 (direita) | 2 (baixo) | 3 (esquerda)
		return aleatorio.nextInt(4);
	}
}
