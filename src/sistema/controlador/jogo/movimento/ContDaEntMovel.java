package sistema.controlador.jogo.movimento;

/**
 * Interface para as classes das entidades m�veis
 * 
 * @author Emanuel
 */
public interface ContDaEntMovel {
	/**
	 * Obtem o movimento dado a velocidade m�xima permitida
	 * 
	 * @param velocidadeMaxima
	 * @return int
	 */
	public int obterMovimentacao(int velocidadeMaxima);

	/**
	 * Obtem a dire��o
	 * 
	 * @return int
	 */
	public int obterDirecao();
}
