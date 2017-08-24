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

	public HashMap<Integer, Posicao> posicoesDasEnt;

	/**
	 * Cria o objeto controlador do mapa dado o mapa
	 * 
	 * @param painel
	 * @param mapa
	 */
	public ContDoMapa(Painel painel, Mapa mapa) {
		this.painel = painel;
		this.mapa = mapa;
		posicoesDasEnt = painel.posicoesDasEnt;
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
		int valor = movimentacao;
		if (valor > 0) {
			novaVelocidade.valor = 1;
			if (direcao != null) novaVelocidade.direcao = direcao;
			for (int i = 0; i < valor; i++) {
				// Deve impedir que se mova quando não é mais necessário
				if (!mover(entidade, configurarDiferencial(novaVelocidade))) break;
			}
		}
		posicoesDasEnt.put(entidade.id, entidade.posicao);
		novaVelocidade.valor = valor;
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
	 * @return boolean
	 */
	private boolean mover(Entidade entidade, Posicao diferencial) {
		Posicao proxPos = entidade.posicao.proxPos;
		Posicao posicaoAux = new Posicao(entidade.posicao.x + diferencial.x, entidade.posicao.y + diferencial.y,
				proxPos);
		boolean move = true;
		int dx, dy, max;
		if (conflita(entidade.id, posicaoAux)) {
			move &= painel.resolverConflito(entidade, entidadeAlvo);
			entidadeAlvo = -1;
		}
		move &= !colide(posicaoAux);
		if (move) {
			entidade.posicao.x += diferencial.x;
			entidade.posicao.y += diferencial.y;

			// Remove a próxima posição alcançada e parte para a póxima posição
			if (proxPos != null) {
				dx = Math.abs(posicaoAux.x - proxPos.x);
				dy = Math.abs(posicaoAux.y - proxPos.y);
				max = obterVelocidadeMax(entidade.especime.especie.tipo.movimento);
				if (dx < max && dy < max) 
					entidade.posicao.proxPos = entidade.posicao.proxPos.proxPos;
			}
			return true;
		}
		return false;
	}

	/**
	 * Verifica se houve conflito e guarda qual entidade é alvo
	 * 
	 * @param ID
	 * @param posicaoAux
	 * @return boolean
	 */
	private boolean conflita(int ID, Posicao posicaoAux) {
		Posicao posicao;
		for (int id : posicoesDasEnt.keySet()) {
			if (id == ID) continue;
			posicao = posicoesDasEnt.get(id);
			if (Math.abs(posicao.x - posicaoAux.x) < Bloco.TAMANHO
					&& Math.abs(posicao.y - posicaoAux.y) < Bloco.TAMANHO) {
				entidadeAlvo = id;
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica se haverá colisão com blocos sólidos do mapa ao se mover
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
}
