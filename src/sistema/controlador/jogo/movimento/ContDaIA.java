package sistema.controlador.jogo.movimento;

import java.util.HashMap;
import java.util.Random;

import componente.Componente.Posicao;
import componente.Componente.Velocidade.Direcao;

/**
 * Classe que controla os dados da IA
 * 
 * @author Emanuel
 */
public class ContDaIA implements ContDaEntMovel {
	private Random aleatorio;
	private Estado estado = Estado.Parado;
	private HashMap<Integer, Posicao> entidades;
	private final int ALCANCE = 150;

	/**
	 * Cria o objeto controlador da IA
	 * 
	 * @param entidades
	 */
	public ContDaIA(HashMap<Integer, Posicao> entidades) {
		aleatorio = new Random();
		this.entidades = entidades;
	}

	/**
	 * Obtém o movimento aleatório dado a velocidade máxima permitida
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterMovimentacao(int)
	 */
	public int obterMovimentacao(int velocidadeMaxima) {
		mudarEstado();
		switch (estado) {
		case Parado:
			return 0;
		case Vagando:
			return 1 + aleatorio.nextInt(velocidadeMaxima);
		}
		return velocidadeMaxima;
	}

	/**
	 * Obtém uma direção aleatória
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterDirecao()
	 */
	public Direcao obterDirecao() {
		switch (estado) {
		case Vagando:
			return Direcao.escolhaAleatoria();
		case Seguindo:
			// TODO Implementar sistema para reduzir a distância de outros
			// espécimes
			return null;
		case Fugindo:
			// TODO Implementar sistema para aumentar a distância de outros
			// espécimes
			return null;
		}
		return null;
	}

	/**
	 * Muda o estado da ia se necessário
	 */
	private void mudarEstado() {
		// TODO Implementar sistema para alternar os estados da ia
	}

	/**
	 * Obtém um alvo para perseguir ou para fugir
	 */
	private int buscarAlvo() {
		// TODO Implementar sistema para buscar alvo
		return -1;
	}

	/**
	 * Define os estados em que a ia pode estar
	 * 
	 * @author Emanuel
	 */
	private enum Estado {
		Parado, Vagando, Seguindo, Fugindo;
	}
}
