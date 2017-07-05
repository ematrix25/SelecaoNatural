package sistema.interface_grafica.renderizador.base_do_jogo.mapa;

import sistema.interface_grafica.renderizador.base_do_jogo.Sprite;
import sistema.interface_grafica.renderizador.base_do_jogo.Tela;

/**
 * Classe do bloco que compõe o mapa
 * 
 * @author Emanuel
 */
public class Bloco {
	public int x, y;
	public Sprite sprite;
	public boolean solido;

	public final static int TAMANHO = Sprite.TAMANHO;

	public static Bloco blocoDeAgua;
	public static Bloco blocoOrganico;
	public static Bloco blocoInorganico;
	public static Bloco blocoDeAgua1;
	public static Bloco blocoOrganico1;
	public static Bloco blocoOrganico2;

	/**
	 * Cria um objeto do bloco
	 * 
	 * @param sprite
	 * @param solido
	 */
	public Bloco(Sprite sprite, boolean solido) {
		this.sprite = sprite;
		this.solido = solido;
	}

	/**
	 * Associa as sprites com os blocos
	 * 
	 * @param temperatura
	 */
	public static void associarSprites(int temperatura) {
		blocoDeAgua = new Bloco(Sprite.ambiente[temperatura][0], false);
		blocoOrganico = new Bloco(Sprite.ambiente[temperatura][1], false);
		blocoInorganico = new Bloco(Sprite.ambiente[temperatura][2], true);
		blocoDeAgua1 = new Bloco(Sprite.ambiente[temperatura][3], false);
		blocoOrganico1 = new Bloco(Sprite.ambiente[temperatura][4], false);
		blocoOrganico2 = new Bloco(Sprite.ambiente[temperatura][5], false);
	}

	/**
	 * Renderiza o bloco
	 * 
	 * @param x
	 * @param y
	 * @param tela
	 */
	public void renderizar(int x, int y, Tela tela) {
		tela.renderizarBloco(x << 4, y << 4, this);
	}
}
