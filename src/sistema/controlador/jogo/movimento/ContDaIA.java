package sistema.controlador.jogo.movimento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import componente.Componente.EstadoDaIA.Estado;
import componente.Componente.Posicao;
import componente.Componente.Velocidade.Direcao;
import componente.Componente.Vetor2i;
import sistema.controlador.jogo.ContAuxDaEnt.Entidade;
import sistema.igu.Painel;
import sistema.igu.renderizador.jogo.base.mapa.Bloco;
import sistema.igu.renderizador.jogo.base.mapa.Coordenada;
import sistema.igu.renderizador.jogo.base.mapa.Mapa;
import sistema.utilitario.arquivo.Arquivo.ArquivoDeRegistro;

/**
 * Classe que controla os dados da IA
 * 
 * @author Emanuel
 */
public class ContDaIA extends ContDaEntMovel {
	private Random aleatorio;
	private GestorDeCaminho gestorDeCaminho;
	private Estado estado;

	private HashMap<Integer, Posicao> posicoesDasEnt;

	private final int ALCANCE = 150;

	/**
	 * Cria o objeto controlador da IA
	 * 
	 * @param painel
	 * @param mapa
	 */
	public ContDaIA(Painel painel, Mapa mapa) {
		aleatorio = new Random();
		gestorDeCaminho = new GestorDeCaminho(mapa);
		estado = Estado.Parado;
		posicoesDasEnt = painel.posicoesDasEnt;
	}

	/**
	 * Configura o controlador da IA conforme a id
	 * 
	 * @param entidade
	 */
	public void configurarIA(Entidade entidade) {
		configurarID(entidade.id);
		if (configEstado(entidade)) {
			configProxPos(entidade);
		}
	}

	/**
	 * Configura o estado da IA se necessário e obtêm se modifica a proxPos
	 *
	 * @param entidade
	 * @return boolean
	 */
	private boolean configEstado(Entidade entidade) {
		Posicao posicao = posicoesDasEnt.get(id);
		int idAlvoAnt = entidade.estadoDaIA.idAlvo;
		int idAlvo = obterAlvo(idAlvoAnt);
		boolean modificaProxPos = false;
		estado = entidade.estadoDaIA.estado;
		if (idAlvo == -1) {
			// Parado || Vagando
			if (idAlvo != idAlvoAnt) {
				posicao.proxPos = null;
				entidade.posicao.proxPos = null;
			}
			if (posicao.proxPos == null) {
				if (aleatorio.nextBoolean()) {
					estado = Estado.Vagando;
					modificaProxPos = true;
				} else estado = Estado.Parado;
			}
		} else {
			// Seguindo || Fugindo
			if (idAlvo != idAlvoAnt) {
				if (aleatorio.nextBoolean()) estado = Estado.Seguindo;
				else estado = Estado.Fugindo;
			}
			modificaProxPos = true;
		}
		entidade.estadoDaIA.estado = estado;
		entidade.estadoDaIA.idAlvo = idAlvo;
		return modificaProxPos;
	}

	/**
	 * Obtém a id do alvo para perseguir ou para fugir
	 * 
	 * @return int
	 */
	private int obterAlvo(int idAlvoAnt) {
		Posicao posicao = posicoesDasEnt.get(id), posicaoAux;
		int dx, dy, idAlvo = -1;
		double distancia = ALCANCE * 2, distanciaAux;
		if (idAlvoAnt != -1) {
			posicaoAux = posicoesDasEnt.get(idAlvoAnt);
			if (posicaoAux != null) {
				distancia = gestorDeCaminho.obterDistancia(posicao, posicaoAux);
				if (distancia < ALCANCE) idAlvo = idAlvoAnt;
			}
		}
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
		return idAlvo;
	}

