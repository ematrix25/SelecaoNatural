package sistema.controlador.jogo.movimento;

import java.util.Random;

import componente.Componente.Velocidade.Direcao;

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
	 * Obt�m o movimento aleat�rio dado a velocidade m�xima permitida
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterMovimentacao(int)
	 */
	public int obterMovimentacao(int velocidadeMaxima) {
		return aleatorio.nextInt(velocidadeMaxima + 1);
	}

	/**
	 * Obt�m uma dire��o aleat�ria
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterDirecao()
	 */
	public Direcao obterDirecao() {
		return Direcao.escolhaAleatoria();
	}
}
