package sistema.controlador.jogo.movimento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import componente.Componente.Posicao;
import componente.Componente.Velocidade.Direcao;
import sistema.controlador.jogo.ContAuxDaEnt.Entidade;
import sistema.igu.Painel;
import sistema.igu.renderizador.jogo.base.mapa.Bloco;
import sistema.igu.renderizador.jogo.base.mapa.Coordenada;
import sistema.igu.renderizador.jogo.base.mapa.Mapa;

/**
 * Classe que controla os dados da IA
 * 
 * @author Emanuel
 */
public class ContDaIA extends ContDaEntMovel {
	private Random aleatorio;
	private Estado estado;
	private GestorDeCaminho gestorDeCaminho;
	private Posicao proxPos;
	private Direcao direcao;

	private HashMap<Integer, Posicao> posicoesDasEnt;

	private final int ALCANCE = 150;

	private int idAlvo;

	/**
	 * Cria o objeto controlador da IA
	 * 
	 * @param painel
	 * @param mapa
	 */
	public ContDaIA(Painel painel, Mapa mapa) {
		aleatorio = new Random();
		gestorDeCaminho = new GestorDeCaminho(mapa);
		posicoesDasEnt = painel.posicoesDasEnt;
		idAlvo = -1;
	}

	/**
	 * Configura o controlador da IA conforme a id
	 * 
	 * @param entidade
	 */
	public void configurarIA(Entidade entidade) {
		configurarID(entidade.id);
		if (posicoesDasEnt.containsKey(entidade.id)) {
			direcao = entidade.velocidade.direcao;
			configEstado();
			if (estado != Estado.Parado) configProxPos();
		}
	}

	/**
	 * Configura o estado da IA se necessário
	 */
	private void configEstado() {
		buscarAlvo();
		Posicao posicao = posicoesDasEnt.get(id), posicaoAlvo;
		int dx, dy;
		if (posicao.proxPos == null) {
			if (aleatorio.nextBoolean()) estado = Estado.Vagando;
			else estado = Estado.Parado;
		} else {
			if (idAlvo == -1) estado = Estado.Vagando;
			else {
				posicaoAlvo = posicoesDasEnt.get(idAlvo);
				dx = posicao.x - posicaoAlvo.x;
				dy = posicao.y - posicaoAlvo.y;
				switch (direcao) {
				case Cima:
					if (dy > 0) estado = Estado.Seguindo;
					else estado = Estado.Fugindo;
					break;
				case Baixo:
					if (dy > 0) estado = Estado.Fugindo;
					else estado = Estado.Seguindo;
					break;
				case Direita:
					if (dx > 0) estado = Estado.Seguindo;
					else estado = Estado.Fugindo;
					break;
				case Esquerda:
					if (dx > 0) estado = Estado.Fugindo;
					else estado = Estado.Seguindo;
					break;
				}
			}
		}
	}

	/**
	 * Obtém a id do alvo para perseguir ou para fugir
	 */
	private void buscarAlvo() {
		Posicao posicao = posicoesDasEnt.get(id), posicaoAux;
		int dx, dy;
		double distancia, distanciaAux;
		if (idAlvo != -1) {
			posicaoAux = posicoesDasEnt.get(idAlvo);
			distancia = gestorDeCaminho.obterDistancia(posicao, posicaoAux);
		} else distancia = ALCANCE * 2;
		idAlvo = -1;
		for (int idAux : posicoesDasEnt.keySet()) {
			if (id == idAux) continue;
			posicaoAux = posicoesDasEnt.get(idAux);
			dx = Math.abs(posicaoAux.x - posicao.x);
			dy = Math.abs(posicaoAux.y - posicao.y);
			if (dx < ALCANCE && dy < ALCANCE) {
				distanciaAux = gestorDeCaminho.obterDistancia(posicao, posicaoAux);
				if (distanciaAux < distancia) {
					distancia = distanciaAux;
					idAlvo = idAux;
				}
			}
		}
	}

