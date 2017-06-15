package sistema.controlador;

import componente.Componente.Posicao;
import componente.Componente.Velocidade;
import sistema.interface_grafica.renderizador.base_do_jogo.mapa.Mapa;
import sistema.utilitario.Opcoes;
import sistema.utilitario.periferico.Mouse;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe que controla os dados do jogo
 * 
 * @author Emanuel
 */
public class ControladorDoJogo {
	// TODO Testar o controlador do jogo
	private Mapa mapa;

	/**
	 * Cria o objeto controlador do jogo dado o mapa
	 * 
	 * @param mapa
	 */
	public ControladorDoJogo(Mapa mapa) {
		this.mapa = mapa;
	}

	/**
	 * Move o jogador
	 * 
	 * @param posicao
	 * @param velocidade
	 * @param novaPosicao
	 * @return Velocidade
	 */
	public Velocidade moverJogador(Posicao posicao, Velocidade velocidade) {
		Velocidade novaVelocidade = configurarVelocidade(velocidade);
		Posicao novaPosicao = configurarPosicao(novaVelocidade);
		if (novaPosicao.y == 0 && novaPosicao.x == 0) novaVelocidade.movendo = false;
		else {
			if (novaPosicao.y != 0 && novaPosicao.x != 0) novaPosicao.x = 0;
			novaVelocidade.movendo = true;
			novaVelocidade = mover(posicao, novaVelocidade, novaPosicao);
		}
		return novaVelocidade;
	}

	/**
	 * Configura a velocidade conforme a entrada dos perifericos
	 * 
	 * @param velocidade
	 * @return Velocidade
	 */
	private Velocidade configurarVelocidade(Velocidade velocidade) {
		Velocidade novaVelocidade = velocidade;
		if (Opcoes.controlePorMouse) {
			// Sujeito a modificações
			int desvioX = Mouse.obterX() / (Mouse.xMaximo / 4 - 5);
			int desvioY = Mouse.obterY() / (Mouse.yMaximo / 4 - 5);
			novaVelocidade.valor = (desvioX > desvioY) ? desvioX : desvioY;
		} else {
			if (Teclado.correr) novaVelocidade.valor = 4;
			else novaVelocidade.valor = 2;
		}
		return novaVelocidade;
	}

	/**
	 * Configura uma posição nova dado a velocidade e a posição almejada
	 * 
	 * @param velocidade
	 * @return Posicao
	 */
	private Posicao configurarPosicao(Velocidade velocidade) {
		Posicao novaPosicao = new Posicao();
		novaPosicao.x = configurarX(velocidade);
		novaPosicao.y = configurarY(velocidade);
		return novaPosicao;
	}

	/**
	 * Configura a posição de X de acordo com a velocidade
	 * 
	 * @param velocidade
	 * @return int
	 */
	private int configurarX(Velocidade velocidade) {
		int xAux = 0;
		if (Opcoes.controlePorMouse) xAux += velocidade.valor;
		else {
			if (Teclado.esquerda) xAux -= velocidade.valor;
			if (Teclado.direita) xAux += velocidade.valor;
		}
		return xAux;
	}

	/**
	 * Configura a posição de Y de acordo com a velocidade
	 * 
	 * @param velocidade
	 * @return int
	 */
	private int configurarY(Velocidade velocidade) {
		int yAux = 0;
		if (Opcoes.controlePorMouse) yAux += velocidade.valor;
		else {
			if (Teclado.cima) yAux -= velocidade.valor;
			if (Teclado.baixo) yAux += velocidade.valor;
		}
		return yAux;
	}

	/**
	 * Tenta mover a entidade móvel para um nova posição
	 * 
	 * @param posicao
	 * @param velocidade
	 * @param novaPosicao
	 * @return Velocidade
	 */
	private Velocidade mover(Posicao posicao, Velocidade velocidade, Posicao novaPosicao) {
		Velocidade novaVelocidade = velocidade;
		// Direção = 0 (cima) | 1 (direita) | 2 (baixo) | 3 (esquerda)
		if (novaPosicao.y < 0) novaVelocidade.direcao = 0;
		if (novaPosicao.x < 0) novaVelocidade.direcao = 1;
		if (novaPosicao.y > 0) novaVelocidade.direcao = 2;
		if (novaPosicao.x > 0) novaVelocidade.direcao = 3;
		if (!colide(posicao, novaPosicao)) {
			posicao.x += novaPosicao.x;
			posicao.y += novaPosicao.y;
		}
		return novaVelocidade;
	}

	/**
	 * Verifica se haverá colisão ao se mover
	 * 
	 * @param posicao
	 * @param novaPosicao
	 * @return boolean
	 */
	private boolean colide(Posicao posicao, Posicao novaPosicao) {
		int xAux, yAux;
		boolean colidiu = false;
		for (int lado = 0; lado < 4; lado++) {
			xAux = ((posicao.x + novaPosicao.x) + lado % 2 * 15) / 16;
			yAux = ((posicao.y + novaPosicao.y) + lado / 2 * 15) / 16;
			if (mapa.obterBloco(xAux, yAux).solido) colidiu = true;
		}
		return colidiu;
	}
}
