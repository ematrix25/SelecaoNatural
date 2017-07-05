package sistema.interface_grafica.renderizador.base_do_jogo.mapa;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import sistema.interface_grafica.renderizador.base_do_jogo.Tela;
import sistema.utilitario.arquivo.Recurso;

/**
 * Classe com os dados de um Mapa do jogo
 * 
 * @author Emanuel
 */
public class Mapa {
	private int blocos[];

	public int largura, altura;

	public static final int COR_DE_AGUA = 0xFF0000FF;
	public static final int COR_DE_AGUA1 = 0xFFFFFFFF;
	public static final int COR_ORGANICA = 0xFF00FF00;
	public static final int COR_ORGANICA1 = 0xFFFFFF00;
	public static final int COR_ORGANICA2 = 0xFFFF0000;
	public static final int COR_INORGANICA = 0xFF3F3F3F;

	/**
	 * Cria um objeto do mapa de um arquivo no dado endereco
	 * 
	 * @param temperatura
	 * @param endereco
	 */
	public Mapa(int temperatura, String endereco) {
		try {
			BufferedImage imagem = ImageIO.read(new Recurso().obterEndereco(endereco));
			largura = imagem.getWidth();
			altura = imagem.getHeight();
			blocos = new int[largura * altura];
			imagem.getRGB(0, 0, largura, altura, blocos, 0, largura);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bloco.associarSprites(temperatura);
	}

	/**
	 * Cria um objeto do mapa dada largura e altura
	 * 
	 * @param temperatura
	 * @param largura
	 * @param altura
	 */
	public Mapa(int temperatura, int largura, int altura) {
		this.largura = largura;
		this.altura = altura;
		blocos = new int[largura * altura];
		gerarMapa();
		Bloco.associarSprites(temperatura);
	}

	/**
	 * Gera o mapa
	 */
	private void gerarMapa() {
		int numero;
		Random aleatorio = new Random();
		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < largura; x++) {
				blocos[x + y * largura] = COR_DE_AGUA1;
				numero = aleatorio.nextInt(100);
				if (numero < 40) blocos[x + y * largura] = COR_INORGANICA;
				if (numero < 35) blocos[x + y * largura] = COR_ORGANICA;
				if (numero < 30) blocos[x + y * largura] = COR_ORGANICA1;
				if (numero < 25) blocos[x + y * largura] = COR_ORGANICA2;
				if (numero < 20) blocos[x + y * largura] = COR_DE_AGUA;
			}
		}
	}

	/**
	 * Renderiza o mapa
	 * 
	 * @param desvioX
	 * @param desvioY
	 * @param tela
	 */
	public void renderizar(int desvioX, int desvioY, Tela tela) {
		tela.configurarDesvio(desvioX, desvioY);
		int xInicial = desvioX >> 4;
		int xFinal = (desvioX + tela.largura + 16) >> 4;
		int yInicial = desvioY >> 4;
		int yFinal = (desvioY + tela.altura + 16) >> 4;
		for (int y = yInicial; y < yFinal; y++) {
			for (int x = xInicial; x < xFinal; x++) {
				obterBloco(x, y).renderizar(x, y, tela);
			}
		}
	}

	/**
	 * Obtem o bloco a ser renderizado no mapa
	 * 
	 * @param x
	 * @param y
	 * @return Bloco
	 */
	public Bloco obterBloco(int x, int y) {
		if (x < 0 || y < 0 || x >= largura || y >= altura) return Bloco.blocoDeAgua1;
		else switch (blocos[x + y * largura]) {
		case COR_DE_AGUA:
			return Bloco.blocoDeAgua;
		case COR_ORGANICA:
			return Bloco.blocoOrganico;
		case COR_ORGANICA1:
			return Bloco.blocoOrganico1;
		case COR_ORGANICA2:
			return Bloco.blocoOrganico2;
		case COR_INORGANICA:
			return Bloco.blocoInorganico;
		default:
			return Bloco.blocoDeAgua1;
		}
	}
}
