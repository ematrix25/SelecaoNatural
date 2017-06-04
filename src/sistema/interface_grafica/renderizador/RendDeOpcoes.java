package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.interface_grafica.Painel;
import sistema.utilitario.Opcoes;
import sistema.utilitario.Resolucao;
import sistema.utilitario.arquivo.Recurso;

/**
 * Classe para renderizar a tela de opções
 * 
 * @author Emanuel
 */
public class RendDeOpcoes {
	// TODO Integrar ao painel
	private Painel painel;
	private Image imagem;	

	private int configuracoes[];

	/**
	 * Cria o objeto de renderizador de opções
	 * 
	 * @param painel
	 */
	public RendDeOpcoes(Painel painel) {
		this.painel = painel;
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
	 * @return Image
	 */
	public Image renderizar() {
		// TODO Arrumar uma maneira de desenhar a imagem do arquivo
		// carregarImagem();
		imagem = new BufferedImage(painel.getWidth(), painel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graficos = imagem.getGraphics();
		
		String opcoes[] = { "Teclado", "Mouse" };
		int configuracao;

		// Renderiza a configuração do controle de jogo
		configuracao = renderizarConfig(graficos, "Controlar o Jogo por:", opcoes, 40, 0);
		if (configuracao != -1) configuracoes[0] = configuracao;

		// Renderiza a configuração de tamanho da janela
		opcoes = new String[3];
		for (int i = 0; i < opcoes.length; i++) {
			opcoes[i] = Resolucao.larguras[i] + "x" + Resolucao.alturas[i];
		}
		configuracao = renderizarConfig(graficos, "Tamanho da janela:", opcoes, 120, 1);
		if (configuracao != -1) configuracoes[1] = configuracao;

		// Renderiza os botões
		renderizarBotao(graficos, "Cancelar", 100);
		renderizarBotao(graficos, "Salvar", 200);

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
	 * Renderiza a configuração com suas opções
	 *
	 * @param graficos
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 * @param configuracao
	 * @return
	 */
	private int renderizarConfig(Graphics graficos, String texto, String[] opcoes, int desvioY, int configuracao) {
		int x = 20, y = desvioY;
		int opcao = -1;

		// Texto da Configuração
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Opções de configuração
		for (int i = 0; i < opcoes.length; i++) {
			if (configuracoes[configuracao] == i + 1) {
				if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, true)) opcao = i + 1;
			} else if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, false)) opcao = i + 1;
		}
		return opcao;
	}

	/**
	 * Renderiza uma opção de configuração com o texto em x e y
	 *
	 * @param graficos
	 * @param texto
	 * @param desvioX
	 * @param desvioY
	 * @param selecionado
	 * @return
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
		graficos.drawString(texto, x + 15, y + tamanho);

		// Marcação de seleção da opção
		if (painel.mouseClicouNoBotao(x, y, tamanho, tamanho)) {
			renderizarMarcacao(graficos, x, y, tamanho / 2);
			return true;
		} else if (selecionado) renderizarMarcacao(graficos, x, y, tamanho / 2);
		return false;
	}

	/**
	 * Renderiza a marcação da opção da configuração em x e y
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
	 * @param desvioX
	 */
	private void renderizarBotao(Graphics graficos, String texto, int desvioX) {
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
