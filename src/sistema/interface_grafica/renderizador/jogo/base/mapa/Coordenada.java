package sistema.interface_grafica.renderizador.jogo.base.mapa;

import java.util.Random;

/**
 * Classe com a coordenada do mapa
 * 
 * @author Emanuel
 */
public class Coordenada {
	private final int TAMANHO_DO_BLOCO = Bloco.TAMANHO;

	private int x, y;
	private Mapa mapa;

	/**
	 * Cria o objeto coordenada com x e y
	 * 
	 * @param x
	 * @param y
	 */
	public Coordenada(Mapa mapa, int x, int y) {
		this.mapa = mapa;
		configurarCoordenada(x, y);
	}

	/**
	 * Cria o objeto coordenada com x e y aleatórias
	 */
	public Coordenada(Mapa mapa) {
		this.mapa = mapa;
		configurarCoordenada();
	}

	/**
	 * Obtem x
	 * 
	 * @return x
	 */
	public int obterX() {
		return x;
	}

	/**
	 * Obtem y
	 * 
	 * @return y
	 */
	public int obterY() {
		return y;
	}

	/**
	 * Obtem a coordenada x e y
	 * 
	 * @return int[]
	 */
	public int[] obterCoordenada() {
		int coordenada[] = { x, y };
		return coordenada;
	}

	/**
	 * Configura uma coordenada aleatória afastada da coordenada dada
	 */
	public void configurarCoordenada() {
		int x = gerarInteiro(0, mapa.largura);
		int y = gerarInteiro(0, mapa.altura);
		if (mapa.obterBloco(x, y).solido) configurarCoordenada();
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
