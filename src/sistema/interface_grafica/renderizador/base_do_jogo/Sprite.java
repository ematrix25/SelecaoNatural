package sistema.interface_grafica.renderizador.base_do_jogo;

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

	public static Sprite spriteDeAgua = new Sprite(0, 0, FolhaDeSprites.SPRITES);
	public static Sprite spriteDePolimeroOrganico = new Sprite(1, 0, FolhaDeSprites.SPRITES);
	public static Sprite spriteDePolimeroInorganico = new Sprite(2, 0, FolhaDeSprites.SPRITES);
	public static Sprite spriteDeAgua1 = new Sprite(3, 0, FolhaDeSprites.SPRITES);
	public static Sprite spriteDePolimeroOrganico1 = new Sprite(4, 0, FolhaDeSprites.SPRITES);
	public static Sprite spriteDePolimeroOrganico2 = new Sprite(5, 0, FolhaDeSprites.SPRITES);

	public static Sprite jogadorMovendoY = new Sprite(0, 15, FolhaDeSprites.SPRITES);
	public static Sprite jogadorMovendoX = new Sprite(1, 15, FolhaDeSprites.SPRITES);
	public static Sprite jogadorParadoY = new Sprite(2, 15, FolhaDeSprites.SPRITES);
	public static Sprite jogadorParadoX = new Sprite(3, 15, FolhaDeSprites.SPRITES);

	/**
	 * Cria o objeto de Sprite da FolhaDeSprites
	 * 
	 * @param tamanho
	 * @param x
	 * @param y
	 * @param folhaDeSprites
	 */
	public Sprite(int x, int y, FolhaDeSprites folhaDeSprites) {
		pixeis = new int[TAMANHO * TAMANHO];
		this.x = x;
		this.y = y;
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
	 * Gera um texto com a posi��o da sprite na folha de sprites
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sprite = [" + x + ", " + y + "]";
	}

	/**
	 * Classe com os dados da FolhaDeSprites
	 * 
	 * @author Emanuel
	 */
	public static class FolhaDeSprites {
		public int pixeis[];

		public static final int TAMANHO = 256;

		public static final FolhaDeSprites SPRITES = new FolhaDeSprites("/imagens/texturas/sprites.png");

		/**
		 * Cria o objeto da FolhaDeSprites
		 * 
		 * @param endereco
		 * @param tamanho
		 */
		public FolhaDeSprites(String endereco) {
			pixeis = new int[TAMANHO * TAMANHO];
			carregar(endereco);
		}

		/**
		 * Carrega a folha de Sprites
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
