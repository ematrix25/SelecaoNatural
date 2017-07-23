package sistema.igu.renderizador.jogo.base;

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

	public static final int TAMANHO = 16;

	public int pixeis[];

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
				pixeis[x + y * TAMANHO] = folhaDeSprites.pixeis[(this.x + x) + (this.y + y) * folhaDeSprites.largura];
			}
		}
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

		public int largura, altura;

		public static final FolhaDeSprites AMBIENTE = new FolhaDeSprites("/imagens/sprites/ambiente.png");

		public static final FolhaDeSprites BACILLUS = new FolhaDeSprites("/imagens/sprites/entidades/bacillus.png");
		public static final FolhaDeSprites COCCUS = new FolhaDeSprites("/imagens/sprites/entidades/coccus.png");
		public static final FolhaDeSprites SPIRAL = new FolhaDeSprites("/imagens/sprites/entidades/spiral.png");

		/**
		 * Cria o objeto da FolhaDeSprites
		 * 
		 * @param endereco
		 */
		public FolhaDeSprites(String endereco) {
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
				largura = imagem.getWidth();
				altura = imagem.getHeight();
				pixeis = new int[largura * altura];
				imagem.getRGB(0, 0, largura, altura, pixeis, 0, largura);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
