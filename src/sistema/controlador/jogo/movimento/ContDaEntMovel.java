package sistema.controlador.jogo.movimento;

import componente.Componente.Velocidade.Direcao;

/**
 * Classe base para as classes das entidades m�veis
 * 
 * @author Emanuel
 */
public abstract class ContDaEntMovel {
	protected int id;

	/**
	 * Obt�m a ID da entidade do Jogador
	 * 
	 * @return int
	 */
	public int obterID() {
		return id;
	}

	/**
	 * Configura qual entidade est� associado � entidade m�vel
	 * 
	 * @param id
	 */
	public void configurarID(int id) {
		this.id = id;
	}
	
	/**
	 * Obt�m o movimento dado a velocidade m�xima permitida
	 * 
	 * @param velocidadeMaxima
	 * @return int
	 */
	public abstract int obterMovimentacao(int velocidadeMaxima);

	/**
	 * Obt�m a dire��o
	 * 
	 * @return Direcao
	 */
	public abstract Direcao obterDirecao();
}