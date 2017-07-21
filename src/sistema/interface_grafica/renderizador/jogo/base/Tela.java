package sistema.interface_grafica.renderizador.jogo.base;

import java.util.Random;

import sistema.interface_grafica.renderizador.jogo.base.mapa.Bloco;

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
		for (int y = 0; y < Bloco.TAMANHO; y++) {
			yAux = y + posY;
			for (int x = 0; x < Bloco.TAMANHO; x++) {
				xAux = x + posX;
				if (xAux < -Bloco.TAMANHO || xAux >= largura || yAux < 0 || yAux >= altura) break;
				if (xAux < 0) xAux = 0;
				pixeis[xAux + yAux * largura] = bloco.sprite.pixeis[x + y * Bloco.TAMANHO];
			}
		}
	}

	/**
	 * Renderiza o especime na tela
	 *
	 * @param posX
	 * @param posY
	 * @param sprite
	 * @param invertido
	 */
	public void rendEspecime(int posX, int posY, Sprite sprite, int cor, int invertido) {
		int xAux = 0, yAux = 0, xRes, yRes, corAux;
		posX -= desvioX;
		posY -= desvioY;
		for (int y = 0; y < Sprite.TAMANHO; y++) {
			yAux = y + posY;
			yRes = y;
			if (invertido == 2 || invertido == 3) yRes = 15 - y;
			for (int x = 0; x < Sprite.TAMANHO; x++) {
				xAux = x + posX;
				xRes = x;
				if (invertido == 1 || invertido == 3) xRes = 15 - x;
				if (xAux < -Sprite.TAMANHO || xAux >= largura || yAux < 0 || yAux >= altura) break;
				if (xAux < 0) xAux = 0;
				corAux = sprite.pixeis[xRes + yRes * Sprite.TAMANHO];
				if (corAux != COR_TRANSPARENTE) pixeis[xAux + yAux * largura] = corAux + cor;
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