	/**
	 * Configura as próximas posições
	 */
	private void configProxPos() {
		Posicao posicao = posicoesDasEnt.get(id), posicaoAlvo;
		if (idAlvo == -1) proxPos = gestorDeCaminho.obterCaminho(posicao, ALCANCE);
		else {
			posicaoAlvo = posicoesDasEnt.get(idAlvo);
			proxPos = gestorDeCaminho.obterCaminho(posicao, posicaoAlvo);
		}
	}

	/**
	 * Obtém a próxima posição
	 * 
	 * @return Posicao
	 */
	public Posicao obterProxPos() {
		return proxPos;
	}

	/**
	 * Obtém o movimento aleatório dado a velocidade máxima permitida
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterMovimentacao(int)
	 */
	public int obterMovimentacao(int velocidadeMaxima) {
		switch (estado) {
		case Parado:
			return 0;
		case Vagando:
			return 1 + aleatorio.nextInt(velocidadeMaxima);
		default:
			return velocidadeMaxima;
		}
	}

	/**
	 * Obtém uma direção aleatória
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterDirecao()
	 */
	public Direcao obterDirecao() {
		switch (estado) {
		case Vagando:
			return obterDirDaDistancia(false);
		case Seguindo:
			return obterDirDaDistancia(false);
		case Fugindo:
			return obterDirDaDistancia(true);
		default:
			return null;
		}
	}

	/**
	 * Obtém a direção segundo a diferença entre as entidades
	 * 
	 * @param aumenta
	 * @return Direcao
	 */
	private Direcao obterDirDaDistancia(boolean aumenta) {
		Posicao posicao = posicoesDasEnt.get(id), posDestino = posicao.proxPos;
		int dx = posDestino.x - posicao.x;
		int dy = posDestino.y - posicao.y;
		if (Math.abs(dx) > Math.abs(dy)) {
			if ((!aumenta && dx > 0) || (aumenta && dx < 0)) return Direcao.Direita;
			else return Direcao.Esquerda;
		} else {
			if ((!aumenta && dy > 0) || (aumenta && dy < 0)) return Direcao.Baixo;
			else return Direcao.Cima;
		}
	}

	/**
	 * Define os estados em que a ia pode estar
	 * 
	 * @author Emanuel
	 */
	private enum Estado {
		Parado, Vagando, Seguindo, Fugindo;
	}

	/**
	 * Classe para designar o melhor caminho para a IA
	 * 
	 * @author Emanuel
	 */
	public class GestorDeCaminho {
		private Mapa mapa;
		private Comparator<No> ordenadorDeNos;

		/**
		 * Cria o objeto gerenciador de caminhos
		 * 
		 * @param mapa
		 */
		public GestorDeCaminho(Mapa mapa) {
			this.mapa = mapa;
			this.ordenadorDeNos = new Comparador();
		}

		/**
		 * Obtém o melhor caminho até uma posição dentro do alcance
		 * 
		 * @param posicao
		 * @param alcance
		 * @return Posicao
		 */
		public Posicao obterCaminho(Posicao posicao, int alcance) {
			Posicao posicaoAlvo = null, posicaoAux = new Posicao();
			// XXX Talvez precise ser melhorado
			while (posicaoAlvo != null) {
				for (int x = alcance / 2; x < alcance; x += 16) {
					posicaoAux.x = posicao.x + x;
					for (int y = alcance / 2; y < alcance; y += 16) {
						posicaoAux.y = posicao.y + y;
					}
				}
				int x = posicaoAux.x / Bloco.TAMANHO;
				int y = posicaoAux.y / Bloco.TAMANHO;
				if (!mapa.obterBloco(x, y).solido) posicaoAlvo = new Posicao(posicaoAux);
			}
			return obterCaminho(posicao, posicaoAlvo);
		}

