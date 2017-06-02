package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.interface_grafica.painel.PainelDeTeste;
import sistema.utilitario.arquivo.Recurso;

/**
 * Classe para renderizar a tela do menu
 * 
 * @author Emanuel
 */
public class RendDoMenu {
	// TODO Integrar ao painel
	private PainelDeTeste painel;
	private Image imagem;

	/**
	 * Cria o objeto de renderizador da seleção
	 * 
	 * @param painel
	 * @param contDaEntidade
	 * @param contDoAmbiente
	 */
	public RendDoMenu(PainelDeTeste painel) {
		this.painel = painel;
	}

	/**
	 * Renderiza o painel do menu
	 * 
	 * @return
	 */
	public Image renderizar() {
		carregarImagem();
		Graphics graficos = imagem.getGraphics();
		// Renderizar texto da informação do sobre
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString("Jogo desenvolvido por Emanuel Torres", 10, painel.getHeight() - 20);

		// Renderizar Botões
		renderizarBotao(graficos, "Opções", 50);
		renderizarBotao(graficos, "Continuar", 100);
		renderizarBotao(graficos, "Novo Jogo", 150);

		return imagem;
	}

	/**
	 * Carrega a imagem do arquivo dos recursos
	 */
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
		int x = painel.getWidth() - 100, y = painel.getHeight() - desvioY;
		int largura = 90, altura = 30;

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (painel.mouseEstaNoBotao(x, y, largura, altura) && painel.ehContinuavel) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, largura, altura);

		// Texto do botao
		graficos.setColor(Color.black);
		if (painel.mouseEstaNoBotao(x, y, largura, altura) && painel.ehContinuavel) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (painel.mouseClicouNoBotao(x, y, largura, altura)) painel.acaoDoBotao(texto.charAt(0));
	}
}
