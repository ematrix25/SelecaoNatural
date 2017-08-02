package sistema.controlador.jogo;

import java.util.HashMap;

import componente.Componente.Posicao;
import componente.Componente.Velocidade;
import componente.Componente.Velocidade.Direcao;
import componente.Especime.Especie.Movimento;
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

	public HashMap<Integer, Posicao> entidades;

	/**
	 * Cria o objeto controlador do mapa dado o mapa
	 * 
	 * @param painel
	 * @param mapa
	 */
	public ContDoMapa(Painel painel, Mapa mapa) {
		this.painel = painel;
		this.mapa = mapa;
		entidades = painel.entidades;
	}

	/**
	 * Obtém o mapa do Jogo
	 * 
	 * @return Mapa
	 */
	public Mapa obterMapa() {
		return mapa;
	}

	/**
	 * Obtém a velocidade máxima dado o tipo de movimento da entidade
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
			mover(entidade, configurarDiferencial(novaVelocidade));
		}
		entidades.put(entidade.id, entidade.posicao);
		return novaVelocidade;
	}

	/**
	 * Configura a diferença para a posição nova dado a velocidade e a direção
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
	 * Tenta mover a entidade móvel para um nova posição
	 * 
	 * @param entidade
	 * @param diferencial
	 */
	private void mover(Entidade entidade, Posicao diferencial) {
		boolean move = true;
		if (conflita(entidade, diferencial)) {
			move &= painel.resolverConflito(entidade, entidadeAlvo);
			entidadeAlvo = -1;
		}
		move &= !colide(entidade.posicao, diferencial);

		if (move) {
			entidade.posicao.x += diferencial.x;
			entidade.posicao.y += diferencial.y;
		}
	}

	/**
	 * Verifica se houve conflito e guarda qual entidade é alvo
	 * 
	 * @param entidade
	 * @param diferencial
	 * @return boolean
	 */
	private boolean conflita(Entidade entidade, Posicao diferencial) {
		Posicao posicao = new Posicao(entidade.posicao.x + diferencial.x, entidade.posicao.y + diferencial.y);
		Posicao posicaoAux;
		for (int id : entidades.keySet()) {
			if (id == entidade.id) continue;
			posicaoAux = entidades.get(id);
			if (Math.abs(posicaoAux.x - posicao.x) < Bloco.TAMANHO
					&& Math.abs(posicaoAux.y - posicao.y) < Bloco.TAMANHO) {
				entidadeAlvo = id;
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica se haverá colisão com blocos sólidos do mapa ao se mover
	 * 
	 * @param posicao
	 * @param diferencial
	 * @return boolean
	 */
	private boolean colide(Posicao posicao, Posicao diferencial) {
		Posicao posicaoAux = new Posicao(posicao.x + diferencial.x, posicao.y + diferencial.y);
		int xAux, yAux;
		boolean colidiu = false;
		for (int lado = 0; lado < 4; lado++) {
			xAux = (posicaoAux.x + lado % 2 * (Bloco.TAMANHO - 1)) / Bloco.TAMANHO;
			yAux = (posicaoAux.y + lado / 2 * (Bloco.TAMANHO - 1)) / Bloco.TAMANHO;
			if (mapa.obterBloco(xAux, yAux).solido) colidiu = true;
		}
		return colidiu;
	}
}
