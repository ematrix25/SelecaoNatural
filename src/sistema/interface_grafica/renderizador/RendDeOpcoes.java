package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import sistema.interface_grafica.Painel;
import sistema.utilitario.Opcoes;
import sistema.utilitario.Resolucao;

/**
 * Classe para renderizar a tela de opções
 * 
 * @author Emanuel
 */
public class RendDeOpcoes extends Renderizador {
	private int configuracoes[];

	/**
	 * Cria o objeto de renderizador de opções
	 * 
	 * @param painel
	 */
	public RendDeOpcoes(Painel painel) {
		super(painel);
		configuracoes = Opcoes.configuracoes;
	}

	/**
	 * Obtem as configuracoes
	 * 
	 * @return int[]
	 */
	public int[] obterConfiguracoes() {
		return configuracoes;
	}

	/**
	 * Renderiza a tela de opcoes
	 * 
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizar()
	 */
	public BufferedImage renderizar() {
		// TODO Criar alguma imagem diferente
		carregarImagem("/imagens/menu.jpg");
		Graphics graficos = imagem.getGraphics();

		String opcoes[] = { "Teclado", "Mouse" };
		int configuracao;

		// Renderiza a configuração do controle de jogo
		configuracao = renderizarSelecao(graficos, "Controlar o Jogo por:", opcoes, 40, 0);
		if (configuracao != -1) configuracoes[0] = configuracao;

		// Renderiza a configuração de tamanho da janela
		opcoes = new String[3];
		for (int i = 0; i < opcoes.length; i++) {
			opcoes[i] = Resolucao.larguras[i] + "x" + Resolucao.alturas[i];
		}
		configuracao = renderizarSelecao(graficos, "Tamanho da janela:", opcoes, 120, 1);
		if (configuracao != -1) configuracoes[1] = configuracao;

		// Renderiza os botões
		renderizarBotao(graficos, "Cancelar", 100);
		renderizarBotao(graficos, "Salvar", 200);

		return imagem;
	}

	/**
	 * Renderiza a selecao de configuração com suas opções
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarSelecao(java.awt.Graphics,
	 *      java.lang.String, java.lang.String[], int, int)
	 */
	protected int renderizarSelecao(Graphics graficos, String texto, String[] opcoes, int desvioY, int selecao) {
		int x = 20, y = desvioY;
		int opcao = -1;

		// Texto da Configuração
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Opções de configuração
		for (int i = 0; i < opcoes.length; i++) {
			if (configuracoes[selecao] == i + 1) {
				if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, true)) opcao = i + 1;
			} else if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, false)) opcao = i + 1;
		}
		return opcao;
	}

	/**
	 * Renderiza uma opção de configuração com o texto em x e y
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarOpcao(java.awt.Graphics,
	 *      java.lang.String, int, int, boolean)
	 */
	protected boolean renderizarOpcao(Graphics graficos, String texto, int desvioX, int desvioY, boolean selecionado) {
		int x = desvioX, y = desvioY;
		int tamanho = 8;

		// Botao circular da opção
		graficos.setColor(Color.white);
		graficos.fillOval(x, y, tamanho, tamanho);

		// Texto da opção
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 15, y + tamanho);

		// Marcação de seleção da opção
		if (painel.mouseClicouNoBotao(x, y, tamanho, tamanho)) {
			renderizarMarcacao(graficos, x, y, tamanho / 2);
			return true;
		} else if (selecionado) renderizarMarcacao(graficos, x, y, tamanho / 2);
		return false;
	}

	/**
	 * Renderiza um botão com o texto em x e y saindo do canto inferior direito
	 * 
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarBotao(java.awt.Graphics,
	 *      java.lang.String, int)
	 */
	protected void renderizarBotao(Graphics graficos, String texto, int desvioX) {
		int x = painel.getWidth() - desvioX, y = painel.getHeight() - 40;
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
