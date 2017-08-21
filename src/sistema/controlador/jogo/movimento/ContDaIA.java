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
	 * Configura o estado da IA se necess�rio e obt�m se vai se mover
	 *
	 * @param entidade
	 * @return boolean
	 */
	private boolean configEstado(Entidade entidade) {
		Posicao posicao = posicoesDasEnt.get(id), posicaoAlvo;
		int dx, dy, idAlvoAnt = entidade.estadoDaIA.idAlvo, idAlvo = obterAlvo(idAlvoAnt);
		estado = entidade.estadoDaIA.estado;
		if (idAlvo == -1) {
			// Parado || Vagando
			if (posicao.proxPos == null) {
				if (aleatorio.nextBoolean()) {
					estado = Estado.Vagando;
					return true;
				} else estado = Estado.Parado;

			}
		} else {
			// Seguindo || Fugindo
			if (idAlvo != idAlvoAnt) {
				posicaoAlvo = posicoesDasEnt.get(idAlvo);
				dx = posicao.x - posicaoAlvo.x;
				dy = posicao.y - posicaoAlvo.y;
				switch (entidade.velocidade.direcao) {
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
				return true;
			}
		}
		return false;
	}

	/**
	 * Obt�m a id do alvo para perseguir ou para fugir
	 * 
	 * @return int
	 */
	private int obterAlvo(int idAlvoAnt) {
		Posicao posicao = posicoesDasEnt.get(id), posicaoAux;
		int dx, dy, idAlvo = idAlvoAnt;
		double distancia, distanciaAux;
		if (idAlvo != -1) {
			posicaoAux = posicoesDasEnt.get(idAlvo);
			distancia = gestorDeCaminho.obterDistancia(posicao, posicaoAux);
		} else distancia = ALCANCE * 2;
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
	 * Configura as pr�ximas posi��es
	 * 
	 * @param entidade
	 */
	private void configProxPos(Entidade entidade) {
		Posicao posicao = entidade.posicao, posicaoAlvo, proxPos = null;
		int idAlvo = entidade.estadoDaIA.idAlvo;
		System.out.print("ID " + id + " est� " + entidade.estadoDaIA.estado);
		// TODO Verificiar porque raramente trava
		if (idAlvo == -1) proxPos = gestorDeCaminho.obterCaminho(posicao, ALCANCE);
		else {
			System.out.print(" e tem alvo " + idAlvo);
			posicaoAlvo = posicoesDasEnt.get(idAlvo);
			System.out.print(" em " + posicaoAlvo);
			proxPos = gestorDeCaminho.obterProxPos(posicao, posicaoAlvo);
		}
		System.out.println();
		entidade.posicao.proxPos = proxPos;
	}

	/**
	 * Obt�m o movimento aleat�rio dado a velocidade m�xima permitida
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
	 * Obt�m uma dire��o aleat�ria
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
	 * Obt�m a dire��o segundo a diferen�a entre as entidades
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
		 * Obt�m o melhor caminho at� uma posi��o dentro do alcance
		 * 
		 * @param posicao
		 * @param alcance
		 * @return Posicao
		 */
		public Posicao obterCaminho(Posicao posicao, int alcance) {
			Posicao proxPos = null;
			int x, y;
			System.out.print(".");
			while (proxPos == null) {
				x = (posicao.x + gerarValorDoAlcance(alcance)) / Bloco.TAMANHO;
				y = (posicao.y + gerarValorDoAlcance(alcance)) / Bloco.TAMANHO;
				if (!(x < mapa.largura && y < mapa.altura)) continue;
				if (!mapa.obterBloco(x, y).solido) {
					proxPos = obterProxPos(posicao, new Posicao(new Coordenada(x, y)));
				}
				System.out.print(".");
			}
			return proxPos;
		}

		/**
		 * Gera um valor aleat�rio dado o alcance
		 * 
		 * @param alcance
		 * @return int
		 */
		private int gerarValorDoAlcance(int alcance) {
			return alcance / 2 + aleatorio.nextInt(alcance / 2);
		}

		/**
		 * Obt�m a pr�xima posi��o de uma posi��o para outra
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
		 * Obt�m o melhor caminho de uma posi��o para outra
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
		 * Obt�m a dist�ncia dado duas medidas de x e y
		 * 
		 * @param posicao
		 * @param posicaoAlvo
		 * @return int
		 */
		private int obterDistancia(Posicao posicao, Posicao posicaoAlvo) {
			return obterDistancia(posicao.obterVetor(), posicaoAlvo.obterVetor());
		}

		/**
		 * Obt�m a dist�ncia dado duas medidas de x e y
		 * 
		 * @param vetor
		 * @param vetorAlvo
		 * @return int
		 */
		private int obterDistancia(Vetor2i vetor, Vetor2i vetorAlvo) {
			return Math.abs(vetorAlvo.x - vetor.x) + Math.abs(vetorAlvo.y - vetor.y);
		}

		/**
		 * Classe que compara os n�s
		 * 
		 * @author Emanuel
		 */
		private class Comparador implements Comparator<No> {

			/**
			 * Compara dois n�s
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
		 * Classe do n� da posi��o que � parte do caminho
		 * 
		 * @author Emanuel
		 */
		@SuppressWarnings("unused")
		public class No {
			private Vetor2i vetor;
			private No pai;
			private int custoG, custoH, custoF;

			/**
			 * Cria o objeto N�
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
			 * Obt�m o c�digo
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
			 * Verifica a igualdade dos N�s
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
			 * Mostra a posi��o do n�
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
