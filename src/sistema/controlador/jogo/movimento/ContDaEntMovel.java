package sistema.controlador.jogo.movimento;

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
	 * @return int
	 */
	public int obterDirecao();
}
