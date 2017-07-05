package sistema.controlador;

import componente.Componente;
import componente.Componente.Posicao;
import componente.Componente.Sprites;
import componente.Componente.Velocidade;
import sistema.interface_grafica.renderizador.base_do_jogo.Sprite;
import sistema.interface_grafica.renderizador.base_do_jogo.mapa.Coordenada;
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
	private Mapa mapa;
	private int jogador;
	private int pontuacao;

	/**
	 * Cria o objeto controlador do jogo dado o mapa
	 * 
	 * @param mapa
	 */
	public ControladorDoJogo(Mapa mapa) {
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
	 * Move o jogador
	 * 
	 * @param posicao
	 * @param velocidade
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
		int velocidadeMaxima = 4;
		if (Opcoes.controlePorMouse) {
			novaVelocidade.valor = Mouse.obterMaiorDiferenca(velocidadeMaxima);
		} else {
			if (Teclado.correr) novaVelocidade.valor = velocidadeMaxima;
			else novaVelocidade.valor = velocidadeMaxima / 2;
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
		if (Opcoes.controlePorMouse) xAux += Mouse.diferencaX;
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
		if (Opcoes.controlePorMouse) yAux += Mouse.diferencaY;
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

	/**
	 * Método principal para testar o Controlador do Jogo
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ControladorDaEntidade controladorDaEntidade = new ControladorDaEntidade();
		Mapa mapa = new Mapa(0, "/mapas/caverna.png");
		ControladorDoJogo controladorDoJogo = new ControladorDoJogo(mapa);

		// Gera a entidade na coordenada referente ao mapa
		int entidade = controladorDaEntidade.criarEntidade();
		Coordenada coordenada = new Coordenada(mapa, 8, 7);
		controladorDaEntidade.adicionarComponente(entidade, (Componente) new Posicao(coordenada));
		controladorDaEntidade.adicionarComponente(entidade, (Componente) new Velocidade());
		controladorDaEntidade.adicionarComponente(entidade, (Componente) new Sprites(Sprite.coccus));

		// Obtem os dados iniciais
		Posicao posicao = controladorDaEntidade.obterComponente(entidade, Posicao.class);
		Velocidade velocidade = controladorDaEntidade.obterComponente(entidade, Velocidade.class);
		Sprites sprites = controladorDaEntidade.obterComponente(entidade, Sprites.class);
		System.out.println(posicao + "\n" + velocidade + "\n" + sprites.obterSpriteY(velocidade.movendo));
		System.out.println("Mapa " + mapa.obterBloco(posicao.x, posicao.y).sprite + "\n");

		// Move a entidade
		int cont = 40;
		Opcoes.controlePorMouse = false;
		while (cont > 0) {
			Teclado.atualizar();
			Teclado.direita = true;
			// if (cont % 3 == 0) Teclado.baixo = true;
			controladorDoJogo.moverJogador(posicao, velocidade);
			posicao = controladorDaEntidade.obterComponente(entidade, Posicao.class);
			velocidade = controladorDaEntidade.obterComponente(entidade, Velocidade.class);
			System.out.println(posicao + "\n" + velocidade);
			if (velocidade.direcao == 0 || velocidade.direcao == 2)
				System.out.println(sprites.obterSpriteY(velocidade.movendo));
			else System.out.println(sprites.obterSpriteX(velocidade.movendo));
			System.out.println("Mapa " + mapa.obterBloco(posicao.x, posicao.y).sprite + "\n");
			cont--;
		}
	}
}
