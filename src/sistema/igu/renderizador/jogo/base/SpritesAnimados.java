package sistema.igu.renderizador.jogo.base;

import componente.Componente.Velocidade.Direcao;
import sistema.igu.Painel;
import sistema.igu.renderizador.jogo.base.Sprite.FolhaDeSprites;

/**
 * Classe que organiza sprites para animação
 * 
 * @author Emanuel
 */
public class SpritesAnimados {
	private FolhaDeSprites folhaDeSprites;
	private Sprite sprites[];

	public static SpritesAnimados spiral = new SpritesAnimados(FolhaDeSprites.SPIRAL);
	public static SpritesAnimados bacillus = new SpritesAnimados(FolhaDeSprites.BACILLUS);
	public static SpritesAnimados coccus = new SpritesAnimados(FolhaDeSprites.COCCUS);

	/**
	 * Cria o objeto gestor de sprites para animação
	 *
	 * @param folhaDeSprites
	 */
	public SpritesAnimados(FolhaDeSprites folhaDeSprites) {
		this.folhaDeSprites = folhaDeSprites;
		carregar();
	}

	/**
	 * Carrega os sprites da folha de sprites
	 */
	private void carregar() {
		int qtd = (folhaDeSprites.largura / Sprite.TAMANHO) * (folhaDeSprites.altura / Sprite.TAMANHO);
		sprites = new Sprite[qtd];
		int x, y;
		for (int i = 0; i < sprites.length; i++) {
			x = i % 4;
			y = i / 4;
			sprites[i] = new Sprite(x, y, folhaDeSprites);
		}
	}

	/**
	 * Obtém o sprite dado a direção e velocidade da entidade
	 * 
	 * @param direcao
	 * @param velocidade
	 * @return Sprite
	 */
	public Sprite obterSprite(Direcao direcao, int velocidade) {
		int indice = -1;
		if (direcao != null) {
			indice = obterSpriteDaDirecao(direcao);
			if (velocidade > 0) {
				if (Painel.tempo % 2 == 0) indice+=4;
			}
		}
		if (indice < 0) indice = 2;
		return sprites[indice];
	}

	/**
	 * Obtém o indice do sprite da direção
	 * 
	 * @param direcao
	 * @return int
	 */
	private int obterSpriteDaDirecao(Direcao direcao) {
		switch (direcao) {
		case Cima:
			return 0;
		case Baixo:
			return 1;
		case Direita:
			return 2;
		case Esquerda:
			return 3;
		}
		return -1;
	}
}