	/**
	 * Configura as próximas posições
	 * 
	 * @param entidade
	 */
	private void configProxPos(Entidade entidade) {
		Posicao posicao = entidade.posicao, posicaoAlvo, proxPos = null;
		int idAlvo = entidade.estadoDaIA.idAlvo;
		System.out.print("ID " + id + posicao + " está " + entidade.estadoDaIA.estado);
		if (idAlvo == -1) proxPos = gestorDeCaminho.obterProxPos(posicao, null, ALCANCE);
		else {
			System.out.print(", tem como alvo " + idAlvo);
			posicaoAlvo = posicoesDasEnt.get(idAlvo);
			System.out.print(" em " + posicaoAlvo);
			//TODO Verificar porque não desvia das pedras
			if (estado == Estado.Fugindo) proxPos = gestorDeCaminho.obterProxPos(posicao, posicaoAlvo, ALCANCE);
			else proxPos = gestorDeCaminho.obterProxPos(posicao, posicaoAlvo);
		}
		System.out.println(" e próxima posição em " + proxPos);
		entidade.posicao.proxPos = proxPos;
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
		if (posDestino == null) return null;
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
	 * Classe para designar o melhor caminho para a IA
	 * 
	 * @author Emanuel
	 */
	public class GestorDeCaminho {
		private Mapa mapa;
		private Comparador ordenadorDeNos;

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
		 * Obtém a próxima posição até uma posição dentro do alcance
		 * 
		 * @param posicao
		 * @param posicaoAlvo
		 * @param alcance
		 * @return Posicao
		 */
		public Posicao obterProxPos(Posicao posicao, Posicao posicaoAlvo, int alcance) {
			Posicao proxPos = null;
			int x, y, cont = 1;
			while (proxPos == null) {
				cont++;
				if (cont > 100) break;
				if (posicaoAlvo != null) {
					x = posicao.x + gerarValorDoAlcance(posicao.x, posicaoAlvo.x, alcance);
					y = posicao.y + gerarValorDoAlcance(posicao.y, posicaoAlvo.y, alcance);
				} else {
					x = posicao.x + gerarValorDoAlcance(alcance);
					y = posicao.y + gerarValorDoAlcance(alcance);
				}
				x /= Bloco.TAMANHO;
				y /= Bloco.TAMANHO;
				if (!(x < mapa.largura && y < mapa.altura)) continue;
				if (!mapa.obterBloco(x, y).solido) proxPos = obterProxPos(posicao, new Posicao(new Coordenada(x, y)));
			}
			System.out.print("[" + cont + "]");
			return proxPos;
		}

		/**
		 * Gera um valor aleatório dado o alcance e as posições
		 * 
		 * @param posicao
		 * @param posicaoAlvo
		 * @param alcance
		 * @return int
		 */
		private int gerarValorDoAlcance(int posicao, int posicaoAlvo, int alcance) {
			int sinal = 1, valor = alcance / 2 + aleatorio.nextInt(alcance / 2);
			if (valor == 0 && posicao < posicaoAlvo) sinal = -1;
			return sinal * valor;
		}

		/**
		 * Gera um valor aleatório dado o alcance
		 * 
		 * @param alcance
		 * @return int
		 */
		private int gerarValorDoAlcance(int alcance) {
			int valor = alcance / 2 + aleatorio.nextInt(alcance / 2);
			if (aleatorio.nextBoolean()) return valor;
			else return -valor;
		}

		/**
		 * Obtém a próxima posição de uma posição para outra
		 * 
		 * @param posicao
		 * @param posicaoAlvo
		 * @return Posicao
		 */
		public Posicao obterProxPos(Posicao posicao, Posicao posicaoAlvo) {
			Posicao proxPos = null, posicaoAtual = null, posicaoAux;
			List<Vetor2i> caminho = obterCaminho(posicao.obterVetor(), posicaoAlvo.obterVetor());
			if (caminho == null || caminho.size() == 1) return null;
			for (Vetor2i vetor : caminho) {
				posicaoAux = new Posicao(vetor);
				if (proxPos == null) {
					proxPos = posicaoAux;
					posicaoAtual = proxPos;
				} else {
					posicaoAtual.proxPos = posicaoAux;
					posicaoAtual = posicaoAux;
				}
			}
			return proxPos;
		}

		/**
		 * Obtém o melhor caminho de uma posição para outra
		 * 
		 * @param vetor
		 * @param vetorAlvo
		 * @return Lista de vetores
		 */
		private List<Vetor2i> obterCaminho(Vetor2i vetor, Vetor2i vetorAlvo) {
			List<No> nos = new ArrayList<No>();
			List<No> nosAvaliados = new ArrayList<No>();
			List<Vetor2i> caminho = new ArrayList<Vetor2i>();
			No noAtual = new No(vetor, null, 0, obterDistancia(vetor, vetorAlvo)), noAux;
			StringBuilder texto = new StringBuilder();
			Bloco bloco;
			Vetor2i vetorAux;
			int x, y, dx, dy, sinal, custoG, custoH;
			nos.add(noAtual);
			while (!nos.isEmpty()) {
				Collections.sort(nos, ordenadorDeNos);
				noAtual = nos.get(0);
				texto.append(noAtual + " -> ");
				if (noAtual.vetor.equals(vetorAlvo)) {
					while (noAtual.pai != null) {
						caminho.add(noAtual.vetor);
						noAtual = noAtual.pai;
					}
					nos.clear();
					nosAvaliados.clear();
					Collections.reverse(caminho);
					return caminho;
				}
				nos.remove(noAtual);
				nosAvaliados.add(noAtual);
				x = noAtual.vetor.x;
				y = noAtual.vetor.y;
				for (int i = 0; i < 2; i++) {
					sinal = (int) Math.pow((-1), (i + 1));
					for (int j = 0; j < 2; j++) {
						dx = (j % 2) * sinal;
						dy = (1 - (j % 2)) * sinal;
						bloco = mapa.obterBloco(x + dx, y + dy);
						if (bloco == null || bloco.solido) continue;
						vetorAux = new Vetor2i(x + dx, y + dy);
						custoG = noAtual.custoG + obterDistancia(noAtual.vetor, vetorAux);
						custoH = obterDistancia(vetorAux, vetorAlvo);
						noAux = new No(vetorAux, noAtual, custoG, custoH);
						if (nosAvaliados.contains(noAux) && custoG >= noAux.custoG) continue;
						if (!nos.contains(noAux) || custoG < noAux.custoG) {
							texto.append(noAux + " ");
							nos.add(noAux);
						}
					}
				}
				texto.append(" -> " + vetorAlvo + "\n");
			}
			nosAvaliados.clear();
			ArquivoDeRegistro.escrever(texto);
			return null;
		}

		/**
		 * Obtém a distância dado duas medidas de x e y
		 * 
		 * @param posicao
		 * @param posicaoAlvo
		 * @return int
		 */
		private int obterDistancia(Posicao posicao, Posicao posicaoAlvo) {
			return Math.abs(posicao.x - posicaoAlvo.x) + Math.abs(posicao.y - posicaoAlvo.y);
		}

		/**
		 * Obtém a distância dado duas medidas de x e y
		 * 
		 * @param vetor
		 * @param vetorAlvo
		 * @return int
		 */
		private int obterDistancia(Vetor2i vetor, Vetor2i vetorAlvo) {
			return Math.abs(vetorAlvo.x - vetor.x) + Math.abs(vetorAlvo.y - vetor.y);
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
		@SuppressWarnings("unused")
		public class No {
			private Vetor2i vetor;
			private No pai;
			private int custoG, custoH, custoF;

			/**
			 * Cria o objeto Nó
			 * 
			 * @param posicao
			 * @param pai
			 * @param custoG
			 * @param custoH
			 */
			public No(Vetor2i vetor, No pai, int custoG, int custoH) {
				this.vetor = vetor;
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
				result = prime * result + ((vetor == null) ? 0 : vetor.hashCode());
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
				if (vetor == null) {
					if (other.vetor != null) {
						return false;
					}
				} else if (!vetor.equals(other.vetor)) {
					return false;
				}
				return true;
			}

			/**
			 * Mostra a posição do nó
			 * 
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {
				return vetor.toString();
			}
		}
	}
}
