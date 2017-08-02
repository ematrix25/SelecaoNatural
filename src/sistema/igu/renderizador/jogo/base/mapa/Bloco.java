package sistema.igu.renderizador.jogo.base.mapa;

import sistema.igu.renderizador.jogo.base.Sprite;
import sistema.igu.renderizador.jogo.base.Sprite.FolhaDeSprites;
import sistema.igu.renderizador.jogo.base.Tela;

/**
 * Classe do bloco que compõe o mapa
 * 
 * @author Emanuel
 */
public class Bloco {
	public Sprite sprite;
	public boolean solido;

	public final static int TAMANHO = Sprite.TAMANHO;

	public static Bloco blocosDeAgua[] = new Bloco[4];
	public static Bloco blocosOrganicos[] = new Bloco[4];
	public static Bloco blocosInorganicos[] = new Bloco[2];

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
	 * Associa as sprites do ambiente com os blocos dada temperatura
	 * 
	 * @param temperatura
	 */
	public static void associarTemperatura(int temperatura) {
		switch (temperatura) {
		case 0:
			associarBlocos(SpritesDoAmbiente.ambienteFrio);
			break;
		case 1:
			associarBlocos(SpritesDoAmbiente.ambienteMenosFrio);
			break;
		case 2:
			associarBlocos(SpritesDoAmbiente.ambienteMorno);
			break;
		case 3:
			associarBlocos(SpritesDoAmbiente.ambienteMenosQuente);
			break;
		case 4:
			associarBlocos(SpritesDoAmbiente.ambienteQuente);
			break;
		}
	}

	/**
	 * Associa as sprites da folha de sprites do ambiente com os blocos
	 * 
	 * @param sprites
	 */
	public static void associarBlocos(SpritesDoAmbiente sprites) {
		blocosDeAgua = associarSprites(sprites, 0, 4, false);
		blocosOrganicos = associarSprites(sprites, 4, 4, false);
		blocosInorganicos = associarSprites(sprites, 8, 2, true);
	}

	/**
	 * Associa as sprites com os blocos
	 * 
	 * @param qtd
	 * @return Bloco[]
	 */
	private static Bloco[] associarSprites(SpritesDoAmbiente sprites, int inicio, int qtd, boolean solido) {
		Bloco blocos[] = new Bloco[qtd];
		for (int i = 0; i < qtd; i++) {
			blocos[i] = new Bloco(sprites.obterSprite(inicio + i), solido);
		}
		return blocos;
	}

	/**
	 * Renderiza o bloco
	 * 
	 * @param x
	 * @param y
	 * @param tela
	 */
	public void renderizar(int x, int y, Tela tela) {
		tela.renderizarBloco(x / TAMANHO, y / TAMANHO, this);
	}

	/**
	 * Classe que organiza sprites dos blocos do ambiente
	 * 
	 * @author Emanuel
	 */
	public static class SpritesDoAmbiente {
		private Sprite sprites[];

		public static SpritesDoAmbiente ambienteFrio = new SpritesDoAmbiente(0);
		public static SpritesDoAmbiente ambienteMenosFrio = new SpritesDoAmbiente(1);
		public static SpritesDoAmbiente ambienteMorno = new SpritesDoAmbiente(2);
		public static SpritesDoAmbiente ambienteMenosQuente = new SpritesDoAmbiente(3);
		public static SpritesDoAmbiente ambienteQuente = new SpritesDoAmbiente(4);

		/**
		 * Cria o objeto gestor de sprites do ambiente
		 *
		 * @param folhaDeSprites
		 */
		public SpritesDoAmbiente(int y) {
			sprites = new Sprite[10];
			for (int x = 0; x < sprites.length; x++) {
				sprites[x] = new Sprite(x, y, FolhaDeSprites.AMBIENTE);
			}
		}

		/**
		 * Obtém a sprite dado o indice
		 * 
		 * @param indice
		 * @return Sprite
		 */
		public Sprite obterSprite(int indice) {
			return sprites[indice];
		}
	}
}
