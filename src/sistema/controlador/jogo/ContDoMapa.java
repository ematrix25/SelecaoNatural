package sistema.controlador.jogo;

import java.util.Random;

import componente.Componente.Posicao;
import componente.Componente.Velocidade;
import componente.Componente.Velocidade.Direcao;
import sistema.controlador.jogo.ContAuxDaEnt.Entidade;
import sistema.igu.Painel;
import sistema.igu.renderizador.jogo.base.mapa.Bloco;
import sistema.igu.renderizador.jogo.base.mapa.Mapa;
import sistema.utilitario.Opcoes;

/**
 * Classe que controla os dados do mapa do jogo
 * 
 * @author Emanuel
 */
public class ContDoMapa {
	private Painel painel;
	private Mapa mapa;
	private int entidadeAlvo = -1;

	/**
	 * Cria o objeto controlador do mapa dado o mapa
	 * 
	 * @param painel
	 * @param mapa
	 */
	public ContDoMapa(Painel painel, Mapa mapa) {
		this.painel = painel;
		this.mapa = mapa;
	}

	/**
	 * Obt�m o mapa do Jogo
	 * 
	 * @return Mapa
	 */
	public Mapa obterMapa() {
		return mapa;
	}

	/**
	 * Atualiza os blocos do mapa
	 */
	public void atualizarBlocos() {
		int cor;
		for (int x = 0; x < mapa.largura; x++) {
			for (int y = 0; y < mapa.altura; y++) {
				cor = mapa.obterCorDoBloco(x, y);
				if (cor <= 0xFF00 && cor > 0xFF) {
					if (y > 0) atualizarBloco(x, y - 1, cor);
					if (x > 0) atualizarBloco(x - 1, y, cor);
					if (y < mapa.altura - 1) atualizarBloco(x, y + 1, cor);
					if (x < mapa.largura - 1) atualizarBloco(x + 1, y, cor);
				}
			}
		}
	}

	/**
	 * Atualiza o bloco do mapa
	 * 
	 * @param x
	 * @param y
	 * @param cor
	 */
	private void atualizarBloco(int x, int y, int cor) {
		if (new Random().nextInt(1000) == 1 && mapa.obterCorDoBloco(x, y) <= 0xFF) mapa.configurarCorDoBloco(x, y, cor);
	}

	/**
	 * Obt�m a velocidade m�xima dado o tipo de movimento da entidade
	 * 
	 * @param entidade
	 * @return int
	 */
	public int obterVelocidadeMax(Entidade entidade) {
		Posicao proxPos = entidade.posicao.proxPos;
		int dx, dy, max = 0;
		switch (entidade.especime.especie.tipo.movimento) {
		case Deslizamento:
			max = 1;
		case Contracao:
			max = 2;
		case Flagelo:
			max = 4;
		}
		if (proxPos != null) {
			dx = Math.abs(entidade.posicao.x - proxPos.x);
			dy = Math.abs(entidade.posicao.y - proxPos.y);
			if (dx < max && dy < max) {
				if (dy == 0 || dy > dx) max = dx;
				if (dx == 0 || dx > dy) max = dy;
			}
		}
		return max;
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
		int valor = movimentacao;
		if (valor > 0) {
			novaVelocidade.valor = 1;
			if (direcao != null) novaVelocidade.direcao = direcao;
			for (int i = 0; i < valor; i++) {
				// Deve impedir que se mova quando n�o � mais necess�rio
				if (!mover(entidade, configurarDiferencial(novaVelocidade))) break;
			}
		}
		painel.posicoesDasEnt.put(entidade.id, entidade.posicao);
		novaVelocidade.valor = valor;
		return novaVelocidade;
	}

