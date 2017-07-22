package sistema.igu.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import sistema.igu.Painel;
import sistema.utilitario.TocadorDeMusica;

/**
 * Classe para renderizar a tela do menu
 * 
 * @author Emanuel
 */
public class RendDoMenu extends Renderizador {

	/**
	 * Cria o objeto de renderizador da seleção
	 * 
	 * @param painel
	 */
	public RendDoMenu(Painel painel) {
		super(painel);
	}

	/**
	 * Renderiza a tela do menu
	 * 
	 * @see sistema.igu.renderizador.Renderizador#renderizar()
	 */
	public BufferedImage renderizar() {
		carregarGraficos("/imagens/menu.jpg");

		int desvio = 20;

		// Renderizar texto da informação sobre o jogo
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString("Jogo desenvolvido por Emanuel Torres", 10, painel.getHeight() - 40);
		graficos.drawString("Tocando " + TocadorDeMusica.obterNomeDaMusica() + " de "
				+ TocadorDeMusica.obterCompositorDaMusica() + " por " + TocadorDeMusica.obterArtistaDaMusica(), 10,
				painel.getHeight() - 20);

		// Renderizar Botões
		renderizarBotao("Opções", desvio);
		renderizarBotao("Continuar", desvio * 3);
		renderizarBotao("Novo Jogo", desvio * 5);

		descarregarGraficos();
		return imagem;
	}

	/**
	 * Renderiza um botão com o texto em x e y saindo do canto inferior direito
	 * 
	 * @see sistema.igu.renderizador.Renderizador#renderizarBotao(java.lang.String,
	 *      int)
	 */
	protected void renderizarBotao(String texto, int desvioY) {
		int largura = 90, altura = 30;
		int x = painel.getWidth() - (largura + 10), y = painel.getHeight() - (altura + desvioY);

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (painel.mouseEstaNoBotao(x, y, largura, altura) && continuavel(texto.charAt(0)))
			graficos.setColor(Color.gray);
		graficos.fillRect(x, y, largura, altura);

		// Texto do botao
		graficos.setColor(Color.black);
		if (painel.mouseEstaNoBotao(x, y, largura, altura) && continuavel(texto.charAt(0)))
			graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (painel.mouseClicouNoBotao(x, y, largura, altura)) painel.acaoDoBotao(texto.charAt(0));
	}

	/**
	 * Verifica se é possível retomar ao jogo
	 * 
	 * @param inicial
	 * @return boolean
	 */
	private boolean continuavel(char inicial) {
		if (inicial == 'C') return painel.ehContinuavel;
		return true;
	}
}
