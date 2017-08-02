package sistema.controlador.jogo.movimento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import componente.Componente.Posicao;
import componente.Componente.Velocidade.Direcao;
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
	private Estado estado = Estado.Parado;
	private GestorDeCaminho gestorDeCaminho;

	private HashMap<Integer, Posicao> entidades;

	private final int ALCANCE = 150;
	private int idAlvo = -1;

	/**
	 * Cria o objeto controlador da IA
	 * 
	 * @param painel
	 * @param mapa
	 */
	public ContDaIA(Painel painel, Mapa mapa) {
		aleatorio = new Random();
		gestorDeCaminho = new GestorDeCaminho(mapa);
		this.entidades = painel.entidades;
	}

	/**
	 * Obtém o movimento aleatório dado a velocidade máxima permitida
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterMovimentacao(int)
	 */
	public int obterMovimentacao(int velocidadeMaxima) {
		mudarEstado();
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
	 * Muda o estado da ia se necessário
	 */
	private void mudarEstado() {
		if (entidades.containsKey(id)) buscarAlvo();
		boolean ativo = aleatorio.nextBoolean();
		if (estado == Estado.Seguindo || estado == Estado.Fugindo) {
			if (idAlvo == -1) {
				if (ativo) estado = Estado.Vagando;
				else estado = Estado.Parado;
			}
		} else {
			if (ativo) {
				if (idAlvo != -1) estado = Estado.Seguindo;
				else estado = Estado.Vagando;
			} else {
				if (idAlvo != -1) estado = Estado.Fugindo;
				else estado = Estado.Parado;
			}
		}
	}

	/**
	 * Obtém a id do alvo para perseguir ou para fugir
	 */
	private void buscarAlvo() {
		Posicao posicao = entidades.get(id), posicaoAux;
		int dx, dy;
		double distancia, distanciaAux;
		if (idAlvo != -1) {
			posicaoAux = entidades.get(idAlvo);
			distancia = gestorDeCaminho.obterDistancia(posicao, posicaoAux);
		} else distancia = ALCANCE * 2;
		idAlvo = -1;
		for (int idAux : entidades.keySet()) {
			if (id == idAux) continue;
			posicaoAux = entidades.get(idAux);
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
	 * Obtém uma direção aleatória
	 * 
	 * @see sistema.controlador.jogo.movimento.ContDaEntMovel#obterDirecao()
	 */
	public Direcao obterDirecao() {
		switch (estado) {
		case Vagando:
			return Direcao.escolhaAleatoria();
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
		Posicao posicao = entidades.get(id), posicaoAlvo = entidades.get(idAlvo);
		int dx = posicaoAlvo.x - posicao.x;
		int dy = posicaoAlvo.y - posicao.y;
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
	private class GestorDeCaminho {
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
		 * Obtém o melhor caminho de uma posicao para outra
		 * 
		 * @param posicao
		 * @param posicaoAlvo
		 * @return Lista de Nos
		 */
		private List<No> obterCaminho(Posicao posicao, Posicao posicaoAlvo) {
			// FIXME Testar a movimentação da IA por A* [GP Ep. 101-102]
			List<No> caminho = new ArrayList<No>();
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
						caminho.add(noAtual);
						noAtual = noAtual.pai;
					}
					nos.clear();
					nosAvaliados.clear();
					return caminho;
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
		private class No {
			private Posicao posicao;
			private No pai;
			private double custoF, custoG, custoH;

			/**
			 * Cria o objeto Nó
			 * 
			 * @param posicao
			 * @param pai
			 * @param custoG
			 * @param custoH
			 */
			public No(Posicao posicao, No pai, double custoG, double custoH) {
				this.posicao = posicao;
				this.pai = pai;
				this.custoG = custoG;
				this.custoH = custoH;
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
