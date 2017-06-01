package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import sistema.controlador.ControladorDaEntidade;
import sistema.controlador.ControladorDoAmbiente;
import sistema.interface_grafica.painel.PainelDoJogo;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe para renderizar o jogo
 * 
 * @author Emanuel
 */
public class RendDoJogo {
	//TODO Implementar o Jogo aqui
	private PainelDoJogo painel;
	private Image imagem;

	private ControladorDaEntidade controladorDaEntidade;
	private ControladorDoAmbiente controladorDoAmbiente;

	private int x = 100, y = 100;
	private boolean xdir = true, ydir = false;

	/**
	 * Cria o objeto para renderização do jogo
	 * 
	 * @param painel
	 * @param contDaEntidade
	 * @param contDoAmbiente
	 */
	public RendDoJogo(PainelDoJogo painel, ControladorDaEntidade contDaEntidade, ControladorDoAmbiente contDoAmbiente) {
		this.painel = painel;
		controladorDaEntidade = contDaEntidade;
		controladorDoAmbiente = contDoAmbiente;

	}

	/**
	 * Renderiza a tela do jogo
	 *
	 * @return Image
	 */
	public Image renderizar() {
		imagem = new BufferedImage(painel.getWidth(), painel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graficos = imagem.getGraphics();

		renderizarInfo(graficos);
		renderizarJogo(graficos);

		graficos.dispose();

		// TODO Remover depois
		moveText();

		return imagem;
	}

	/**
	 * Renderizar a janela do informações
	 * 
	 * @param graficos
	 */
	private void renderizarInfo(Graphics graficos) {
		int percentualMassa = (int) ((painel.massaCelular / painel.MASSA_CELULAR_MAX) * 100);
		final int MIL = (int) Math.pow(10, 3);
		graficos.setColor(Color.black);
		graficos.fillRect(0, 0, painel.getWidth(), 30);

		renderizarRotulo(graficos, Color.lightGray, "Massa Celular: " + percentualMassa + "%", painel.getWidth() - 10);
		if (painel.pontuacao < MIL)
			renderizarRotulo(graficos, Color.gray, "Pontuacao: " + painel.pontuacao, (painel.getWidth() / 2) + 70);
		else renderizarRotulo(graficos, Color.gray, "Pontuacao: " + painel.pontuacao / MIL + "K",
				(painel.getWidth() / 2) + 70);
		if (painel.qtdCelulas < MIL)
			renderizarRotulo(graficos, Color.darkGray, "Qtd Celulas: " + painel.qtdCelulas, 150);
		else renderizarRotulo(graficos, Color.darkGray, "Qtd Celulas: " + painel.qtdCelulas / MIL + "K", 150);
	}

	/**
	 * Renderiza um rótulo com o texto em x e y
	 * 
	 * @param graficos
	 * @param cor
	 * @param texto
	 * @param desvioX
	 */
	private void renderizarRotulo(Graphics graficos, Color cor, String texto, int desvioX) {
		int x = painel.getWidth() - desvioX, y = 5;

		// Retangulo do rotulo
		graficos.setColor(cor);
		graficos.fillRect(x, y, 140, 20);

		// Texto do rotulo
		if (cor == Color.lightGray) graficos.setColor(Color.black);
		else graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 5, y + 15);
	}

	/**
	 * Renderizar a janela do jogo
	 * 
	 * @param graficos
	 */
	private void renderizarJogo(Graphics graficos) {
		graficos.setColor(Color.white);
		graficos.fillRect(0, 30, painel.getWidth(), painel.getHeight());
		graficos.setColor(Color.black);
		graficos.setFont(new Font("Verdana", 0, 25));
		graficos.drawString("Janela do Jogo", x, y);

		// Ações conforme as teclas são pressionadas
		if (Teclado.sair) painel.acaoDoBotao('J', 'S');
	}

	/**
	 * Faz a movimentação das posições de x e y do texto
	 */
	private void moveText() {
		if (x == painel.getWidth() - 183 || x == 0) xdir = !xdir;
		if (y == painel.getHeight() - 5 || y == 49) ydir = !ydir;
		if (xdir) x++;
		else x--;
		if (ydir) y++;
		else y--;
	}
}
