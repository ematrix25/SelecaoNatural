package sistema.controlador.jogo.movimento;

import java.util.HashMap;
import java.util.Random;

import componente.Componente.Posicao;
import componente.Componente.Velocidade.Direcao;
import sistema.igu.Painel;

/**
 * Classe que controla os dados da IA
 * 
 * @author Emanuel
 */
public class ContDaIA extends ContDaEntMovel {
	private Random aleatorio;
	private Estado estado = Estado.Parado;

	private HashMap<Integer, Posicao> entidades;

	private final int ALCANCE = 150;
	private int idAlvo = -1;

	/**
	 * Cria o objeto controlador da IA
	 * 
	 * @param painel
	 */
	public ContDaIA(Painel painel) {
		aleatorio = new Random();
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
		Estado estadoAux = estado;
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
			distancia = obterDistancia(posicao, posicaoAux);
		} else distancia = ALCANCE * 2;
		idAlvo = -1;
		for (int idAux : entidades.keySet()) {
			if (id == idAux) continue;
			posicaoAux = entidades.get(idAux);
			dx = Math.abs(posicaoAux.x - posicao.x);
			dy = Math.abs(posicaoAux.y - posicao.y);
			if (dx < ALCANCE && dy < ALCANCE) {
				distanciaAux = obterDistancia(posicao, posicaoAux);
				if (distanciaAux < distancia) {
					distancia = distanciaAux;
					idAlvo = idAux;
				}
			}
		}
	}

	/**
	 * Obtém a distância dado duas medidas de x e y
	 * 
	 * @param posicao
	 * @param posicaoAux
	 * @return double
	 */
	private double obterDistancia(Posicao posicao, Posicao posicaoAux) {
		int dx = Math.abs(posicaoAux.x - posicao.x);
		int dy = Math.abs(posicaoAux.y - posicao.y);
		return Math.sqrt((dx * dx) + (dy * dy));
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
}
