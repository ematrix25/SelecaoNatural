package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.visao.Tela;

/**
 * Cria o Painel do Questionario
 * 
 * @author Emanuel
 */
public class PainelDoQuest extends Painel {
	// TODO Implementar a ação do botão de guardar as respostas

	private static final long serialVersionUID = 1L;

	/**
	 * Inicializa o painel do questionario
	 *
	 * @param tela
	 */
	public PainelDoQuest(Tela tela) {
		super(tela, "Quest");
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicia a thread da painel do questionario
	 */
	public void start() {
		super.start(1.7f);
	}

	/**
	 * Executa a thread do painel do questionario
	 * 
	 * @see sistema.visao.painel.Painel#run()
	 */
	@Override
	public void run() {
		requestFocus();
		while (rodando) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
			}
			repaint();
		}
	}

	/**
	 * Renderiza o painel do questionario
	 * 
	 * @see sistema.visao.painel.Painel#renderizar(java.awt.Graphics)
	 */
	@Override
	protected void renderizar(Graphics graficos) {
		String opcoes[] = new String[5];
		for (int i = 0; i < opcoes.length; i++) {
			for (int j = 0; j < opcoes.length; j++) {
				opcoes[j] = "Opção " + (i + 1) + "." + (j + 1);
			}
			renderizarPergunta(graficos, "Pergunta " + (i + 1), opcoes, 40 + 80 * i);
		}
		renderizarBotao(graficos, "Submeter", 40);
	}

	/**
	 * Renderiza a pergunta com suas opções de respostas
	 * 
	 * @param graficos
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 */
	private void renderizarPergunta(Graphics graficos, String texto, String[] opcoes, int desvioY) {
		int x = 20, y = desvioY;

		// Texto da Pergunta
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Opções de respostas da Pergunta
		for (int i = 0; i < opcoes.length; i++) {
			renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20);
		}
	}

	/**
	 * Renderiza uma opcao de resposta com o texto em x e y
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioX
	 * @param desvioY
	 */
	private void renderizarOpcao(Graphics graficos, String texto, int desvioX, int desvioY) {
		int x = desvioX, y = desvioY;

		// Circulo da opcao
		graficos.setColor(Color.white);
		graficos.fillOval(x, y, 10, 10);

		// Texto da opcao
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 15, y + 10);
	}

	/**
	 * Renderiza um botao com o texto em x e y saindo do canto inferior direito
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioY
	 */
	private void renderizarBotao(Graphics graficos, String texto, int desvioY) {
		int x = this.getWidth() - 100, y = this.getHeight() - desvioY;

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (mouseEstaNoBotao(x, y)) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, 90, 30);

		// Texto do botao
		graficos.setColor(Color.black);
		if (mouseEstaNoBotao(x, y)) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (mouseClicouNoBotao(x, y)) acaoDoBotao(texto.charAt(0));
	}

	/**
	 * Realiza a ação do botão quando clicado
	 * 
	 * @see sistema.visao.painel.Painel#acaoDoBotao(char)
	 */
	@Override
	protected void acaoDoBotao(char inicial) {
		switch (inicial) {
		case 'S':
			System.out.println("Clicou no botão");
			break;
		default:
			System.out.println("Botao clicado nao reconhecido");
			break;
		}
	}
}
