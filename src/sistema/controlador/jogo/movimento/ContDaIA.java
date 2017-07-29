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
	 * Obt�m o movimento aleat�rio dado a velocidade m�xima permitida
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
	 * Obt�m uma dire��o aleat�ria
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterDirecao()
	 */
	public Direcao obterDirecao() {
		switch (estado) {
		case Vagando:
			return Direcao.escolhaAleatoria();
		case Seguindo:
			// TODO Implementar sistema para reduzir a dist�ncia de outros
			// esp�cimes
			return null;
		case Fugindo:
			// TODO Implementar sistema para aumentar a dist�ncia de outros
			// esp�cimes
			return null;
		}
		return null;
	}

	/**
	 * Muda o estado da ia se necess�rio
	 */
	private void mudarEstado() {
		// TODO Implementar sistema para alternar os estados da ia
	}

	/**
	 * Obt�m um alvo para perseguir ou para fugir
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
