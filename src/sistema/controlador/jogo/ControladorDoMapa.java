package sistema.controlador.jogo;

import java.util.Random;

import componente.Componente.Posicao;
import componente.Componente.Velocidade;
import componente.Especime.Especie;
import componente.Especime.Especie.Movimento;
import sistema.controlador.jogo.ControladorDaEntidadeMovel.Entidade;
import sistema.interface_grafica.renderizador.jogo.base.mapa.Mapa;
import sistema.utilitario.Opcoes;
import sistema.utilitario.periferico.Mouse;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe que controla os dados do mapa do jogo
 * 
 * @author Emanuel
 */
public class ControladorDoMapa {
	private Mapa mapa;
	private int jogador;
	private int pontuacao;

	/**
	 * Cria o objeto controlador do mapa dado o mapa
	 * 
	 * @param mapa
	 */
	public ControladorDoMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	/**
	 * Obtem o mapa do Jogo
	 * 
	 * @return Mapa
	 */
	public Mapa obterMapa() {
		return mapa;
	}

	/**
	 * Obtem a ID da especime do Jogador
	 * 
	 * @return int
	 */
	public int obterJogador() {
		return jogador;
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
	 * Configura qual especime está associado ao Jogador
	 * 
	 * @param jogador
	 */
	public void configurarJogador(int jogador) {
		this.jogador = jogador;
	}

	/**
	 * Move a entidade
	 * 
	 * @param ehJogador
	 * @param entidade
	 * @return Velocidade
	 */
	public Velocidade moverEntidade(boolean ehJogador, Entidade entidade) {
		Especie especie = entidade.especime.especie;
		Velocidade novaVelocidade = configurarVelocidade(ehJogador, especie.tipo.movimento, entidade.velocidade);
		if (novaVelocidade.valor != 0) {
			Posicao novaPosicao = configurarPosicao(ehJogador, novaVelocidade);
			if (novaPosicao.y != 0) novaPosicao.x = 0;
			novaVelocidade = mover(entidade.posicao, novaVelocidade, novaPosicao);
		}
		return novaVelocidade;
	}

	/**
	 * Configura a velocidade conforme a entrada dos perifericos ou da ia
	 * 
	 * @param ehJogador
	 * @param movimento
	 * @param velocidade
	 * @return Velocidade
	 */
	private Velocidade configurarVelocidade(boolean ehJogador, Movimento movimento, Velocidade velocidade) {
		Velocidade novaVelocidade = velocidade;
		int velocidadeMaxima = obterVelocidadeMax(movimento);
		// Obtem a velocidade do Jogador
		if (ehJogador) {
			if (Opcoes.controlePorMouse) {
				novaVelocidade.valor = Mouse.obterDiferenca(velocidadeMaxima);
			} else {
				if (Teclado.cima || Teclado.baixo || Teclado.direita || Teclado.esquerda) {
					if (Teclado.correr) novaVelocidade.valor = velocidadeMaxima;
					else novaVelocidade.valor = velocidadeMaxima / 2;
				} else novaVelocidade.valor = 0;
			}
		}
		// Obtem a velocidade aleatória
		else novaVelocidade.valor = new Random().nextInt(velocidadeMaxima + 1);
		return novaVelocidade;
	}

	/**
	 * Obtem a velocidade máxima dado o tipo de movimento da entidade
	 * 
	 * @param movimento
	 * @return int
	 */
	private int obterVelocidadeMax(Movimento movimento) {
		switch (movimento) {
		case Deslizamento:
			return 1;
		case Contracao:
			return 2;
		case Flagelo:
			return 4;
		default:
			return 0;
		}
	}

	/**
	 * Configura uma posição nova dado a velocidade e a posição almejada
	 * 
	 * @param ehJogador
	 * @param velocidade
	 * @return Posicao
	 */
	private Posicao configurarPosicao(boolean ehJogador, Velocidade velocidade) {
		Posicao novaPosicao = new Posicao();
		// Obtem a posição x do Jogador
		if (ehJogador) {
			novaPosicao.x = configurarX(ehJogador, velocidade);
			novaPosicao.y = configurarY(ehJogador, velocidade);
		} else {
			if (new Random().nextBoolean()) novaPosicao.x = configurarX(ehJogador, velocidade);
			else novaPosicao.y = configurarY(ehJogador, velocidade);
		}
		return novaPosicao;
	}

	/**
	 * Configura a posição de X de acordo com a velocidade
	 * 
	 * @param ehJogador
	 * @param velocidade
	 * @return int
	 */
	private int configurarX(boolean ehJogador, Velocidade velocidade) {
		int xAux = 0;

		// Obtem a posição x do Jogador
		if (ehJogador) {
			if (Opcoes.controlePorMouse) xAux += Mouse.diferencaX;
			else {
				if (Teclado.esquerda) xAux -= velocidade.valor;
				if (Teclado.direita) xAux += velocidade.valor;
			}
		}
		// Obtem a posição aleatória de x
		else {
			if (new Random().nextBoolean()) xAux -= velocidade.valor;
			else xAux += velocidade.valor;
		}
		return xAux;
	}

	/**
	 * Configura a posição de Y de acordo com a velocidade
	 * 
	 * @param ehJogador
	 * @param velocidade
	 * @return int
	 */
	private int configurarY(boolean ehJogador, Velocidade velocidade) {
		int yAux = 0;

		// Obtem a posição xy do Jogador
		if (ehJogador) {
			if (Opcoes.controlePorMouse) yAux += Mouse.diferencaY;
			else {
				if (Teclado.cima) yAux -= velocidade.valor;
				if (Teclado.baixo) yAux += velocidade.valor;
			}
		}
		// Obtem a posição aleatória de y
		else {
			if (new Random().nextBoolean()) yAux -= velocidade.valor;
			else yAux += velocidade.valor;
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
		if (novaPosicao.x > 0) novaVelocidade.direcao = 1;
		if (novaPosicao.y > 0) novaVelocidade.direcao = 2;
		if (novaPosicao.x < 0) novaVelocidade.direcao = 3;
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
