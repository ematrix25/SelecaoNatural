package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.utilitario.periferico.Mouse;
import sistema.visao.Tela;

/**
 * Cria o Painel do Questionario
 * 
 * @author Emanuel
 */
public class PainelDoQuest extends Painel implements Runnable {
	// TODO Terminar o painel baseado na TelaDoQuestionario

	private static final long serialVersionUID = 1L;

	private Tela tela;
	private Thread thread;
	private Mouse mouse;
	private Image imagem;

	private volatile boolean rodando = false;

	/**
	 * Inicializa o painel do questionario
	 *
	 * @param tela
	 */
	public PainelDoQuest(Tela tela) {
		// Creio que nao precisa de referencia para a tela
		this.tela = tela;
		thread = new Thread("Quest");
		mouse = new Mouse(this.getWidth(), this.getHeight());
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	/**
	 * Inicia a thread da painel do questionario
	 */
	public void start() {
		tela.redimensionar((int) (tela.ALTURA_PADRAO * 1.7));
		rodando = true;
		thread.start();
	}

	/**
	 * Finaliza a thread da painel do questionario
	 */
	public void stop() {
		rodando = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executa a thread do painel do questionario
	 * 
	 * @see java.lang.Runnable#run()
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
	 * Modela a imagem no questionario
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics graficos) {
		graficos.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);

		String opcoes[] = new String[5];
		for (int i = 0; i < opcoes.length; i++) {
			for (int j = 0; j < opcoes.length; j++) {
				opcoes[j] = "Opção " + (i + 1) + "." + (j + 1);
			}
			renderizarPergunta(graficos, "Pergunta " + (i + 1), opcoes, 40 + 80 * i);
		}
		renderizarBotao(graficos, "Submeter", 40);

		graficos.dispose();
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
