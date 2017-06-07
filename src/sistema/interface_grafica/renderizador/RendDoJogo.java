package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import sistema.controlador.ControladorDaEntidade;
import sistema.controlador.ControladorDoAmbiente;
import sistema.interface_grafica.Painel;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe para renderizar o jogo
 * 
 * @author Emanuel
 */
@SuppressWarnings("unused")
public class RendDoJogo extends Renderizador {
	// TODO Implementar o Jogo aqui
	// TODO Integrar os dados dos controladores aqui
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
	public RendDoJogo(Painel painel, ControladorDaEntidade contDaEntidade, ControladorDoAmbiente contDoAmbiente) {
		super(painel);
		controladorDaEntidade = contDaEntidade;
		controladorDoAmbiente = contDoAmbiente;
	}

	/**
	 * Renderiza a tela do jogo
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizar()
	 */
	public BufferedImage renderizar() {
		carregarGraficos();

		renderizarInfo();
		renderizarJogo();

		// TODO Remover depois
		moveText();

		// Ações conforme as teclas são pressionadas
		if (Teclado.sair) painel.acaoDoBotao('s');

		descarregarGraficos();
		return imagem;
	}

	/**
	 * Renderizar a janela do informações
	 */
	private void renderizarInfo() {
		int percentualMassa = (int) ((painel.massaCelular / painel.MASSA_CELULAR_MAX) * 100);
		final int MIL = (int) Math.pow(10, 3);
		graficos.setColor(Color.black);
		graficos.fillRect(0, 0, painel.getWidth(), 30);

		renderizarRotulo(Color.lightGray, "Massa Celular: " + percentualMassa + "%", painel.getWidth() - 10);
		if (painel.pontuacao < MIL)
			renderizarRotulo(Color.gray, "Pontuacao: " + painel.pontuacao, (painel.getWidth() / 2) + 70);
		else renderizarRotulo(Color.gray, "Pontuacao: " + painel.pontuacao / MIL + "K",
				(painel.getWidth() / 2) + 70);
		if (painel.qtdCelulas < MIL)
			renderizarRotulo(Color.darkGray, "Qtd Celulas: " + painel.qtdCelulas, 150);
		else renderizarRotulo(Color.darkGray, "Qtd Celulas: " + painel.qtdCelulas / MIL + "K", 150);
	}

	/**
	 * Renderiza um rótulo com o texto em x e y
	 * 
	 * @param cor
	 * @param texto
	 * @param desvioX
	 */
	private void renderizarRotulo(Color cor, String texto, int desvioX) {
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
	 * Renderizar a tela do jogo
	 */
	private void renderizarJogo() {
		graficos.setColor(Color.white);
		graficos.fillRect(0, 30, painel.getWidth(), painel.getHeight());
		graficos.setColor(Color.black);
		graficos.setFont(new Font("Verdana", 0, 25));
		graficos.drawString("Janela do Jogo", x, y);
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
