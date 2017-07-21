package sistema.controlador.jogo.movimento;

import sistema.utilitario.Opcoes;
import sistema.utilitario.periferico.Mouse;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe que controla os dados do Jogador
 * 
 * @author Emanuel
 */
public class ContDoJogador implements ContDaEntMovel {
	private int id;
	private int pontuacao;

	/**
	 * Obtem a ID da entidade do Jogador
	 * 
	 * @return int
	 */
	public int obterID() {
		return id;
	}

	/**
	 * Configura qual entidade está associado ao Jogador
	 * 
	 * @param id
	 */
	public void configurarID(int id) {
		this.id = id;
	}

	/**
	 * Obtem a pontuacao do jogo
	 * 
	 * @return int
	 */
	public int obterPontuacao() {
		return pontuacao;
	}

	/**
	 * Configura a pontuação do jogador
	 * 
	 * @param pontuacao
	 */
	public void configurarPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	/**
	 * Obtem o movimento requerido pelo jogador dada velocidade máxima
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterMovimentacao(int)
	 */
	public int obterMovimentacao(int velocidadeMaxima) {
		if (Opcoes.controlePorMouse) {
			return Mouse.obterDiferenca(velocidadeMaxima);
		} else {
			if (Teclado.cima || Teclado.baixo || Teclado.direita || Teclado.esquerda) {
				if (Teclado.correr) return velocidadeMaxima;
				else return velocidadeMaxima / 2;
			} else return 0;
		}
	}

	/**
	 * Obtem a direção pela entrada de dados dos perifericos
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterDirecao()
	 */
	public int obterDirecao() {
		if (Opcoes.controlePorMouse) return obterDirDoMouse();
		else return obterDirDoTeclado();
	}

	/**
	 * Obtem a direção pela entrada de dados do mouse
	 * 
	 * @return int
	 */
	private int obterDirDoMouse() {
		if (Mouse.diferencaY > 0) return 0;
		if (Mouse.diferencaY < 0) return 2;
		if (Mouse.diferencaX > 0) return 1;
		if (Mouse.diferencaX < 0) return 3;
		return -1;
	}

	/**
	 * Obtem a direção pela entrada de dados do teclado
	 * 
	 * @return int
	 */
	private int obterDirDoTeclado() {
		if (Teclado.cima) return 0;
		if (Teclado.baixo) return 2;
		if (Teclado.direita) return 1;
		if (Teclado.esquerda) return 3;
		return -1;
	}
}