	/**
	 * Configura a diferen�a para a posi��o nova dado a velocidade e a dire��o
	 * 
	 * @param velocidade
	 * @return Posicao
	 */
	private Posicao configurarDiferencial(Velocidade velocidade) {
		Posicao diferencial = new Posicao();
		if (Opcoes.controlePorMouse || velocidade.direcao == Direcao.Direita) diferencial.x += velocidade.valor;
		else if (velocidade.direcao == Direcao.Esquerda) diferencial.x -= velocidade.valor;
		if (Opcoes.controlePorMouse || velocidade.direcao == Direcao.Baixo) diferencial.y += velocidade.valor;
		else if (velocidade.direcao == Direcao.Cima) diferencial.y -= velocidade.valor;
		if (diferencial.y != 0) diferencial.x = 0;
		return diferencial;
	}

	/**
	 * Tenta mover a entidade m�vel para um nova posi��o
	 * 
	 * @param entidade
	 * @param diferencial
	 * @return boolean
	 */
	private boolean mover(Entidade entidade, Posicao diferencial) {
		Posicao proxPos = entidade.posicao.proxPos;
		Posicao posicaoAux = new Posicao(entidade.posicao.x + diferencial.x, entidade.posicao.y + diferencial.y,
				proxPos);
		boolean move = true;
		if (conflita(entidade.id, posicaoAux)) {
			move &= painel.resolverConflito(entidade, entidadeAlvo);
			entidadeAlvo = -1;
		}
		move &= !colide(posicaoAux);
		if (move) {
			entidade.posicao.x += diferencial.x;
			entidade.posicao.y += diferencial.y;

			// Remove a pr�xima posi��o alcan�ada e parte para a p�xima posi��o
			if (posicaoAux.equals(proxPos)) entidade.posicao.proxPos = proxPos.proxPos;
			
			// Ingere os compostos org�nicos
			comerCompostoOrganico(entidade);
			return true;
		}
		return false;
	}

	/**
	 * Verifica se houve conflito e guarda qual entidade � alvo
	 * 
	 * @param ID
	 * @param posicaoAux
	 * @return boolean
	 */
	private boolean conflita(int ID, Posicao posicaoAux) {
		Posicao posicao;
		for (int id : painel.posicoesDasEnt.keySet()) {
			if (id == ID) continue;
			posicao = painel.posicoesDasEnt.get(id);
			if (Math.abs(posicao.x - posicaoAux.x) < Bloco.TAMANHO
					&& Math.abs(posicao.y - posicaoAux.y) < Bloco.TAMANHO) {
				entidadeAlvo = id;
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica se haver� colis�o com blocos s�lidos do mapa ao se mover
	 * 
	 * @param posicaoAux
	 * @return boolean
	 */
	private boolean colide(Posicao posicaoAux) {
		int xAux, yAux;
		boolean colidiu = false;
		for (int lado = 0; lado < 4; lado++) {
			xAux = (posicaoAux.x + lado % 2 * (Bloco.TAMANHO - 1)) / Bloco.TAMANHO;
			yAux = (posicaoAux.y + lado / 2 * (Bloco.TAMANHO - 1)) / Bloco.TAMANHO;
			if (mapa.obterBloco(xAux, yAux).solido) colidiu = true;
		}
		return colidiu;
	}

	/**
	 * Come os compostos org�nicos caso esteja pr�ximo
	 * 
	 * @param entidade
	 */
	private void comerCompostoOrganico(Entidade entidade) {
		int xAux, yAux, cor, massa;
		for (int lado = 0; lado < 4; lado++) {
			xAux = (entidade.posicao.x + lado % 2 * (Bloco.TAMANHO - 1)) / Bloco.TAMANHO;
			yAux = (entidade.posicao.y + lado / 2 * (Bloco.TAMANHO - 1)) / Bloco.TAMANHO;
			cor = mapa.obterCorDoBloco(xAux, yAux);
			if (cor <= 0xFF00 && cor > 0xFF) {
				mapa.configurarCorDoBloco(xAux, yAux);
				massa = entidade.especime.massa;
				if (massa > 99) entidade.especime.massa = 100;
				else entidade.especime.massa++;
			}
		}
	}
}