		/**
		 * Obtém o melhor caminho de uma posicao para outra
		 * 
		 * @param posicao
		 * @param posicaoAlvo
		 * @return Posicao
		 */
		public Posicao obterCaminho(Posicao posicao, Posicao posicaoAlvo) {
			List<No> nos = new ArrayList<No>();
			List<No> nosAvaliados = new ArrayList<No>();
			No noAtual = new No(posicao, null, 0, obterDistancia(posicao, posicaoAlvo)), noAux;
			Bloco bloco;
			Posicao posicaoAux;
			int x, y;
			double custoG, custoH;
			nos.add(noAtual);
			while (!nos.isEmpty()) {
				Collections.sort(nos, ordenadorDeNos);
				noAtual = nos.get(0);
				if (noAtual.posicao.equals(posicaoAlvo)) {
					while (noAtual.pai != null) {
						noAtual = noAtual.pai;
					}
					nos.clear();
					nosAvaliados.clear();
					// Precisa ser verificado
					return noAtual.posicao.proxPos;
				}
				nos.remove(noAtual);
				nosAvaliados.add(noAtual);
				for (int i = 0; i < 9; i++) {
					if (i == 4) continue;
					x = (noAtual.posicao.x / Bloco.TAMANHO) + (i % 3) - 1;
					y = (noAtual.posicao.y / Bloco.TAMANHO) + (i / 3) - 1;
					bloco = mapa.obterBloco(x, y);
					if (bloco == null || bloco.solido) continue;
					posicaoAux = new Posicao(new Coordenada(x, y));
					custoG = noAtual.custoG + obterDistancia(noAtual.posicao, posicaoAux);
					custoH = obterDistancia(posicaoAux, posicaoAlvo);
					noAux = new No(posicaoAux, noAtual, custoG, custoH);
					// Precisa ser verificado
					if (nosAvaliados.contains(noAux) && custoG >= noAux.custoG) continue;
					if (!nos.contains(noAux) || custoG < noAux.custoG) nos.add(noAux);
				}
			}
			nosAvaliados.clear();
			return null;
		}

		/**
		 * Obtém a distância dado duas medidas de x e y
		 * 
		 * @param posicao
		 * @param posicaoAlvo
		 * @return double
		 */
		private double obterDistancia(Posicao posicao, Posicao posicaoAlvo) {
			int dx = Math.abs(posicaoAlvo.x - posicao.x);
			int dy = Math.abs(posicaoAlvo.y - posicao.y);
			return Math.sqrt((dx * dx) + (dy * dy));
		}

		/**
		 * Classe que compara os nós
		 * 
		 * @author Emanuel
		 */
		private class Comparador implements Comparator<No> {

			/**
			 * Compara dois nós
			 * 
			 * @param no
			 * @param noAux
			 * @return int
			 */
			@Override
			public int compare(No no, No noAux) {
				if (noAux.custoF < no.custoF) return 1;
				if (noAux.custoF > no.custoF) return -1;
				return 0;
			}
		}

		/**
		 * Classe do nó da posição que é parte do caminho
		 * 
		 * @author Emanuel
		 */
		public class No {
			private Posicao posicao;
			private No pai;
			private double custoF, custoG;

			/**
			 * Cria o objeto Nó
			 * 
			 * @param posicao
			 * @param pai
			 * @param custoG
			 * @param custoH
			 */
			public No(Posicao posicao, No pai, double custoG, double custoH) {
				if (pai != null) pai.posicao.proxPos = posicao;
				this.posicao = posicao;
				this.pai = pai;
				this.custoG = custoG;
				this.custoF = custoG + custoH;
			}

			/**
			 * Obtém o código
			 * 
			 * @see java.lang.Object#hashCode()
			 */
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((posicao == null) ? 0 : posicao.hashCode());
				return result;
			}

			/**
			 * Verifica a igualdade dos Nós
			 * 
			 * @see java.lang.Object#equals(java.lang.Object)
			 */
			@Override
			public boolean equals(Object obj) {
				if (this == obj) {
					return true;
				}
				if (obj == null) {
					return false;
				}
				if (!(obj instanceof No)) {
					return false;
				}
				No other = (No) obj;
				if (posicao == null) {
					if (other.posicao != null) {
						return false;
					}
				} else if (!posicao.equals(other.posicao)) {
					return false;
				}
				return true;
			}
		}
	}
}
