package sistema.interface_grafica.renderizador.base_do_jogo.mapa;

/**
 * Classe com a coordenada do mapa
 * 
 * @author Emanuel
 */
public class Coordenada {
	private int x, y;
	private final int TAMANHO_DO_BLOCO = Bloco.TAMANHO;

	/**
	 * Cria o objeto coordenada com x e y
	 * 
	 * @param x
	 * @param y
	 */
	public Coordenada(int x, int y) {
		this.x = x * TAMANHO_DO_BLOCO;
		this.y = y * TAMANHO_DO_BLOCO;
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

	public int[] obterCoordenada() {
		int coordenada[] = { x, y };
		return coordenada;
	}
}
