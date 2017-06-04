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
 * Classe para renderizar a tela do question�rio
 * 
 * @author Emanuel
 */
public class RendDoQuest {
	private Painel painel;
	private Image imagem;

	private int respostas[];

	/**
	 * Cria o objeto de renderizador do question�rio
	 * 
	 * @param painel
	 */
	public RendDoQuest(Painel painel) {
		this.painel = painel;
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
	public Image renderizar() {
		// TODO Arrumar uma maneira de desenhar a imagem do arquivo
		// carregarImagem();
		imagem = new BufferedImage(painel.getWidth(), painel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graficos = imagem.getGraphics();

		String opcoes[] = new String[5];
		int resposta;

		// TODO Escrever as perguntas e op��es de respostas aqui
		for (int i = 0; i < respostas.length; i++) {
			for (int j = 0; j < opcoes.length; j++) {
				opcoes[j] = "Op��o " + (i + 1) + "." + (j + 1);
			}
			resposta = renderizarPergunta(graficos, "Pergunta " + (i + 1), opcoes, 40 + 80 * i, i);
			if (resposta != -1) respostas[i] = resposta;
		}

		// Renderiza o bot�o para submeter as respostas
		renderizarBotao(graficos, "Submeter", 40);

		return imagem;
	}

	/**
	 * Carrega a imagem do arquivo dos recursos
	 */
	@SuppressWarnings("unused")
	private void carregarImagem() {
		try {
			// TODO Criar alguma imagem diferente
			imagem = ImageIO.read(new Recurso().getEnderecoEmFluxo("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Renderiza a pergunta com suas op��es de respostas
	 *
	 * @param graficos
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 * @param pergunta
	 * @return int
	 */
	private int renderizarPergunta(Graphics graficos, String texto, String[] opcoes, int desvioY, int pergunta) {
		int x = 20, y = desvioY;
		int resposta = -1;

		// Texto da Pergunta
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Op��es de respostas da Pergunta
		for (int i = 0; i < opcoes.length; i++) {
			if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, respostas[pergunta] == i + 1))
				resposta = i + 1;
		}
		return resposta;
	}

	/**
	 * Renderiza uma op��o de resposta com o texto em x e y
	 *
	 * @param graficos
	 * @param texto
	 * @param desvioX
	 * @param desvioY
	 * @param selecionado
	 * @return boolean
	 */
	private boolean renderizarOpcao(Graphics graficos, String texto, int desvioX, int desvioY, boolean selecionado) {
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
			renderizarMarcacao(graficos, x, y, tamanho / 2);
			return true;
		} else if (selecionado) renderizarMarcacao(graficos, x, y, tamanho / 2);
		return false;
	}

	/**
	 * Renderiza a marca��o da op��o da resposta em x e y
	 * 
	 * @param graficos
	 * @param x
	 * @param y
	 * @param tamanho
	 */
	private void renderizarMarcacao(Graphics graficos, int x, int y, int tamanho) {
		graficos.setColor(Color.black);
		graficos.fillOval(x + (tamanho / 2), y + (tamanho / 2), tamanho, tamanho);
	}

	/**
	 * Renderiza um bot�o com o texto em x e y saindo do canto inferior direito
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
