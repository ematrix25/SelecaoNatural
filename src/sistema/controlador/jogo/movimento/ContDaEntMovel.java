package sistema.controlador.jogo.movimento;

import componente.Componente.Velocidade.Direcao;

/**
 * Interface para as classes das entidades m�veis
 * 
 * @author Emanuel
 */
public interface ContDaEntMovel {
	/**
	 * Obt�m o movimento dado a velocidade m�xima permitida
	 * 
	 * @param velocidadeMaxima
	 * @return int
	 */
	public int obterMovimentacao(int velocidadeMaxima);

	/**
	 * Obt�m a dire��o
	 * 
	 * @return Direcao
	 */
	public Direcao obterDirecao();
}
