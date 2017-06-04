package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.interface_grafica.Painel;
import sistema.utilitario.arquivo.Recurso;

/**
 * Classe para renderizar a tela do menu
 * 
 * @author Emanuel
 */
public class RendDoMenu {
	private Painel painel;
	private Image imagem;

	/**
	 * Cria o objeto de renderizador da seleção
	 * 
	 * @param painel
	 */
	public RendDoMenu(Painel painel) {
		this.painel = painel;
	}

	/**
	 * Renderiza a tela do menu
	 * 
	 * @return
	 */
	public Image renderizar() {
		// TODO Arrumar uma maneira de desenhar a imagem do arquivo
		// carregarImagem();
		imagem = new BufferedImage(painel.getWidth(), painel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graficos = imagem.getGraphics();

		int desvio = 20;

		// Renderizar texto da informação do sobre
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString("Jogo desenvolvido por Emanuel Torres", 10, painel.getHeight() - 20);

		// Renderizar Botões
		renderizarBotao(graficos, "Opções", desvio);
		renderizarBotao(graficos, "Continuar", desvio * 3);
		renderizarBotao(graficos, "Novo Jogo", desvio * 5);

		return imagem;
	}

	/**
	 * Carrega a imagem do arquivo dos recursos
	 */
	@SuppressWarnings("unused")
	private void carregarImagem() {
		try {
			imagem = ImageIO.read(new Recurso().getEnderecoEmFluxo("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Renderiza um botão com o texto em x e y saindo do canto inferior direito
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioY
	 */
	private void renderizarBotao(Graphics graficos, String texto, int desvioY) {
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
