package sistema.controlador.jogo.movimento;

import componente.Componente.Velocidade.Direcao;

/**
 * Interface para as classes das entidades móveis
 * 
 * @author Emanuel
 */
public interface ContDaEntMovel {
	/**
	 * Obtem o movimento dado a velocidade máxima permitida
	 * 
	 * @param velocidadeMaxima
	 * @return int
	 */
	public int obterMovimentacao(int velocidadeMaxima);

	/**
	 * Obtem a direção
	 * 
	 * @return Direcao
	 */
	public Direcao obterDirecao();
}
