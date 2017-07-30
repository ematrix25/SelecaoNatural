package sistema.controlador.jogo.movimento;

import componente.Componente.Velocidade.Direcao;
import sistema.utilitario.Opcoes;
import sistema.utilitario.periferico.Mouse;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe que controla os dados do Jogador
 * 
 * @author Emanuel
 */
public class ContDoJogador extends ContDaEntMovel {
	private int pontuacao;

	/**
	 * Obt�m a pontuacao do jogo
	 * 
	 * @return int
	 */
	public int obterPontuacao() {
		return pontuacao;
	}

	/**
	 * Configura a pontua��o do jogador
	 * 
	 * @param pontuacao
	 */
	public void configurarPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	/**
	 * Obt�m o movimento requerido pelo jogador dada velocidade m�xima
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
	 * Obt�m a dire��o pela entrada de dados dos perifericos
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterDirecao()
	 */
	public Direcao obterDirecao() {	
		if (Opcoes.controlePorMouse) return obterDirDoMouse();
		else return obterDirDoTeclado();
	}

	/**
	 * Obt�m a dire��o pela entrada de dados do mouse
	 * 
	 * @return Direcao
	 */
	private Direcao obterDirDoMouse() {
		if (Mouse.diferencaY > 0) return Direcao.Cima;
		if (Mouse.diferencaY < 0) return Direcao.Baixo;
		if (Mouse.diferencaX > 0) return Direcao.Direita;
		if (Mouse.diferencaX < 0) return Direcao.Esquerda;;
		return null;
	}

	/**
	 * Obt�m a dire��o pela entrada de dados do teclado
	 * 
	 * @return Direcao
	 */
	private Direcao obterDirDoTeclado() {
		if (Teclado.cima) return Direcao.Cima;
		if (Teclado.baixo) return Direcao.Baixo;
		if (Teclado.direita) return Direcao.Direita;
		if (Teclado.esquerda) return Direcao.Esquerda;
		return null;
	}
}
