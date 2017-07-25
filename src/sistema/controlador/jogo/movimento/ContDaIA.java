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
	 * Obtém o movimento aleatório dado a velocidade máxima permitida
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterMovimentacao(int)
	 */
	public int obterMovimentacao(int velocidadeMaxima) {
		return aleatorio.nextInt(velocidadeMaxima + 1);
	}

	/**
	 * Obtém uma direção aleatória
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterDirecao()
	 */
	public Direcao obterDirecao() {
		return Direcao.escolhaAleatoria();
	}
}
