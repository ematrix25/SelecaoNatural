package sistema.igu.renderizador.jogo.base.mapa;

import java.util.Random;

/**
 * Classe com a coordenada do mapa
 * 
 * @author Emanuel
 */
public class Coordenada {
	private final int TAMANHO_DO_BLOCO = Bloco.TAMANHO;

	private int x, y;

	/**
	 * Cria o objeto coordenada com x e y aleatórias
	 *
	 * @param mapa
	 */
	public Coordenada(Mapa mapa) {
		configurarCoordenada(mapa);
	}

	/**
	 * Cria o objeto coordenada com x e y
	 * 
	 * @param x
	 * @param y
	 */
	public Coordenada(int x, int y) {
		configurarCoordenada(x, y);
	}

	/**
	 * Obtém x
	 * 
	 * @return int
	 */
	public int obterX() {
		return x;
	}

	/**
	 * Obtém y
	 * 
	 * @return int
	 */
	public int obterY() {
		return y;
	}

	/**
	 * Obtém a coordenada x e y
	 * 
	 * @return int[]
	 */
	public int[] obterCoordenada() {
		int coordenada[] = { x, y };
		return coordenada;
	}

	/**
	 * Configura uma coordenada aleatória afastada da coordenada dada
	 *
	 * @param mapa
	 */
	public void configurarCoordenada(Mapa mapa) {
		int x = gerarInteiro(0, mapa.largura);
		int y = gerarInteiro(0, mapa.altura);
		if (mapa.obterBloco(x, y).solido) configurarCoordenada(mapa);
		else configurarCoordenada(x, y);
	}

	/**
	 * Muda a coordenada x e y
	 * 
	 * @param x
	 * @param y
	 */
	public void configurarCoordenada(int x, int y) {
		this.x = x * TAMANHO_DO_BLOCO;
		this.y = y * TAMANHO_DO_BLOCO;
	}

	/**
	 * Gera um número inteiro dentro dos limites estabelecidos
	 * 
	 * @param limiteMin
	 * @param limiteMax
	 * @return int
	 */
	private int gerarInteiro(int limiteMin, int limiteMax) {
		return limiteMin + new Random().nextInt(limiteMax - limiteMin);
	}

	/**
	 * Gera o texto com a posição da coordenada
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
