package sistema.controlador.jogo.movimento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
		estado = Estado.Parado;
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
		direcao = entidade.velocidade.direcao;
		if (configEstado()) entidade.posicao.proxPos = configProxPos();
	}

	/**
	 * Configura o estado da IA se necess�rio e obt�m se precisa configurar
	 * pr�xima posi��o
	 *
	 * @return boolean
	 */
	private boolean configEstado() {
		buscarAlvo();
		Posicao posicao = posicoesDasEnt.get(id), posicaoAlvo;
		int dx, dy;
		if (posicao.proxPos == null) {
			if (aleatorio.nextBoolean()) {
				estado = Estado.Vagando;
				return true;
			} else estado = Estado.Parado;
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
				return true;
			}
		}
		return false;
	}

	/**
	 * Obt�m a id do alvo para perseguir ou para fugir
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
	 * Configura as pr�ximas posi��es
	 * 
	 * @return Posicao
	 */
	private Posicao configProxPos() {
		Posicao posicao = posicoesDasEnt.get(id), posicaoAlvo, proxPos = null;
		System.out.print("ID " + id);
		if (idAlvo == -1) {
			System.out.print(" est� Vagando");
			// FIXME Verificiar porque �s vezes trava
			proxPos = gestorDeCaminho.obterCaminho(posicao, ALCANCE);
		} else {
			System.out.print(" tem alvo " + idAlvo);
			posicaoAlvo = posicoesDasEnt.get(idAlvo);
			System.out.print(" em " + posicaoAlvo);
			// TODO Verificiar porque �s vezes da null
			proxPos = gestorDeCaminho.obterProxPos(posicao, posicaoAlvo);
		}
		System.out.println(" e a pr�xima posi��o � " + proxPos);
		posicao.proxPos = proxPos;
		return proxPos;
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
			Posicao proxPos, posicaoAtual, posicaoAux;
			List<No> caminho = obterCaminho(posicao.obterVetor(), posicaoAlvo.obterVetor());
			if (caminho == null || caminho.size() == 1) return null;
			proxPos = new Posicao(caminho.get(1).vetor);
			posicaoAtual = proxPos;
			for (int i = 2; i < caminho.size(); i++) {
				posicaoAtual.proxPos = new Posicao(caminho.get(i).vetor);
				posicaoAux = posicaoAtual.proxPos;
				posicaoAtual = posicaoAux;
			}

			return proxPos;
		}

		/**
		 * Obt�m o melhor caminho de uma posi��o para outra
		 * 
		 * @param vetor
		 * @param vetorAlvo
		 * @return Lista de N�s
		 */
		private List<No> obterCaminho(Vetor2i vetor, Vetor2i vetorAlvo) {
			List<No> nos = new ArrayList<No>();
			List<No> nosAvaliados = new ArrayList<No>();
			List<No> caminho = new ArrayList<No>();
			No noAtual = new No(vetor, null, 0, obterDistancia(vetor, vetorAlvo)), noAux;
			StringBuilder texto = new StringBuilder();
			Bloco bloco;
			Vetor2i vetorAux;
			int x, y, dx, dy, sinal;
			double custoG, custoH;
			nos.add(noAtual);
			while (!nos.isEmpty()) {
				Collections.sort(nos, ordenadorDeNos);
				noAtual = nos.get(0);
				texto.append(noAtual + " -> ");
				if (noAtual.vetor.equals(vetorAlvo)) {					
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
		 * @return double
		 */
		private double obterDistancia(Posicao posicao, Posicao posicaoAlvo) {
			return obterDistancia(posicao.obterVetor(), posicaoAlvo.obterVetor());
		}

		/**
		 * Obt�m a dist�ncia dado duas medidas de x e y
		 * 
		 * @param vetor
		 * @param vetorAlvo
		 * @return double
		 */
		private double obterDistancia(Vetor2i vetor, Vetor2i vetorAlvo) {
			int dx = Math.abs(vetorAlvo.x - vetor.x);
			int dy = Math.abs(vetorAlvo.y - vetor.y);
			return Math.sqrt((dx * dx) + (dy * dy));
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
			private double custoG, custoH, custoF;

			/**
			 * Cria o objeto N�
			 * 
			 * @param posicao
			 * @param pai
			 * @param custoG
			 * @param custoH
			 */
			public No(Vetor2i vetor, No pai, double custoG, double custoH) {
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
