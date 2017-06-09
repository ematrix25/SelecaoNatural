package sistema.interface_grafica;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.utilitario.arquivo.Recurso;

/**
 * Classe com os dados da FolhaDeSprites
 * 
 * @author Emanuel
 */
public class FolhaDeSprites {
	public final int TAMANHO;
	public int pixeis[];

	/**
	 * Cria o objeto da FolhaDeSprites
	 * 
	 * @param endereco
	 * @param tamanho
	 */
	public FolhaDeSprites(String endereco, int tamanho) {
		carregar(endereco);
		TAMANHO = tamanho;
		pixeis = new int[TAMANHO * TAMANHO];
	}

	/**
	 * Carrega a folha de Sprites
	 */
	private void carregar(String endereco) {
		try {
			BufferedImage imagem = ImageIO.read(new Recurso().getEndereco(endereco));
			int largura = imagem.getWidth();
			int altura = imagem.getHeight();
			imagem.getRGB(0, 0, largura, altura, pixeis, 0, largura);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
