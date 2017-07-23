package sistema.controlador.jogo;

import componente.Componente.Posicao;
import componente.Componente.Velocidade;
import componente.Componente.Velocidade.Direcao;
import componente.Especime.Especie.Movimento;
import sistema.controlador.jogo.ContAuxDaEnt.Entidade;
import sistema.igu.renderizador.jogo.base.mapa.Mapa;
import sistema.utilitario.Opcoes;

/**
 * Classe que controla os dados do mapa do jogo
 * 
 * @author Emanuel
 */
public class ContDoMapa {
	private Mapa mapa;

	/**
	 * Cria o objeto controlador do mapa dado o mapa
	 * 
	 * @param mapa
	 */
	public ContDoMapa(Mapa mapa) {
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
	 * Obtem a velocidade m�xima dado o tipo de movimento da entidade
	 * 
	 * @param movimento
	 * @return int
	 */
	public int obterVelocidadeMax(Movimento movimento) {
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
	 * Move a entidade
	 * 
	 * @param movimentacao
	 * @param direcao
	 * @param entidade
	 * @return Velocidade
	 */
	public Velocidade moverEntidade(int movimentacao, Direcao direcao, Entidade entidade) {
		Velocidade novaVelocidade = entidade.velocidade;
		novaVelocidade.valor = movimentacao;
		if (novaVelocidade.valor != 0) {
			novaVelocidade.direcao = direcao;
			mover(entidade.posicao, configurarPosicao(novaVelocidade));
		}
		return novaVelocidade;
	}

	/**
	 * Configura uma posi��o nova dado a velocidade e a direcao
	 * 
	 * @param direcao
	 * @param velocidade
	 * @return Posicao
	 */
	private Posicao configurarPosicao(Velocidade velocidade) {
		Posicao novaPosicao = new Posicao();
		if (Opcoes.controlePorMouse || velocidade.direcao == Direcao.Direita) novaPosicao.x += velocidade.valor;
		else if(velocidade.direcao == Direcao.Esquerda) novaPosicao.x -= velocidade.valor;
		if (Opcoes.controlePorMouse || velocidade.direcao == Direcao.Baixo) novaPosicao.y += velocidade.valor;
		else if(velocidade.direcao == Direcao.Cima) novaPosicao.y -= velocidade.valor;
		if (novaPosicao.y != 0) novaPosicao.x = 0;
		return novaPosicao;
	}

	/**
	 * Tenta mover a entidade m�vel para um nova posi��o
	 * 
	 * @param posicao
	 * @param novaPosicao
	 */
	private void mover(Posicao posicao, Posicao novaPosicao) {
		if (!colide(posicao, novaPosicao) && conflito()) {
			posicao.x += novaPosicao.x;
			posicao.y += novaPosicao.y;
		}
	}

	/**
	 * Verifica se haver� colis�o com blocos s�lidos do mapa ao se mover
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
	 * Verifica se ao se mover o conflito foi resolvido e a entidade ganhou
	 * 
	 * @return boolean
	 */
	private boolean conflito() {
		// TODO Implementar o sistema de conflito entre entidades
		return true;
	}
}