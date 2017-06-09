package sistema.interface_grafica;

import java.util.Random;

import sistema.mapa.bloco.Bloco;

/**
 * Tela do Jogo
 * 
 * @author Emanuel
 */
public class Tela {
	public final int TAMANHO_DO_MAPA = 64;
	public final int MASCARA_DO_TAM_DO_MAPA = TAMANHO_DO_MAPA - 1;
	public final int COR_TRANSPARENTE = 0xff4cfe00;

	public int largura, altura;
	public int pixeis[];
	public int desvioX, desvioY;
	public int blocos[] = new int[TAMANHO_DO_MAPA * TAMANHO_DO_MAPA];

	/**
	 * Cria o objeto da Tela do Jogo
	 * 
	 * @param largura
	 * @param altura
	 */
	public Tela(int largura, int altura) {
		this.largura = largura;
		this.altura = altura;
		pixeis = new int[largura * altura];

		for (int i = 0; i < blocos.length; i++) {
			blocos[i] = new Random().nextInt(0xffffff);
		}
	}

	/**
	 * Limpa a tela
	 */
	public void limpar() {
		for (int i = 0; i < pixeis.length; i++) {
			pixeis[i] = 0;
		}
	}

	/**
	 * Renderiza os blocos do mapa na tela
	 * 
	 * @param posX
	 * @param posY
	 * @param bloco
	 */
	public void renderizarBloco(int posX, int posY, Bloco bloco) {
		int xAux = 0, yAux = 0;
		posX -= desvioX;
		posY -= desvioY;
		for (int y = 0; y < bloco.sprite.TAMANHO; y++) {
			yAux = y + posY;
			for (int x = 0; x < bloco.sprite.TAMANHO; x++) {
				xAux = x + posY;
				if (xAux < -bloco.sprite.TAMANHO || xAux >= largura || yAux < 0 || yAux >= altura) break;
				if (xAux < 0) xAux = 0;
				pixeis[xAux + yAux * largura] = bloco.sprite.pixeis[x + y * bloco.sprite.TAMANHO];
			}
		}
	}

	/**
	 * Renderiza o jogador na tela
	 *
	 * @param posX
	 * @param posY
	 * @param sprite
	 * @param direcao
	 */
	public void renderizarJogador(int posX, int posY, Sprite sprite, int direcao) {
		int xAux = 0, yAux = 0, xRes, yRes, cor;
		posX -= desvioX;
		posY -= desvioY;
		for (int y = 0; y < sprite.TAMANHO; y++) {
			yAux = y + posY;
			yRes = y;
			if (direcao == 2 || direcao == 3) yRes = 15 - y;
			for (int x = 0; x < sprite.TAMANHO; x++) {
				xAux = x + posX;
				xRes = x;
				if (direcao == 1 || direcao == 3) xRes = 15 - x;
				if (xAux < -sprite.TAMANHO || xAux >= largura || yAux < 0 || yAux >= altura) break;
				if (xAux < 0) xAux = 0;
				cor = sprite.pixeis[xRes + yRes * sprite.TAMANHO];
				if (cor != COR_TRANSPARENTE) pixeis[xAux + yAux * largura] = cor;
			}
		}
	}

	/**
	 * Configura o desvio de x e y
	 */
	public void configurarDesvio(int desvioX, int desvioY) {
		this.desvioX = desvioX;
		this.desvioY = desvioY;
	}
}
