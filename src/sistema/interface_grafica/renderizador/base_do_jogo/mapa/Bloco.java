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
	
	public static Bloco blocoDeAgua = new Bloco(Sprite.spriteDeAgua, false);
	public static Bloco blocoOrganico = new Bloco(Sprite.spriteDePolimeroOrganico, false);
	public static Bloco blocoInorganico = new Bloco(Sprite.spriteDePolimeroInorganico, true);
	public static Bloco blocoDeAgua1 = new Bloco(Sprite.spriteDeAgua1, false);
	public static Bloco blocoOrganico1 = new Bloco(Sprite.spriteDePolimeroOrganico1, false);
	public static Bloco blocoOrganico2 = new Bloco(Sprite.spriteDePolimeroOrganico2, false);
	
	/**
	 * Cria um objeto do bloco
	 * 
	 * @param sprite
	 * @param solido
	 */
	public Bloco(Sprite sprite, boolean solido) {
		this.sprite = sprite;

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
