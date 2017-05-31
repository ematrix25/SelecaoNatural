package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import sistema.interface_grafica.painel.PainelDoJogo;

/**
 * Classe para renderizar a tela de seleção das espécies
 * 
 * @author Emanuel
 */
public class RendDaSelecao {
	private PainelDoJogo painel;
	private Image imagem;
	private int selecao;

	/**
	 * Cria o objeto de renderizador da seleção
	 * 
	 * @param painel
	 */
	public RendDaSelecao(PainelDoJogo painel) {
		this.painel = painel;
	}
			
	/**
	 * Repassa o resultado da selecao
	 * 
	 * @return int
	 */
	public int getSelecao() {
		return selecao;
	}

	/**
	 * Renderiza a tela de seleção
	 *
	 * @return Image
	 */
	public Image renderizar() {
		imagem = new BufferedImage(painel.getWidth(), painel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graficos = imagem.getGraphics();

		//TODO Dados devem ser fornecidos pelo ControleDoAmbiente
		String dados[] = { "TempMax = 30", "TempMin = 10" };
		renderizarDados(graficos, "Ambiente", dados, 40);

		//TODO Opções devem ser fornecidos pelo ControleDoAmbiente
		String opcoes[] = { "Nome = nunsei:Tipo = chaves:TempMax = 30:TempMin = 10",
				"Nome = vixe:Tipo = o+:TempMax = 32:TempMin = 12",
				"Nome = jadisse:Tipo = tipado:TempMax = 34:TempMin = 14" };
		int selecao = renderizarSelecao(graficos, "Selecione uma espécie: ", opcoes, 100);
		if (selecao != -1) this.selecao = selecao;

		renderizarBotao(graficos, "Selecionar", 40);

		graficos.dispose();
		return imagem;
	}

	/**
	 * Renderiza os dados da seleção
	 * 
	 * @param graficos
	 * @param texto
	 * @param dados
	 * @param desvioY
	 */
	private void renderizarDados(Graphics graficos, String texto, String[] dados, int desvioY) {
		int x = 20, y = desvioY;

		// Texto dos dados
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza os dados
		renderizarDado(graficos, dados[0], x + 0, y + 20);
		renderizarDado(graficos, dados[1], x + 100, y + 20);
	}

	/**
	 * Renderiza um dado em String
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioX
	 * @param desvioY
	 */
	private void renderizarDado(Graphics graficos, String texto, int desvioX, int desvioY) {
		int x = desvioX, y = desvioY;

		// Texto da opção
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x, y);
	}

	/**
	 * Renderiza a seleção com suas opções
	 *
	 * @param graficos
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 * @return int
	 */
	private int renderizarSelecao(Graphics graficos, String texto, String[] opcoes, int desvioY) {
		int x = 20, y = desvioY;
		int selecao = -1;

		// Texto da Pergunta
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Opções de respostas da Pergunta
		for (int i = 0; i < opcoes.length; i++) {
			if (renderizarOpcao(graficos, opcoes[i], x, y + (20 * i + 20), this.selecao == i + 1)) selecao = i + 1;
		}
		return selecao;
	}

	/**
	 * Renderiza uma opção de resposta com o texto em x e y
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

		// Botao circular da opção
		graficos.setColor(Color.white);
		graficos.fillOval(x, y, tamanho, tamanho);

		// Texto da opção
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 0, 12));
		String dados[] = texto.split(":");
		for (int i = 0; i < dados.length; i++) {
			graficos.drawString(dados[i], x + 15 + 120 * i, y + tamanho);
		}

		// Marcação de seleção da opção
		if (painel.mouseClicouNoBotao(x, y, tamanho, tamanho)) {
			renderizarMarcacao(graficos, x, y, tamanho / 2);
			return true;
		} else if (selecionado) renderizarMarcacao(graficos, x, y, tamanho / 2);
		return false;
	}

	/**
	 * Renderiza a marcação da opção da resposta em x e y
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
		if (painel.mouseEstaNoBotao(x, y, largura, altura)) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, largura, altura);

		// Texto do botao
		graficos.setColor(Color.black);
		if (painel.mouseEstaNoBotao(x, y, largura, altura)) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (painel.mouseClicouNoBotao(x, y, largura, altura)) painel.acaoDoBotao('S', texto.charAt(0));
	}
}
