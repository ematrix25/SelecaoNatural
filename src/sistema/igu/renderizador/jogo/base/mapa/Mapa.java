package sistema.igu.renderizador.jogo.base.mapa;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import sistema.igu.renderizador.jogo.base.Tela;
import sistema.utilitario.arquivo.Recurso;

/**
 * Classe com os dados de um Mapa do jogo
 * 
 * @author Emanuel
 */
public class Mapa {
	private int blocos[];

	private int coresDeAgua[];
	private int coresOrganicas[];
	private int coresInorganicas[];

	public int largura, altura;

	public final int TAMANHO_MINIMO = (int) Math.pow(Bloco.TAMANHO, 2);

	/**
	 * Cria um objeto do mapa de um arquivo no dado endereco
	 * 
	 * @param endereco
	 * @param temperatura
	 */
	public Mapa(String endereco, int temperatura) {
		inicializarCores();
		try {
			BufferedImage imagem = ImageIO.read(new Recurso().obterEndereco(endereco));
			int largura = imagem.getWidth();
			int altura = imagem.getHeight();
			if (largura < TAMANHO_MINIMO || altura < TAMANHO_MINIMO) {
				criarMapa(TAMANHO_MINIMO, TAMANHO_MINIMO);
				colocarNoMapa(imagem);
			} else {
				criarMapa(largura, altura);
				imagem.getRGB(0, 0, largura, altura, blocos, 0, largura);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bloco.associarTemperatura(temperatura);
	}

	/**
	 * Cria um objeto do mapa dada largura e altura
	 * 
	 * @param largura
	 * @param altura
	 * @param temperatura
	 */
	public Mapa(int largura, int altura, int temperatura) {
		inicializarCores();
		criarMapa(largura, altura);
		Bloco.associarTemperatura(temperatura);
	}

	/**
	 * Inicializa as cores conforme a quantidade de blocos do ambiente
	 */
	private void inicializarCores() {
		coresDeAgua = new int[Bloco.blocosDeAgua.length];
		coresDeAgua[0] = 0xFF0000FF;
		for (int i = 1; i < coresDeAgua.length; i++) {
			coresDeAgua[i] = coresDeAgua[0] - obterFator(0) * i;
		}

		coresOrganicas = new int[Bloco.blocosOrganicos.length];
		coresOrganicas[0] = 0xFF00FF00;
		for (int i = 1; i < coresOrganicas.length; i++) {
			coresOrganicas[i] = coresOrganicas[0] - obterFator(1) * i;
		}

		System.out.println("INORGANICAS");
		coresInorganicas = new int[Bloco.blocosInorganicos.length];
		coresInorganicas[0] = 0xFF3F3F3F;
		for (int i = 1; i < coresInorganicas.length; i++) {
			coresInorganicas[i] = coresInorganicas[0] - obterFator(2) * i;
			System.out.println(Integer.toHexString(coresInorganicas[i]).toUpperCase());
		}
		System.out.println();
	}

	/**
	 * Obtém o fator de subtração do RGB
	 * 
	 * @param tipo
	 * @return int
	 */
	private int obterFator(int tipo) {
		switch (tipo) {
		case 0:
			return 0xFF / coresDeAgua.length;
		case 1:
			return (0xFF / coresOrganicas.length) * 0x100;
		case 2:
			int aux = 0x3F / coresInorganicas.length;
			return aux * 0x10000 + aux * 0x100 + aux;
		}
		return -1;
	}

	/**
	 * Cria o dada largura e altura
	 * 
	 * @param largura
	 * @param altura
	 */
	private void criarMapa(int largura, int altura) {
		this.largura = largura;
		this.altura = altura;
		blocos = new int[largura * altura];
		gerarMapa();
	}

	/**
	 * Colocar no mapa a imagem carregada do endereço
	 * 
	 * @param imagem
	 */
	private void colocarNoMapa(BufferedImage imagem) {
		int largura = imagem.getWidth();
		int altura = imagem.getHeight();
		int blocos[] = new int[largura * altura];
		int desvioX = this.largura / 2 - largura / 2;
		int desvioY = this.altura / 2 - altura / 2;
		imagem.getRGB(0, 0, largura, altura, blocos, 0, largura);
		for (int x = 0; x < largura; x++) {
			for (int y = 0; y < altura; y++) {
				this.blocos[desvioX + x + (desvioY + y) * this.largura] = blocos[x + y * largura];
			}
		}
	}

	/**
	 * Gera o mapa
	 */
	private void gerarMapa() {
		int numero;
		Random aleatorio = new Random();
		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < largura; x++) {
				// Paredes de pedra que fecham o mapa
				if (x == 0 || x == largura - 1 || y == 0 || y == altura - 1)
					blocos[x + y * largura] = coresInorganicas[aleatorio.nextInt(coresInorganicas.length)];
				// Dentro das paredes
				else {
					numero = aleatorio.nextInt(20);
					if (numero >= 5) blocos[x + y * largura] = coresDeAgua[aleatorio.nextInt(coresDeAgua.length)];
					if (numero > 0 && numero < 5)
						blocos[x + y * largura] = coresOrganicas[aleatorio.nextInt(coresOrganicas.length)];
					if (numero == 0)
						blocos[x + y * largura] = coresInorganicas[aleatorio.nextInt(coresInorganicas.length)];
				}
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
		if (x < 0 || y < 0 || x >= largura || y >= altura) return Bloco.blocosDeAgua[0];
		else for (int i = 0; i < coresInorganicas.length; i++) {
			if (blocos[x + y * largura] == coresDeAgua[i * 2]) return Bloco.blocosDeAgua[i * 2];
			if (blocos[x + y * largura] == coresDeAgua[i * 2 + 1]) return Bloco.blocosDeAgua[i * 2 + 1];
			if (blocos[x + y * largura] == coresOrganicas[i * 2]) return Bloco.blocosOrganicos[i * 2];
			if (blocos[x + y * largura] == coresOrganicas[i * 2 + 1]) return Bloco.blocosOrganicos[i * 2 + 1];
			if (blocos[x + y * largura] == coresInorganicas[i]) return Bloco.blocosInorganicos[i];
		}
		return Bloco.blocosDeAgua[0];
	}
}
