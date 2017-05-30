package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import sistema.interface_grafica.painel.Painel;
import sistema.utilitario.periferico.Mouse;

/**
 * Classe para renderizar a tela de sele��o das esp�cies
 * 
 * @author Emanuel
 */
public class RendDaSelecao {
	// TODO Integrar ao PainelDoJogo
	private Image imagem;
	private int selecao, larguraDaTela, alturaDaTela;

	/**
	 * Cria o objeto de renderizador da sele��o
	 * 
	 * @param imagem
	 * @param graficos
	 */
	public RendDaSelecao(Painel painel) {
		larguraDaTela = painel.getWidth();
		alturaDaTela = painel.getHeight();
		imagem = new BufferedImage(larguraDaTela, alturaDaTela, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * Repassa a imagem renderizados
	 * 
	 * @return Image
	 */
	public Image getImagem() {
		return imagem;
	}

	/**
	 * Renderiza a tela de sele��o
	 */
	public void renderizar() {
		Graphics graficos = imagem.getGraphics();

		String dados[] = new String[2];
		renderizarDados(graficos, "Ambiente", dados, 40);

		String opcoes[] = new String[4];
		int selecao = renderizarSelecao(graficos, "Selecione uma esp�cie: ", opcoes, 80);
		if(selecao!=-1) this.selecao = selecao;
		
		renderizarBotao(graficos, "Selecionar", 40);

		graficos.dispose();
	}

	/**
	 * Renderiza os dados da sele��o
	 * 
	 * @param graficos
	 * @param texto
	 * @param dados
	 * @param i
	 */
	private void renderizarDados(Graphics graficos, String texto, String[] dados, int desvioY) {
		int x = 20, y = desvioY;

		// Texto dos dados
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza os dados
		renderizarDado(graficos, dados[0], x + 0, y + 20);
		renderizarDado(graficos, dados[1], x + 20, y + 20);
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

		// Texto da op��o
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x, y);
	}

	/**
	 * Renderiza a sele��o com suas op��es
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

		// Renderiza as Op��es de respostas da Pergunta
		for (int i = 0; i < opcoes.length; i++) {
			if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, this.selecao == i + 1)) selecao = i + 1;
		}
		return selecao;
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
		if (mouseClicouNoBotao(x, y, tamanho, tamanho)) {
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
		int x = larguraDaTela - 100, y = alturaDaTela - desvioY;
		int largura = 90, altura = 30;

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (mouseEstaNoBotao(x, y, largura, altura)) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, largura, altura);

		// Texto do botao
		graficos.setColor(Color.black);
		if (mouseEstaNoBotao(x, y, largura, altura)) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (mouseClicouNoBotao(x, y, largura, altura)) acaoDoBotao(texto.charAt(0));
	}

	/**
	 * Verifica se o mouse clicou no bot�o em x e y
	 *
	 * @param x
	 * @param y
	 * @param largura
	 * @param altura
	 * @return boolean
	 */
	protected boolean mouseClicouNoBotao(int x, int y, int largura, int altura) {
		if (mouseEstaNoBotao(x, y, largura, altura) && Mouse.getBotao() > -1) {
			Mouse.setBotao(-1);
			return true;
		}
		return false;
	}

	/**
	 * Verifica se o mouse est� no bot�o
	 *
	 * @param x
	 * @param y
	 * @param largura
	 * @param altura
	 * @return boolean
	 */
	protected boolean mouseEstaNoBotao(int x, int y, int largura, int altura) {
		int mouseX = Mouse.getX(), mouseY = Mouse.getY();
		if (mouseX >= x && mouseX <= x + largura) if (mouseY >= y && mouseY <= y + altura) return true;
		return false;
	}

	/**
	 * Realiza a a��o do bot�o quando clicado
	 * 
	 * @param inicial
	 */
	protected void acaoDoBotao(char inicial) {
		// TODO Implementar as a��es dos bot�es aqui
		switch (inicial) {
		case 'S':
			break;
		default:
			System.out.println("Botao clicado nao reconhecido");
			break;
		}
	}
}
