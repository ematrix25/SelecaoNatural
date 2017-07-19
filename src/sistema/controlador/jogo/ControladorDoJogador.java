package sistema.controlador.jogo;

/**
 * Classe que controla os dados do Jogador
 * 
 * @author Emanuel
 */
public class ControladorDoJogador {
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
}
