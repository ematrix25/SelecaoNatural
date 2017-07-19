package sistema.interface_grafica.renderizador.jogo.base;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.utilitario.arquivo.Recurso;

/**
 * Classe com os dados de uma Sprite
 * 
 * @author Emanuel
 */
public class Sprite {
	private int x, y;
	private FolhaDeSprites folhaDeSprites;

	public static final int TAMANHO = (int) Math.sqrt(FolhaDeSprites.TAMANHO);

	public int pixeis[];

	private static Sprite ambienteFrio[] = associarSprites(0, 6);
	private static Sprite ambienteMorno[] = associarSprites(1, 6);
	private static Sprite ambienteQuente[] = associarSprites(2, 6);

	public static Sprite ambiente[][] = { ambienteFrio, ambienteMorno, ambienteQuente };

	public static Sprite spiral[] = associarSprites(13, 4);
	public static Sprite bacillus[] = associarSprites(14, 4);
	public static Sprite coccus[] = associarSprites(15, 4);

	/**
	 * Cria o objeto de Sprite da FolhaDeSprites
	 * 
	 * @param x
	 * @param y
	 * @param folhaDeSprites
	 */
	public Sprite(int x, int y, FolhaDeSprites folhaDeSprites) {
		pixeis = new int[TAMANHO * TAMANHO];
		this.x = x * TAMANHO;
		this.y = y * TAMANHO;
		this.folhaDeSprites = folhaDeSprites;
		carregar();
	}

	/**
	 * Carrega o Sprite da FolhaDeSprites
	 */
	private void carregar() {
		for (int y = 0; y < TAMANHO; y++) {
			for (int x = 0; x < TAMANHO; x++) {
				pixeis[x + y * TAMANHO] = folhaDeSprites.pixeis[(this.x + x) + (this.y + y) * FolhaDeSprites.TAMANHO];
			}
		}
	}

	/**
	 * Associa sprites em um array
	 * 
	 * @param y
	 * @param qtd
	 * @return Sprite[]
	 */
	private static Sprite[] associarSprites(int y, int qtd) {
		Sprite sprites[] = new Sprite[qtd];
		for (int x = 0; x < sprites.length; x++) {
			sprites[x] = new Sprite(x, y, FolhaDeSprites.SPRITES);
		}
		return sprites;
	}

	/**
	 * Gera um texto com a posição da sprite na folha de sprites
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sprite = [" + x / TAMANHO + ", " + y / TAMANHO + "]";
	}

	/**
	 * Classe com os dados da FolhaDeSprites
	 * 
	 * @author Emanuel
	 */
	public static class FolhaDeSprites {
		private int pixeis[];

		private static final int TAMANHO = 256;

		private static final FolhaDeSprites SPRITES = new FolhaDeSprites("/imagens/texturas/sprites.png");

		/**
		 * Cria o objeto da FolhaDeSprites
		 * 
		 * @param endereco
		 */
		public FolhaDeSprites(String endereco) {
			pixeis = new int[TAMANHO * TAMANHO];
			carregar(endereco);
		}

		/**
		 * Carrega a folha de Sprites
		 *
		 * @param endereco
		 */
		private void carregar(String endereco) {
			try {
				BufferedImage imagem = ImageIO.read(new Recurso().obterEndereco(endereco));
				int largura = imagem.getWidth();
				int altura = imagem.getHeight();
				imagem.getRGB(0, 0, largura, altura, pixeis, 0, largura);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
