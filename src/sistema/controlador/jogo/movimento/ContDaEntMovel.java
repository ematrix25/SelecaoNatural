package sistema.controlador.jogo.movimento;

import componente.Componente.Velocidade.Direcao;

/**
 * Classe base para as classes das entidades móveis
 * 
 * @author Emanuel
 */
public abstract class ContDaEntMovel {
	protected int id;

	/**
	 * Obtém a ID da entidade do Jogador
	 * 
	 * @return int
	 */
	public int obterID() {
		return id;
	}

	/**
	 * Configura qual entidade está associado à entidade móvel
	 * 
	 * @param id
	 */
	public void configurarID(int id) {
		this.id = id;
	}
	
	/**
	 * Obtém o movimento dado a velocidade máxima permitida
	 * 
	 * @param velocidadeMaxima
	 * @return int
	 */
	public abstract int obterMovimentacao(int velocidadeMaxima);

	/**
	 * Obtém a direção
	 * 
	 * @return Direcao
	 */
	public abstract Direcao obterDirecao();
}