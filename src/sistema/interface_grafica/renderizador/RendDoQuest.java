package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import sistema.interface_grafica.Painel;

/**
 * Classe para renderizar a tela do question�rio
 * 
 * @author Emanuel
 */
public class RendDoQuest extends Renderizador {
	private int respostas[];

	/**
	 * Cria o objeto de renderizador do question�rio
	 * 
	 * @param painel
	 */
	public RendDoQuest(Painel painel) {
		super(painel);
		respostas = new int[5];
	}

	/**
	 * Obtem as respostas
	 * 
	 * @return int[]
	 */
	public int[] obterRespostas() {
		return respostas;
	}

	/**
	 * Renderiza a tela do question�rio
	 */
	public BufferedImage renderizar() {
		// TODO Criar alguma imagem diferente
		carregarGraficos("/imagens/menu.jpg");

		String opcoes[] = new String[5];
		int resposta;

		// TODO Escrever as perguntas e op��es de respostas aqui
		for (int i = 0; i < respostas.length; i++) {
			for (int j = 0; j < opcoes.length; j++) {
				opcoes[j] = "Op��o " + (i + 1) + "." + (j + 1);
			}
			resposta = renderizarSelecao("Pergunta " + (i + 1), opcoes, 40 + 80 * i, i);
			if (resposta != -1) respostas[i] = resposta;
		}

		// Renderiza o bot�o para submeter as respostas
		renderizarBotao("Submeter", 40);

		descarregarGraficos();
		return imagem;
	}

	/**
	 * Renderiza a pergunta com suas op��es de respostas
	 * 
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarSelecao(java.lang.String,
	 *      java.lang.String[], int, int)
	 */
	protected int renderizarSelecao(String texto, String[] opcoes, int desvioY, int selecao) {
		int x = 20, y = desvioY;
		int resposta = -1;

		// Texto da Pergunta
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Op��es de respostas da Pergunta
		for (int i = 0; i < opcoes.length; i++) {
			if (renderizarOpcao(opcoes[i], x + 150 * i, y + 20, respostas[selecao] == i + 1)) resposta = i + 1;
		}
		return resposta;
	}

	/**
	 * Renderiza uma op��o de resposta com o texto em x e y
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarOpcao(java.lang.String,
	 *      int, int, boolean)
	 */
	protected boolean renderizarOpcao(String texto, int desvioX, int desvioY, boolean selecionado) {
		int x = desvioX, y = desvioY;
		int tamanho = 8;

		// Botao circular da op��o
		graficos.setColor(Color.white);
		graficos.fillOval(x, y, tamanho, tamanho);

		// Texto da op��o
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 15, y + tamanho);

		// Marca��o de sele��o da op��o
		if (painel.mouseClicouNoBotao(x, y, tamanho, tamanho)) {
			renderizarMarcacao(x, y, tamanho / 2);
			return true;
		} else if (selecionado) renderizarMarcacao(x, y, tamanho / 2);
		return false;
	}

	/**
	 * Renderiza um bot�o com o texto em x e y saindo do canto inferior direito
	 * 
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarBotao(java.lang.String,
	 *      int)
	 */
	protected void renderizarBotao(String texto, int desvioY) {
		int x = painel.getWidth() - 100, y = painel.getHeight() - desvioY;
		int largura = 90, altura = 30;

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (painel.mouseEstaNoBotao(x, y, largura, altura)) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, largura, altura);

		// Texto do botao
		graficos.setColor(Color.black);
		if (painel.mouseEstaNoBotao(x, y, largura, altura)) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (painel.mouseClicouNoBotao(x, y, largura, altura)) painel.acaoDoBotao(texto.charAt(0));
	}
}
