package sistema.controlador.jogo;

import java.util.HashMap;

import componente.Componente.Posicao;
import componente.Componente.Velocidade;
import componente.Componente.Velocidade.Direcao;
import componente.Especime.Especie.Movimento;
import sistema.controlador.jogo.ContAuxDaEnt.Entidade;
import sistema.igu.Painel;
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
	private HashMap<Integer, Posicao> entidades;

	/**
	 * Cria o objeto controlador do mapa dado o mapa
	 * 
	 * @param mapa
	 */
	public ContDoMapa(Painel painel, Mapa mapa) {
		this.painel = painel;
		this.mapa = mapa;
		entidades = new HashMap<>();
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
		return novaVelocidade;
	}

	/**
	 * Configura a diferença para a posição nova dado a velocidade e a direção
	 * 
	 * @param direcao
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
		boolean move = false;
		if (conflita(entidade.posicao, diferencial)) {
			move = painel.resolverConflito(entidade, entidadeAlvo);
		}
		move = !colide(entidade.posicao, diferencial);
			
		if(move){
			entidade.posicao.x += diferencial.x;
			entidade.posicao.y += diferencial.y;
			atualizarEntidades(entidade.id, entidade.posicao);
		}
	}

	/**
	 * Verifica se haverá colisão com blocos sólidos do mapa ao se mover
	 * 
	 * @param posicao
	 * @param diferencial
	 * @return boolean
	 */
	private boolean colide(Posicao posicao, Posicao diferencial) {
		int xAux, yAux;
		boolean colidiu = false;
		for (int lado = 0; lado < 4; lado++) {
			xAux = ((posicao.x + diferencial.x) + lado % 2 * 15) / 16;
			yAux = ((posicao.y + diferencial.y) + lado / 2 * 15) / 16;
			if (mapa.obterBloco(xAux, yAux).solido) colidiu = true;
		}
		return colidiu;
	}

	/**
	 * Verifica se houve conflito e guarda qual entidade é alvo
	 * 
	 * @return boolean
	 */
	private boolean conflita(Posicao posicao, Posicao diferencial) {
		Posicao posicaoAux = new Posicao(posicao.x + diferencial.x, posicao.y + diferencial.y);
		for (int id : entidades.keySet()) {
			if (entidades.get(id).equals(posicaoAux)) {
				entidadeAlvo = id;
				return true;
			}
		}
		return false;
	}

	/**
	 * Atualiza as posições das entidades
	 * 
	 * @param id
	 * @param posicao
	 */
	private void atualizarEntidades(int id, Posicao posicao) {
		if (!entidades.isEmpty() && entidades.containsKey(id)) {
			entidades.remove(id);
		}
		entidades.put(id, posicao);
	}
}
